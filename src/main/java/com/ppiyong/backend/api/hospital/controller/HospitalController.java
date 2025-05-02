package com.ppiyong.backend.api.hospital.controller;

import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;
import com.ppiyong.backend.api.hospital.service.HospitalService;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import com.ppiyong.backend.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponse<HospitalSearchResponse>> getHospitals(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "15") Integer size,
            @RequestParam Float x,
            @RequestParam Float y,
            @RequestParam String categoryName,
            @RequestHeader(name = "Authorization") String authToken
    ) {
        // 파라미터 유효성 검사
        if (authToken == null || authToken.isEmpty()) {
            throw CustomException.of(ErrorCode.EMPTY_TOKEN);
        }
        if (x == null) {
            throw CustomException.of(ErrorCode.MISSING_X_COORDINATE);
        }
        if (y == null) {
            throw CustomException.of(ErrorCode.MISSING_Y_COORDINATE);
        }

        // 카테고리 변환
        Department department = null;
        if (categoryName != null && !"진료과 선택".equals(categoryName)) {
            department = Department.from(categoryName);
        }

        // 서비스 호출 및 응답 포맷팅
        HospitalSearchResponse response = hospitalService.searchHospitals(authToken, page, size, x, y, department);
        return ResponseEntity.ok(CommonResponse.ok(response));
    }
}
