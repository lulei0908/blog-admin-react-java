package com.blog.service;

import com.blog.dto.PostDTO;
import com.blog.vo.PostVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface PostService {
    Page<PostVO> getList(Long page, Long limit, String status, Long categoryId, String keyword);
    PostVO getById(Long id);
    PostVO create(PostDTO dto);
    PostVO update(Long id, PostDTO dto);
    void delete(Long id);
    void toggleSticky(Long id);
}
