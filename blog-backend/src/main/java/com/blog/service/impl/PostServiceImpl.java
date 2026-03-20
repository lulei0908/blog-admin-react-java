package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.dto.PostDTO;
import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.Tag;
import com.blog.mapper.CategoryMapper;
import com.blog.mapper.PostMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.PostService;
import com.blog.util.SlugUtil;
import com.blog.vo.CategoryVO;
import com.blog.vo.PostVO;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    @Override
    public Page<PostVO> getList(Long page, Long limit, String status, Long categoryId, String keyword) {
        Page<Post> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getDeletedAt, null);
        if (status != null && !status.isEmpty()) wrapper.eq(Post::getStatus, status);
        if (categoryId != null) wrapper.eq(Post::getCategoryId, categoryId);
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(Post::getTitle, keyword).or().like(Post::getContent, keyword));
        }
        wrapper.orderByDesc(Post::getCreateTime);
        
        Page<Post> result = postMapper.selectPage(pageParam, wrapper);
        
        Page<PostVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<PostVO> voList = result.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public PostVO getById(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null || post.getDeletedAt() != null) {
            throw new BusinessException(404, "文章不存在");
        }
        return convertToVO(post);
    }

    @Override
    @Transactional
    public PostVO create(PostDTO dto) {
        String slug = SlugUtil.generate(dto.getTitle());
        long count = postMapper.selectCount(new LambdaQueryWrapper<Post>().eq(Post::getSlug, slug));
        if (count > 0) slug = slug + "-" + System.currentTimeMillis();
        
        Post post = new Post();
        BeanUtils.copyProperties(dto, post);
        post.setSlug(slug);
        post.setViewCount(0);
        post.setIsSticky(dto.getIsSticky() != null && dto.getIsSticky());
        
        postMapper.insert(post);
        return convertToVO(post);
    }

    @Override
    @Transactional
    public PostVO update(Long id, PostDTO dto) {
        Post post = postMapper.selectById(id);
        if (post == null || post.getDeletedAt() != null) {
            throw new BusinessException(404, "文章不存在");
        }
        
        BeanUtils.copyProperties(dto, post);
        if (dto.getIsSticky() != null) post.setIsSticky(dto.getIsSticky());
        
        postMapper.updateById(post);
        return convertToVO(post);
    }

    @Override
    public void delete(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) throw new BusinessException(404, "文章不存在");
        post.setDeletedAt(LocalDateTime.now());
        postMapper.updateById(post);
    }

    @Override
    public void toggleSticky(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) throw new BusinessException(404, "文章不存在");
        post.setIsSticky(!post.getIsSticky());
        postMapper.updateById(post);
    }

    private PostVO convertToVO(Post post) {
        PostVO vo = new PostVO();
        BeanUtils.copyProperties(post, vo);
        
        if (post.getCategoryId() != null) {
            Category category = categoryMapper.selectById(post.getCategoryId());
            if (category != null) {
                CategoryVO categoryVO = new CategoryVO();
                categoryVO.setId(category.getId());
                categoryVO.setName(category.getName());
                categoryVO.setSlug(category.getSlug());
                vo.setCategory(categoryVO);
            }
        }
        
        return vo;
    }
}
