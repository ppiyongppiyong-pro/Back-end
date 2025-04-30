package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.domain.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalSaveRequest {


    private Long hospitalId;


    private String placeName;

    private String addressName;

    private String roadAddressName;

    @NotNull(message = "진료과목은 필수입니다")
    private Department categoryName;

    private String phone;

    @NotNull(message = "x(경도)좌표는 필수입니다")
    private BigDecimal pointX;

    @NotNull(message = "y(위도)좌표는 필수입니다")
    private BigDecimal pointY;

    public void validateFields() {
        if (hospitalId == null ) {
            throw new IllegalArgumentException("placeId 값이 필요합니다.");
        }

        if (placeName == null || placeName.isEmpty()) {
            throw new IllegalArgumentException("placeName 값이 필요합니다.");
        }

        if (addressName == null || addressName.isEmpty()) {
            throw new IllegalArgumentException("addressName 값이 필요합니다.");
        }

        if (roadAddressName == null || roadAddressName.isEmpty()) {
            throw new IllegalArgumentException("roadAddressName 값이 필요합니다.");
        }

        if (categoryName == null ) {
            throw new IllegalArgumentException("categoryName 값이 필요합니다.");
        }

        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("phone 값이 필요합니다.");
        }

        if (pointX == null ) {
            throw new IllegalArgumentException("x 값이 필요합니다.");
        }

        if (pointY == null ) {
            throw new IllegalArgumentException("y 값이 필요합니다.");
        }
    }



}