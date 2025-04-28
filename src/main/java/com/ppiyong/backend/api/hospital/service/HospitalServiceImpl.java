package com.ppiyong.backend.api.hospital.service;

import com.ppiyong.backend.api.hospital.domain.CategoryGroupCode;
import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.Document;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.KakaoCategorySearchResponse;
import com.ppiyong.backend.api.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final KakaoHospitalApiClient kakaoHospitalApiClient;
    private static final Integer radius = 10000;
    private static final String distance = "distance";
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalSearchResponse searchHospitals(String authToken, Integer page, Integer size, Float x, Float y, Department categoryName) {

        if (size <= 0 || size > 25) {
            throw new IllegalArgumentException("size는 1~25 사이의 값이어야 합니다.");
        }

        Department effectiveCategory = (categoryName != null && "진료과 선택".equals(categoryName.getDisplayName()))
                ? null : categoryName;

        String categoryNameValue = (effectiveCategory != null) ? effectiveCategory.getDisplayName() : null;

        KakaoCategorySearchResponse response = kakaoHospitalApiClient.searchHospitals(
                CategoryGroupCode.HP8.name(),
                x, y, categoryNameValue, radius, page, size, distance
        );

        List<Document> filteredDocuments = response.getDocuments();
        if (effectiveCategory != null) {
            filteredDocuments = filteredDocuments.stream()
                    .filter(document ->
                            document.getCategoryName() != null &&
                                    extractDepartmentName(document.getCategoryName()).equals(effectiveCategory.getDisplayName())
                    )
                    .toList();
        }

        return HospitalSearchResponse.ofFiltered(response, filteredDocuments);
    }

    // "의료,건강 > 병원 > 내과" → "내과"
    private String extractDepartmentName(String categoryName) {
        if (categoryName == null) return null;
        String[] parts = categoryName.split(">");
        return parts[parts.length - 1].trim();
    }
}
