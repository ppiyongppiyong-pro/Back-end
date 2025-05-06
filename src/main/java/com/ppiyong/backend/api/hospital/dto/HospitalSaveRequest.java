package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.domain.Department;
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



}