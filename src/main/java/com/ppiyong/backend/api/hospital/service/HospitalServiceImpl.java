package com.ppiyong.backend.api.hospital.service;

import com.ppiyong.backend.api.hospital.domain.CategoryGroupCode;
import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.HospitalInfoOnMap;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.MapHospitalSearchResult;
import com.ppiyong.backend.api.hospital.entity.LikedHospital;
import com.ppiyong.backend.api.hospital.repository.HospitalRepository;
import com.ppiyong.backend.api.hospital.repository.LikedHospitalRepository;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final KakaoHospitalApiClient kakaoHospitalApiClient;
    private final HospitalRepository hospitalRepository;
    private final LikedHospitalRepository likedHospitalRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private static final Integer radius = 10000;
    private static final String distance = "distance";

    @Override
    public HospitalSearchResponse searchHospitals(String authToken, Integer page, Integer size, Float x, Float y, String categoryName) {
        // 0. 파라미터 검증
        validateParameters(x, y, categoryName);

        // 1. 사용자 정보 추출
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> CustomException.of(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 카테고리 변환
        Department effectiveCategory = convertCategory(categoryName);

        // 3. 카카오 API로 병원 검색
        String categoryNameValue = (effectiveCategory != null) ? effectiveCategory.getDisplayName() : null;
        MapHospitalSearchResult response = kakaoHospitalApiClient.searchHospitals(
                CategoryGroupCode.HP8.name(),
                x, y, categoryNameValue, radius, page, size, distance
        );

        // 4. 필터링된 문서 가져오기 (null-safe)
        List<HospitalInfoOnMap> filteredHospitalInfoOnMaps =
                response.getHospitalInfoOnMaps() != null
                        ? response.getHospitalInfoOnMaps()
                        : Collections.emptyList();

        if (effectiveCategory != null) {
            filteredHospitalInfoOnMaps = filteredHospitalInfoOnMaps.stream()
                    .filter(hospitalInfoOnMap ->
                            hospitalInfoOnMap.getCategoryName() != null &&
                                    extractDepartmentName(hospitalInfoOnMap.getCategoryName()).equals(effectiveCategory.getDisplayName())
                    )
                    .toList();
        }

        // 5. 사용자가 좋아요한 병원 ID 목록 조회
        List<LikedHospital> likedHospitals = likedHospitalRepository.findByMemberAndIsLikeTrue(member);
        Set<Long> likedHospitalIds = likedHospitals.stream()
                .map(lh -> lh.getHospital().getHospitalId())
                .collect(Collectors.toSet());

        // 6. 응답 생성 (likedHospitalIds 전달)
        return HospitalSearchResponse.ofFiltered(response, filteredHospitalInfoOnMaps, likedHospitalIds);
    }

    private void validateParameters(Float x, Float y, String categoryName) {
        if (x == null) throw CustomException.of(ErrorCode.MISSING_X_COORDINATE);
        if (y == null) throw CustomException.of(ErrorCode.MISSING_Y_COORDINATE);
        if (categoryName == null || categoryName.isBlank()) {
            throw CustomException.of(ErrorCode.MISSING_CATEGORY_NAME);
        }
    }

    private Department convertCategory(String categoryName) {
        if ("진료과 선택".equals(categoryName)) {
            return null;
        }
        return Department.from(categoryName);
    }

    private String extractDepartmentName(String categoryName) {
        if (categoryName == null) return null;
        String[] parts = categoryName.split(">");
        return parts[parts.length - 1].trim();
    }
}
