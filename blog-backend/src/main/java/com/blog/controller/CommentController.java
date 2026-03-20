package com.blog.controller;

import com.blog.common.Result;
import com.blog.service.CommentService;
import com.blog.vo.CommentVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Result<Page<CommentVO>> getList(
            @RequestParam(defaultValue = "1") Long page,
            @RequestParam(defaultValue = "10") Long limit,
            @RequestParam(required = false) String status) {
        Page<CommentVO> result = commentService.getList(page, limit, status);
        return Result.success(result.getRecords(), Result.PageMeta.of(result.getTotal(), page, limit));
    }

    @PutMapping("/{id}/review")
    public Result<Void> review(@PathVariable Long id, @RequestBody Map<String, String> body) {
        commentService.review(id, body.get("status"));
        return Result.success(null);
    }

    @PostMapping("/{id}/reply")
    public Result<Void> reply(@PathVariable Long id, @RequestBody Map<String, String> body) {
        commentService.reply(id, body.get("reply"));
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Result.success(null);
    }
}
