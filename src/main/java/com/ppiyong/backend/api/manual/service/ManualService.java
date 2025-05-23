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
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
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
    public List<ManualRespondDto> getManuals(String name, String authToken) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        List<Manual> manuals = (name == null || name.isBlank())
                ? manualRepository.findAll()
                : manualRepository.findByNameContaining(name);

        return manuals.stream()
                .map(manualMapper::toDto)
                .collect(Collectors.toList());
    }

    // 2. 검색 자동완성
    public List<String> autocomplete(String name, String authToken) {
        if (name == null || name.isBlank()) {
            throw CustomException.of(ErrorCode.MISSING_NAME_PARAM);
        }
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        return manualRepository.autocompleteByName(name);
    }

    // 3. 카테고리별 매뉴얼 조회
    public List<ManualCategoryRespondDto> getManualsByCategory(String category, String authToken) {
        if (category == null || category.isBlank()) {
            throw CustomException.of(ErrorCode.MISSING_CATEGORY_PARAM);
        }
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        Category cat = Category.fromDisplayName(category);
        List<Manual> manuals = manualRepository.findByCategory(cat);

        return manuals.stream()
                .map(m -> manualMapper.toCategoryDto(m, null))
                .collect(Collectors.toList());
    }

    // 4. 매뉴얼 상세 조회
    public ManualDetailRespondDto getManualDetail(String name, String authToken) {
        if (name == null || name.isBlank()) {
            throw CustomException.of(ErrorCode.MISSING_PATH_VARIABLE);
        }
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        Manual manual = manualRepository.findByName(name)
                .orElseThrow(() -> CustomException.of(ErrorCode.MANUAL_NOT_FOUND));
        return manualMapper.toDetailDto(manual);
    }

    // 5. 키워드로 매뉴얼 검색
    public List<ManualKeywordRespondDto> searchByKeyword(String keyword, String authToken) {
        if (keyword == null || keyword.isBlank()) {
            throw CustomException.of(ErrorCode.MISSING_KEYWORD_PARAM);
        }
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

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
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));
        Manual manual = manualRepository.findByName(name)
                .orElseThrow(() -> CustomException.of(ErrorCode.MANUAL_NOT_FOUND));

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
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));
        Manual manual = manualRepository.findByName(name)
                .orElseThrow(() -> CustomException.of(ErrorCode.MANUAL_NOT_FOUND));

        LikedManual likedManual = likedManualRepository.findByMemberAndManual(member, manual)
                .orElseThrow(() -> CustomException.of(ErrorCode.MANUAL_IS_EMPTY)); //즐겨찾기 매뉴얼이 비어있음

        likedManual.setIsLike(false);
        likedManualRepository.save(likedManual);
    }

    // 8. 내가 좋아요한 매뉴얼 목록 조회
    public List<ManualRespondDto> getLikedManuals(String authToken) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

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
