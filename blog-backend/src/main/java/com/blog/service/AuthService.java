package com.blog.service;

import com.blog.dto.LoginDTO;
import com.blog.dto.RegisterDTO;
import com.blog.vo.UserVO;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(LoginDTO dto);
    Map<String, Object> register(RegisterDTO dto);
    UserVO getCurrentUser(Long userId);
    void logout(Long userId);
}
