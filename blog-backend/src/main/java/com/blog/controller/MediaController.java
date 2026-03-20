package com.blog.controller;

import com.blog.common.Result;
import com.blog.service.MediaService;
import com.blog.vo.MediaVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @GetMapping
    public Result<Page<MediaVO>> getList(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "20") Long limit) {
        Page<MediaVO> result = mediaService.getList(page, limit);
        return Result.success(result.getRecords(), Result.PageMeta.of(result.getTotal(), page, limit));
    }

    @PostMapping("/upload")
    public Result<MediaVO> upload(@RequestParam("file") MultipartFile file, Authentication authentication) {
        Long userId = Long.parseLong(authentication.getPrincipal().toString());
        return Result.created(mediaService.upload(file, userId));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        mediaService.delete(id);
        return Result.success(null);
    }
}
