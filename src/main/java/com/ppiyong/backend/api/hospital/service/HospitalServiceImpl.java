package com.ppiyong.backend.api.hospital.service;

import com.ppiyong.backend.api.hospital.domain.CategoryGroupCode;
import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.Document;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.KakaoCategorySearchResponse;
import com.ppiyong.backend.api.hospital.entity.LikedHospital;
import com.ppiyong.backend.api.hospital.repository.HospitalRepository;
import com.ppiyong.backend.api.hospital.repository.LikedHospitalRepository;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public HospitalSearchResponse searchHospitals(String authToken, Integer page, Integer size, Float x, Float y, Department categoryName) {

        // 1. 사용자 정보 추출
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 2. 카카오 API로 병원 검색
        Department effectiveCategory = (categoryName != null && "진료과 선택".equals(categoryName.getDisplayName()))
                ? null : categoryName;
        String categoryNameValue = (effectiveCategory != null) ? effectiveCategory.getDisplayName() : null;

        KakaoCategorySearchResponse response = kakaoHospitalApiClient.searchHospitals(
                CategoryGroupCode.HP8.name(),
                x, y, categoryNameValue, radius, page, size, distance
        );

        // 3. 필터링된 문서 가져오기
        List<Document> filteredDocuments = response.getDocuments();
        if (effectiveCategory != null) {
            filteredDocuments = filteredDocuments.stream()
                    .filter(document ->
                            document.getCategoryName() != null &&
                                    extractDepartmentName(document.getCategoryName()).equals(effectiveCategory.getDisplayName())
                    )
                    .toList();
        }

        // 4. 사용자가 좋아요한 병원 ID 목록 조회
        List<LikedHospital> likedHospitals = likedHospitalRepository.findByMemberAndIsLikeTrue(member);
        Set<Long> likedHospitalIds = likedHospitals.stream()
                .map(lh -> lh.getHospital().getHospitalId())
                .collect(Collectors.toSet());

        // 5. 응답 생성 (likedHospitalIds 전달)
        return HospitalSearchResponse.ofFiltered(response, filteredDocuments, likedHospitalIds); // 수정
    }

    private String extractDepartmentName(String categoryName) {
        if (categoryName == null) return null;
        String[] parts = categoryName.split(">");
        return parts[parts.length - 1].trim();
    }
}
