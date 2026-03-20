package com.blog.service;

import com.blog.vo.MediaVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    Page<MediaVO> getList(Long page, Long limit);
    MediaVO upload(MultipartFile file, Long uploaderId);
    void delete(Long id);
}
