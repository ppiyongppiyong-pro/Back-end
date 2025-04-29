package com.ppiyong.backend.global.security.service;

import com.ppiyong.backend.api.member.common.Role;
import com.ppiyong.backend.api.member.common.Type;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import com.ppiyong.backend.global.security.dto.response.KakaoInfoResponseDto;
import com.ppiyong.backend.global.security.dto.response.KakaoTokenDto;
import com.ppiyong.backend.global.security.dto.response.LoginResponseDto;
import com.ppiyong.backend.global.security.util.KakaoUtil;
import com.ppiyong.backend.global.security.util.RefreshTokenUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Tag(name ="카카오 로그인 API Controller", description = "카카오 로그인 API")
public class KakaoLoginServiceImpl implements KakaoLoginService {

    private final KakaoUtil kakaoUtil;
    private final TokenProvider tokenProvider;
    private final RefreshTokenUtil refreshTokenUtil;
    private final MemberRepository memberRepository;

    // 인증코드 얻기
    @Transactional(readOnly = true)
    public String getLoginUrl() {
        return kakaoUtil.buildLoginUrl();
    }

    @Transactional
    public LoginResponseDto login(String code, HttpServletResponse response) {
        KakaoTokenDto kakaoToken = kakaoUtil.requestAccessToken(code);
        String token = kakaoToken.getAccessToken();
        KakaoInfoResponseDto profile = kakaoUtil.requestProfile(token);

        String email = profile.getKakaoAccount().getEmail();

        // 기존 회원인지 아닌지에 따라 로직 구분
        log.info("{} -> 기존 회원인지 조회", email);
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.info("신규 회원 등록 : {}}", email);
                    Member newMember = Member.builder()
                            .email(email)
                            .name(profile.getKakaoAccount().getName())
                            .role(Role.ROLE_USER)
                            .type(Type.KAKAO)
                            .build();
                    return memberRepository.save(newMember);
                });

        // 토큰 발급
        String accessToken = tokenProvider.generateAccessToken(member);
        String refreshToken = tokenProvider.generateRefreshToken(member);
        // Refresh Token 쿠키로 설정
        refreshTokenUtil.addRefreshTokenCookie(response, refreshToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .type(member.getType())
                .build();
    }
}