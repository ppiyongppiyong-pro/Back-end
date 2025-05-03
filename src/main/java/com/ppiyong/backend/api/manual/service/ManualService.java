package com.ppiyong.backend.api.manual.service;

import com.ppiyong.backend.api.manual.common.Category;
import com.ppiyong.backend.api.manual.dto.manual.ManualRespondDto;
import com.ppiyong.backend.api.manual.dto.manualcategory.ManualCategoryRespondDto;
import com.ppiyong.backend.api.manual.dto.manualdetail.ManualDetailRespondDto;
import com.ppiyong.backend.api.manual.dto.manualkeyword.ManualKeywordRespondDto;
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
@Transactional(readOnly = true)
public class ManualService {
    private final ManualRepository manualRepository;
    private final ManualMapper manualMapper;
    private final LikedManualRepository likedManualRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    // 1. 이름으로 매뉴얼 검색
    public List<ManualRespondDto> getManuals(String name) {
        List<Manual> manuals = (name == null || name.isBlank())
                ? manualRepository.findAll()
                : manualRepository.findByNameContaining(name);

        return manuals.stream()
                .map(manualMapper::toDto)
                .collect(Collectors.toList());
    }

    // 2. 검색 자동완성
    public List<String> autocomplete(String name) {
        return manualRepository.autocompleteByName(name);
    }

    // 3. 카테고리별 매뉴얼 조회
    public List<ManualCategoryRespondDto> getManualsByCategory(String category) {
        Category cat = Category.fromDisplayName(category);
        List<Manual> manuals = manualRepository.findByCategory(cat);

        return manuals.stream()
                .map(m -> manualMapper.toCategoryDto(m, null))
                .collect(Collectors.toList());
    }

    // 4. 매뉴얼 상세 조회
    public ManualDetailRespondDto getManualDetail(String name) {
        Manual manual = manualRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("매뉴얼을 찾을 수 없습니다."));
        return manualMapper.toDetailDto(manual);
    }

    // 5. 키워드로 매뉴얼 검색
    public List<ManualKeywordRespondDto> searchByKeyword(String keyword) {
        List<Manual> manuals = manualRepository.findByKeyword(keyword);
        return manuals.stream()
                .map(manualMapper::toKeywordDto)
                .collect(Collectors.toList());
    }

    // 6. 매뉴얼 좋아요 추가
    @Transactional
    public void likeManual(String authToken, String name) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Manual manual = manualRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("매뉴얼을 찾을 수 없습니다."));

        LikedManual likedManual = likedManualRepository.findByMemberAndManual(member, manual)
                .orElse(LikedManual.builder()
                        .member(member)
                        .manual(manual)
                        .build());

        likedManual.setIsLike(true);
        likedManualRepository.save(likedManual);
    }

    // 7. 매뉴얼 좋아요 취소
    @Transactional
    public void unlikeManual(String authToken, String name) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Manual manual = manualRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("매뉴얼을 찾을 수 없습니다."));

        LikedManual likedManual = likedManualRepository.findByMemberAndManual(member, manual)
                .orElseThrow(() -> new RuntimeException("좋아요 정보가 없습니다."));

        likedManual.setIsLike(false);
        likedManualRepository.save(likedManual);
    }

    // 8. 내가 좋아요한 매뉴얼 목록 조회
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
