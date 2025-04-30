package com.ppiyong.backend.api.manual.service;

import com.ppiyong.backend.api.manual.dto.manual.ManualRespondDto;
import com.ppiyong.backend.api.manual.entity.LikedManual;
import com.ppiyong.backend.api.manual.entity.Manual;
import com.ppiyong.backend.api.manual.mapper.ManualMapper;
import com.ppiyong.backend.api.manual.repository.LikedManualRepository;
import com.ppiyong.backend.api.manual.repository.ManualRepository;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LikedManualService {
    private final LikedManualRepository likedManualRepository;
    private final ManualRepository manualRepository;
    private final MemberRepository memberRepository;
    private final ManualMapper manualMapper;
    private final TokenProvider tokenProvider;

    // 1. 매뉴얼 좋아요 추가
    public void likeManual(String authToken, Long manualId) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Manual manual = manualRepository.findById(manualId)
                .orElseThrow(() -> new RuntimeException("매뉴얼을 찾을 수 없습니다."));

        LikedManual likedManual = likedManualRepository.findByMemberAndManual(member, manual)
                .orElse(LikedManual.builder()
                        .member(member)
                        .manual(manual)
                        .build());

        likedManual.setIsLike(true);
        likedManualRepository.save(likedManual);
    }

    // 2. 매뉴얼 좋아요 취소
    public void unlikeManual(String authToken, Long manualId) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Manual manual = manualRepository.findById(manualId)
                .orElseThrow(() -> new RuntimeException("매뉴얼을 찾을 수 없습니다."));

        LikedManual likedManual = likedManualRepository.findByMemberAndManual(member, manual)
                .orElseThrow(() -> new RuntimeException("좋아요 정보가 없습니다."));

        likedManual.setIsLike(false);
        likedManualRepository.save(likedManual);
    }

    // 3. 내가 좋아요한 매뉴얼 목록 조회
    @Transactional(readOnly = true)
    public List<ManualRespondDto> getLikedManuals(String authToken) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        List<LikedManual> likedManuals = likedManualRepository.findByMemberAndIsLikeTrue(member);

        return likedManuals.stream()
                .map(lm -> {
                    ManualRespondDto dto = manualMapper.toDto(lm.getManual());
                    dto.setIsLiked(true);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
