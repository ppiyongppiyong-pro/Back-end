package com.ppiyong.backend.api.member.controller;

import com.ppiyong.backend.api.member.dto.request.MypageRequestDto;
import com.ppiyong.backend.api.member.dto.response.MypageResponseDto;
import com.ppiyong.backend.api.member.service.MemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Mypage", description = "마이페이지 API")
public class MemberController {

    private final MemberServiceImpl memberService;

    @Operation(summary = "회원 정보 수정하기", description = """
            회원 정보를 수정합니다.<br>
            헤더에 accessToken을 넣어주세요.
            """, parameters = {@Parameter(name = "MypageRequest", description = "회원가입 요청 객체",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MypageRequestDto.class)
            ))})
    @PatchMapping("/profiles")
    public MypageResponseDto updateProfile(
            @RequestBody MypageRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) {
        String token = authToken.startsWith("Bearer ") ?
                authToken.substring(7) : authToken;
        return memberService.addMemberInfo(token, requestDto);
    }


    @Operation(summary = "마이페이지 조회하기", description = """
            마이페이지를 조회합니다.<br>
            헤더에 accessToken을 넣어주세요.
            """)
    @GetMapping("/profiles")
    public MypageResponseDto getProfile(
            @RequestHeader("Authorization") String authToken
    ) {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) : authToken;

        return memberService.getMyInfo(authToken);
    }
}
