package com.ppiyong.backend.api.member.service;

import com.ppiyong.backend.api.member.dto.request.MypageRequestDto;
import com.ppiyong.backend.api.member.dto.response.MypageResponseDto;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    // 회원 정보를 추가하기(수정)
    @Override
    @Transactional
    public MypageResponseDto addMemberInfo(String token, MypageRequestDto requestDto) throws CustomException {

        Long memberId = tokenProvider.getMemberIdFromToken(token);
        try {
            tokenProvider.validationToken(token);
        } catch (CustomException e) {
            throw e;
        }

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateMember(
                requestDto.phoneNumber(),
                requestDto.username(),
                requestDto.address(),
                requestDto.parentPhoneNumber()
        );
        memberRepository.save(member);
        return Member.toMypageDto(member);
    }

    // 나의 정보 조회하기
    @Override
    @Transactional(readOnly = true)
    public MypageResponseDto getMyInfo(String token) throws CustomException {

        try {
            Long memberId = tokenProvider.getMemberIdFromToken(token);

            Member member = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

            return Member.toMypageDto(member);
        } catch (CustomException e) {
            throw e;
        }
    }
}
