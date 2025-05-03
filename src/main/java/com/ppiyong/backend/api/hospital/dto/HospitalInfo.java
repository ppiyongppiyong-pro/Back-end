package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.HospitalInfoOnMap;
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

    public static HospitalInfo of(HospitalInfoOnMap hospitalInfoOnMap, Boolean isLike) {
        return new HospitalInfo(
                hospitalInfoOnMap.getId(),
                hospitalInfoOnMap.getPlaceName(),
                hospitalInfoOnMap.getAddressName(),
                hospitalInfoOnMap.getRoadAddressName(),
                extractDepartment(hospitalInfoOnMap.getCategoryName()),
                hospitalInfoOnMap.getPhone(),
                hospitalInfoOnMap.getPointX(),
                hospitalInfoOnMap.getPointY(),
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
    public static HospitalInfo createDocument(HospitalInfoOnMap hospitalInfoOnMap, boolean isLike) {
        return new HospitalInfo(
                hospitalInfoOnMap.getId(),
                hospitalInfoOnMap.getPlaceName(),
                hospitalInfoOnMap.getAddressName(),
                hospitalInfoOnMap.getRoadAddressName(),
                extractDepartment(hospitalInfoOnMap.getCategoryName()), // 수정: Department.getCategoryName() → extractDepartment
                hospitalInfoOnMap.getPhone(),
                hospitalInfoOnMap.getPointX(),
                hospitalInfoOnMap.getPointY(),
                isLike
        );
    }

    // final 변수이므로 setter/변경 메서드 삭제
    // public void likedDocument() { ... }  // 삭제
}
