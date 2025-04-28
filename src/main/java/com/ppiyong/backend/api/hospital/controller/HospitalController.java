package com.ppiyong.backend.api.hospital.controller;


import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;
import com.ppiyong.backend.api.hospital.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/map")
@RequiredArgsConstructor
@Tag(name = "EmergencyMap", description = "지도 관련 API")
public class HospitalController {

    private final HospitalService hospitalService;

    @Operation(summary = "근처 병원 조회", description = """
            위도와 경도를 기반으로 근처 병원 주소를 조회합니다.<br>
            헤더에 accessToken을 넣어주세요.<br>
            """)

    @GetMapping("/hospital")
    public ResponseEntity<HospitalSearchResponse> getHospitals(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "15") Integer size,
            @RequestParam Float x,
            @RequestParam Float y,
            @RequestParam String categoryName, // String으로 받기!
            @RequestHeader(name = "Authorization",required = true) String authToken
    ) {
        Department department = null;
        if (categoryName != null && !"진료과 선택".equals(categoryName)) {
            department = Department.from(categoryName); // displayName(한글) → Enum으로 변환
        }
        return ResponseEntity.ok().body(
                hospitalService.searchHospitals(authToken, page, size, x, y, department)
        );
    }

}