package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.common.BusinessException;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.PostMapper;
import com.blog.service.CommentService;
import com.blog.vo.CommentVO;
import com.blog.vo.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    @Override
    public Page<CommentVO> getList(Long page, Long limit, String status) {
        Page<Comment> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Comment::getStatus, status);
        }
        wrapper.orderByDesc(Comment::getCreateTime);
        
        Page<Comment> result = commentMapper.selectPage(pageParam, wrapper);
        Page<CommentVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<CommentVO> voList = result.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    @Transactional
    public void review(Long id, String status) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) throw new BusinessException(404, "评论不存在");
        
        comment.setStatus(status);
        commentMapper.updateById(comment);
    }

    @Override
    @Transactional
    public void reply(Long id, String reply) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) throw new BusinessException(404, "评论不存在");
        
        comment.setReply(reply);
        comment.setReplyTime(LocalDateTime.now());
        comment.setStatus("approved");
        commentMapper.updateById(comment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentMapper.deleteById(id);
    }

    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtils.copyProperties(comment, vo);
        
        if (comment.getPostId() != null) {
            Post post = postMapper.selectById(comment.getPostId());
            if (post != null) {
                PostVO postVO = new PostVO();
                postVO.setId(post.getId());
                postVO.setTitle(post.getTitle());
                postVO.setSlug(post.getSlug());
                vo.setPost(postVO);
            }
        }
        
        return vo;
    }
}
