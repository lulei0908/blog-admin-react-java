package com.blog.controller;

import com.blog.common.Result;
import com.blog.dto.LoginDTO;
import com.blog.dto.RegisterDTO;
import com.blog.service.AuthService;
import com.blog.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDTO dto) {
        return Result.success(authService.login(dto));
    }

    @PostMapping("/register")
    public Result<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        return Result.created(authService.register(dto));
    }

    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getPrincipal().toString());
        return Result.success(authService.getCurrentUser(userId));
    }

    @PostMapping("/logout")
    public Result<Void> logout(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getPrincipal().toString());
        authService.logout(userId);
        return Result.success(null);
    }
}
