package com.ppiyong.backend.global.security.controller;

import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.response.ErrorResponse;
import com.ppiyong.backend.global.security.dto.request.LoginRequestDto;
import com.ppiyong.backend.global.security.dto.request.SignupRequestDto;
import com.ppiyong.backend.global.security.dto.response.LoginResponseDto;
import com.ppiyong.backend.global.security.dto.response.SignupResponseDto;
import com.ppiyong.backend.global.security.service.FormLoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "폼 로그인 API Controller", description = "일반 로그인 API")
public class FormLoginController {

    private final FormLoginServiceImpl formLoginService;

    @Operation(summary = "회원가입하기", description = "회원가입 기능의 API 입니다.")
    @PostMapping("/signup")
    public SignupResponseDto signup(
            @RequestBody SignupRequestDto requestDto
            ) throws CustomException {
        return formLoginService.register(requestDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "올바르지 않은 로그인", content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = """
                                {
                                  "code": "ME001",
                                  "error": "MEMBER_NOT_FOUND",
                                  "message": "회원을 찾을 수 없습니다."
                                }
                            """))),
    })
    @Operation(summary = "로그인하기", description = "로그인 기능의 API 입니다.", requestBody =
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "이메일 주소와 비밀번호",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = LoginRequestDto.class)
            )))
    @PostMapping("/login/form")
    public LoginResponseDto formLogin(
            @RequestBody LoginRequestDto requestDto,
            HttpServletResponse response
    ) throws CustomException {
        return formLoginService.generalLogin(requestDto, response);
    }

    // Access Token을 재발급받기
    @Operation(summary = "액세스 토큰 재발급", description = """
            access token이 만료되면, refresh token의 유효성을 판단해 다시 access token을 발급받습니다.
            """, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "만료된 액세스 토큰과 리프레시 토큰",
            required = true
    ))
    @PostMapping("/refresh")
    public String refresh(
            HttpServletRequest request, HttpServletResponse response
    ) {
        return formLoginService.refresh(request, response);
    }

    // 로그아웃하기
    @PostMapping("/logout")
    public void logout(
            HttpServletResponse response,
            @RequestHeader("Authorization") String authToken
    ) {
        String token = authToken.startsWith("Bearer ") ?
                authToken.substring(7) : authToken;
        formLoginService.logout(response, token);
    }
}