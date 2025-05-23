package com.ppiyong.backend.api.hospital.controller;

import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;
import com.ppiyong.backend.api.hospital.service.HospitalService;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
@Tag(name = "EmergencyMap", description = "지도 관련 API")
public class HospitalController {

    private final HospitalService hospitalService;

    @Operation(summary = "근처 병원 조회", description = """
            위도와 경도를 기반으로 근처 병원 주소를 조회합니다.<br>
            헤더에 accessToken을 넣어주세요.<br>
            """)
    @GetMapping("/hospital")
    public HospitalSearchResponse getHospitals(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "15") Integer size,
            @RequestParam Float x,
            @RequestParam Float y,
            @RequestParam String categoryName,
            @RequestHeader(name = "Authorization") String authToken
    ) {
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return hospitalService.searchHospitals(token, page, size, x, y, categoryName);
    }
}

