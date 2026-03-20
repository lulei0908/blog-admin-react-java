package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.common.BusinessException;
import com.blog.dto.CategoryDTO;
import com.blog.entity.Category;
import com.blog.mapper.CategoryMapper;
import com.blog.service.CategoryService;
import com.blog.util.SlugUtil;
import com.blog.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> getTree() {
        List<Category> all = categoryMapper.selectList(null);
        return buildTree(all, null);
    }

    @Override
    public CategoryVO getById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) throw new BusinessException(404, "分类不存在");
        return convertToVO(category);
    }

    @Override
    @Transactional
    public CategoryVO create(CategoryDTO dto) {
        String slug = SlugUtil.generate(dto.getName());
        long count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>().eq(Category::getSlug, slug));
        if (count > 0) slug = slug + "-" + System.currentTimeMillis();
        
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        category.setSlug(slug);
        
        categoryMapper.insert(category);
        return convertToVO(category);
    }

    @Override
    @Transactional
    public CategoryVO update(Long id, CategoryDTO dto) {
        Category category = categoryMapper.selectById(id);
        if (category == null) throw new BusinessException(404, "分类不存在");
        
        BeanUtils.copyProperties(dto, category);
        if (dto.getName() != null) {
            category.setSlug(SlugUtil.generate(dto.getName()));
        }
        
        categoryMapper.updateById(category);
        return convertToVO(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        long childCount = categoryMapper.selectCount(new LambdaQueryWrapper<Category>().eq(Category::getParentId, id));
        if (childCount > 0) throw new BusinessException(400, "请先删除子分类");
        categoryMapper.deleteById(id);
    }

    private List<CategoryVO> buildTree(List<Category> all, Long parentId) {
        return all.stream()
                .filter(c -> (parentId == null && c.getParentId() == null) || 
                             (parentId != null && c.getParentId() != null && c.getParentId().equals(parentId)))
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private CategoryVO convertToVO(Category category) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(category, vo);
        return vo;
    }
}
