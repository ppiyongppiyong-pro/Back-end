package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.Document;
import lombok.Getter;

@Getter
public class HospitalInfo {
    private final Long id;
    private final String placeName;
    private final String addressName;
    private final String roadAddressName;
    private final Department department;
    private final String phone;
    private final Float x;
    private final Float y;

    public HospitalInfo(Long id, String placeName, String addressName, String roadAddressName,
                        Department department, String phone, Float x, Float y) {
        this.id = id;
        this.placeName = placeName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.department = department;
        this.phone = phone;
        this.x = x;
        this.y = y;
    }

    public static HospitalInfo of(Document document) {
        return new HospitalInfo(
                document.getId(),
                document.getPlaceName(),
                document.getAddressName(),
                document.getRoadAddressName(),
                extractDepartment(document.getCategoryName()),
                document.getPhone(),
                document.getX(),
                document.getY()
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
}
