package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.BusinessException;
import com.blog.dto.TagDTO;
import com.blog.entity.Tag;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import com.blog.util.SlugUtil;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    @Override
    public List<TagVO> getList() {
        List<Tag> tags = tagMapper.selectList(new LambdaQueryWrapper<Tag>().orderByDesc(Tag::getCreateTime));
        return tags.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public TagVO getById(Long id) {
        Tag tag = tagMapper.selectById(id);
        if (tag == null) throw new BusinessException(404, "标签不存在");
        return convertToVO(tag);
    }

    @Override
    @Transactional
    public TagVO create(TagDTO dto) {
        String slug = SlugUtil.generate(dto.getName());
        long count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>().eq(Tag::getSlug, slug));
        if (count > 0) slug = slug + "-" + System.currentTimeMillis();
        
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setSlug(slug);
        
        tagMapper.insert(tag);
        return convertToVO(tag);
    }

    @Override
    @Transactional
    public TagVO update(Long id, TagDTO dto) {
        Tag tag = tagMapper.selectById(id);
        if (tag == null) throw new BusinessException(404, "标签不存在");
        
        tag.setName(dto.getName());
        tag.setSlug(SlugUtil.generate(dto.getName()));
        
        tagMapper.updateById(tag);
        return convertToVO(tag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagMapper.deleteById(id);
    }

    private TagVO convertToVO(Tag tag) {
        TagVO vo = new TagVO();
        BeanUtils.copyProperties(tag, vo);
        return vo;
    }
}
