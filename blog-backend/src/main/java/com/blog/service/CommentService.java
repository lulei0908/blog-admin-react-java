package com.blog.service;

import com.blog.vo.CommentVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface CommentService {
    Page<CommentVO> getList(Long page, Long limit, String status);
    void review(Long id, String status);
    void reply(Long id, String reply);
    void delete(Long id);
}
