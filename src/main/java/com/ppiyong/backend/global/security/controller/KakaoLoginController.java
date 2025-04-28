package com.ppiyong.backend.global.security.controller;

import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.security.dto.response.LoginResponseDto;
import com.ppiyong.backend.global.security.service.KakaoLoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "카카오 로그인 API Controller", description = "카카오 로그인 API")
public class KakaoLoginController {

    private final KakaoLoginServiceImpl kakaoLoginServiceImpl;

    @Operation(summary = "카카오 인가 코드 받기", description = """
            카카오 인가 코드를 받기 위한 url을 반환합니다.
            """)
    @GetMapping("/url/kakao")
    public String get() {
        return kakaoLoginServiceImpl.getLoginUrl();
    }

    @Operation(summary = "카카오 로그인", description = """
            카카오 로그인을 통하여 유저의 이름과, 이메일을 저장합니다.<br>
            인가 코드 요청 url을 통하여 코드를 받습니다.<br>
            로그인 성공시 토큰을 발급합니다.
            구체적인 회원정보는 마이페이지 수정에서 처리합니다.
            """, parameters = @Parameter(name = "Code", description = "카카오로부터 받은 인가 코드"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "카카오 토큰 받기 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {
                  "code": "TE004",
                  "error": "INVALID_KAKAO_ACCESS_TOKEN",
                  "message": "카카오 토큰이 유효하지 않습니다."
                }
            """))),
            @ApiResponse(responseCode = "401", description = "카카오로부터 정보 얻기 실패",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {
                  "code": "TE009",
                  "error": "FAILED_KAKAO_REQUEST",
                  "message": "카카오로부터 요청에 실패하였습니다."
                }
            """)))})
    @PostMapping("/login/kakao")
    public LoginResponseDto callback(
            @Parameter(description = "카카오로부터 얻은 인가 코드")
            @RequestParam String code,
            HttpServletResponse response
    ) throws CustomException {
        return kakaoLoginServiceImpl.login(code, response);

    }
}
