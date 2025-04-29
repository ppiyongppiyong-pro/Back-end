package com.ppiyong.backend.global.security.service;

import com.ppiyong.backend.global.security.dto.response.LoginResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface KakaoLoginService {

    String getLoginUrl();

    LoginResponseDto login(String code, HttpServletResponse response);
}
