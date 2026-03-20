package com.blog.controller;

import com.blog.common.Result;
import com.blog.dto.TagDTO;
import com.blog.service.TagService;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public Result<List<TagVO>> getList() {
        return Result.success(tagService.getList());
    }

    @GetMapping("/{id}")
    public Result<TagVO> getById(@PathVariable Long id) {
        return Result.success(tagService.getById(id));
    }

    @PostMapping
    public Result<TagVO> create(@RequestBody TagDTO dto) {
        return Result.created(tagService.create(dto));
    }

    @PutMapping("/{id}")
    public Result<TagVO> update(@PathVariable Long id, @RequestBody TagDTO dto) {
        return Result.success(tagService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return Result.success(null);
    }
}
