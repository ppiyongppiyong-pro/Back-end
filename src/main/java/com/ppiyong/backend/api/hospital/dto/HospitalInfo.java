package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.Document;
import com.ppiyong.backend.api.hospital.entity.Hospital;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class HospitalInfo {
    private final Long id;
    private final String placeName;
    private final String addressName;
    private final String roadAddressName;
    private final Department department;
    private final String phone;
    private final BigDecimal pointX;
    private final BigDecimal pointY;
    private final Boolean isLike;

    public HospitalInfo(Long id, String placeName, String addressName, String roadAddressName,
                        Department department, String phone, BigDecimal pointX, BigDecimal pointY, Boolean isLike) {
        this.id = id;
        this.placeName = placeName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.department = department;
        this.phone = phone;
        this.pointX = pointX;
        this.pointY = pointY;
        this.isLike = isLike;
    }

    public static HospitalInfo of(Document document, Boolean isLike) {
        return new HospitalInfo(
                document.getId(),
                document.getPlaceName(),
                document.getAddressName(),
                document.getRoadAddressName(),
                extractDepartment(document.getCategoryName()),
                document.getPhone(),
                document.getPointX(),
                document.getPointY(),
                isLike
        );
    }

    // "의료,건강 > 병원 > 내과" → "내과"만 추출해서 Enum 매칭
    private static Department extractDepartment(String categoryName)  {
        if (categoryName == null) return null;
        String[] parts = categoryName.split(">");
        String last = parts[parts.length - 1].trim();
        try {
            return Department.from(last);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // 병원 즐겨찾기 목록 변환
    public static HospitalInfo of(Hospital hospital, Boolean isLike) {
        return new HospitalInfo(
                hospital.getHospitalId(),
                hospital.getPlaceName(),
                hospital.getAddressName(),
                hospital.getRoadAddressName(),
                hospital.getCategoryName(),
                hospital.getPhone(),
                hospital.getPointX(),
                hospital.getPointY(),
                isLike
        );
    }

    // 문서 변환 (카카오 API 등)
    public static HospitalInfo createDocument(Document document, boolean isLike) {
        return new HospitalInfo(
                document.getId(),
                document.getPlaceName(),
                document.getAddressName(),
                document.getRoadAddressName(),
                extractDepartment(document.getCategoryName()), // 수정: Department.getCategoryName() → extractDepartment
                document.getPhone(),
                document.getPointX(),
                document.getPointY(),
                isLike
        );
    }

    // final 변수이므로 setter/변경 메서드 삭제
    // public void likedDocument() { ... }  // 삭제
}
