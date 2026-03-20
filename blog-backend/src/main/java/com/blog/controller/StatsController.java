package com.blog.controller;

import com.blog.common.Result;
import com.blog.service.StatsService;
import com.blog.vo.StatsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/dashboard")
    public Result<StatsVO> getDashboard() {
        return Result.success(statsService.getDashboard());
    }
}
