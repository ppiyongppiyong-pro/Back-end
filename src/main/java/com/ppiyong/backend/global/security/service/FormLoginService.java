package com.ppiyong.backend.global.security.service;

import com.ppiyong.backend.global.security.dto.request.LoginRequestDto;
import com.ppiyong.backend.global.security.dto.request.SignupRequestDto;
import com.ppiyong.backend.global.security.dto.response.LoginResponseDto;
import com.ppiyong.backend.global.security.dto.response.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface FormLoginService {

    SignupResponseDto register(SignupRequestDto requestDto);
    LoginResponseDto generalLogin(LoginRequestDto requestDto, HttpServletResponse httpResponse);
    void logout(HttpServletResponse httpResponse, String token);
    String refresh(HttpServletRequest httpRequest, HttpServletResponse httpResponse);
}
