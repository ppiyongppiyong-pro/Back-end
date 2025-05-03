package com.ppiyong.backend.global.security.service;

import com.ppiyong.backend.api.member.common.Role;
import com.ppiyong.backend.api.member.common.Type;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import com.ppiyong.backend.global.security.dto.request.LoginRequestDto;
import com.ppiyong.backend.global.security.dto.request.SignupRequestDto;
import com.ppiyong.backend.global.security.dto.response.LoginResponseDto;
import com.ppiyong.backend.global.security.dto.response.SignupResponseDto;
import com.ppiyong.backend.global.security.util.RefreshTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class FormLoginServiceImpl implements FormLoginService{

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenUtil refreshTokenUtil;

    // 회원 가입 서비스
    @Transactional
    public SignupResponseDto register(SignupRequestDto requestDto) throws CustomException {

        // 회원 가입 여부 확인
        if (memberRepository.existsByEmail(requestDto.email())) {
            log.warn("이미 존재하는 회원입니다.");
            throw CustomException.of(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        log.info("{" + requestDto.email() + "} :  회원 가입 시작");
        String encodedPassword = this.passwordEncoder.encode(requestDto.password());
        SignupRequestDto updatedRequestDto = new SignupRequestDto(
                requestDto.email(),
                requestDto.username(),
                encodedPassword,
                requestDto.phoneNumber(),
                requestDto.gender(),
                requestDto.parentPhoneNumber(),
                requestDto.address()
        );

        Member member = updatedRequestDto.toEntity();
        memberRepository.save(member);

        log.info("{" + requestDto.email() + "} : 회원 가입 완료");
        return Member.toSignupDto(member);
    }

    @Transactional
    public LoginResponseDto generalLogin(
            LoginRequestDto requestDto,
            HttpServletResponse httpResponse
    ) throws CustomException{

        // 가입된 회원인지 조회
        log.info(requestDto.email() + "의 회원 조회를 시작합니다.");
        Member member = memberRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 검증
        log.info("비밀번호 검증을 시작합니다.");
        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.UNMATCHED_PASSWORD);
        }
        log.info("로그인에 성공하였습니다.");

        String accessToken = tokenProvider.generateAccessToken(member);
        String refreshToken = tokenProvider.generateRefreshToken(member);
        refreshTokenUtil.saveRefreshToken(member.getMemberId(), refreshToken);
        refreshTokenUtil.addRefreshTokenCookie(httpResponse, refreshToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .type(member.getType())
                .build();
    }

    // 로그아웃
    @Transactional
    public void logout(HttpServletResponse response, String token) {
        Long memberId = tokenProvider.getMemberIdFromToken(token);

        refreshTokenUtil.removeRefreshTokenCookie(response, memberId);
        refreshTokenUtil.deleteRefreshToken(memberId);
    }

    // 액세스 토큰 재발급
    @Transactional
    public String refresh(
            HttpServletRequest request, HttpServletResponse response
    ) throws CustomException {
        Long memberId = refreshTokenUtil.getMemberIdFromCookie(request);

        log.info(memberId + "의 회원을 조회합니다.");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        log.info("Access Token을 재발급합니다.");
        return tokenProvider.generateAccessToken(member);
    }
}
