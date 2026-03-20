package com.blog.controller;

import com.blog.common.Result;
import com.blog.dto.PostDTO;
import com.blog.service.PostService;
import com.blog.vo.PostVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Result<Page<PostVO>> getList(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "10") Long limit,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {
        Page<PostVO> result = postService.getList(page, limit, status, categoryId, keyword);
        return Result.success(result.getRecords(), Result.PageMeta.of(result.getTotal(), page, limit));
    }

    @GetMapping("/{id}")
    public Result<PostVO> getById(@PathVariable Long id) {
        return Result.success(postService.getById(id));
    }

    @PostMapping
    public Result<PostVO> create(@RequestBody PostDTO dto) {
        return Result.created(postService.create(dto));
    }

    @PutMapping("/{id}")
    public Result<PostVO> update(@PathVariable Long id, @RequestBody PostDTO dto) {
        return Result.success(postService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return Result.success(null);
    }

    @PutMapping("/{id}/sticky")
    public Result<Void> toggleSticky(@PathVariable Long id) {
        postService.toggleSticky(id);
        return Result.success(null);
    }
}
