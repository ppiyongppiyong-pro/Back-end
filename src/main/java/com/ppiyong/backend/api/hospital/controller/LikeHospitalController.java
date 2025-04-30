package com.ppiyong.backend.api.hospital.controller;

import com.ppiyong.backend.api.hospital.dto.HospitalSaveRequest;
import com.ppiyong.backend.api.hospital.service.LikeHospitalService;
import com.ppiyong.backend.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
public class LikeHospitalController {
    private final LikeHospitalService likeHospitalService;

    @Operation(summary = "근처 병원 좋아요")
    @PostMapping("/like")
    public ResponseEntity<CommonResponse<Void>> likeHospital(
            @RequestHeader("Authorization") String authToken,
            @RequestBody HospitalSaveRequest hospitalSaveRequest) {
        likeHospitalService.like(authToken, hospitalSaveRequest);
        return ResponseEntity.ok(CommonResponse.ok(null));
    }

    @Operation(summary = "근처 병원 좋아요 취소하기")
    @DeleteMapping("/unlike/{hospitalId}")
    public ResponseEntity<CommonResponse<Void>> unlikeHospital(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("hospitalId") Long hospitalId) {
        likeHospitalService.unlike(authToken, hospitalId);
        return ResponseEntity.ok(CommonResponse.ok(null));
    }

    @Operation(summary = "맵 좋아요 조회하기")
    @GetMapping("/liked")
    public ResponseEntity<CommonResponse<List<HospitalSaveRequest>>> getLikedHospitals(
            @RequestHeader("Authorization") String authToken) {
        List<HospitalSaveRequest> data = likeHospitalService.getLikedHospitals(authToken);
        return ResponseEntity.ok(CommonResponse.ok(data));
    }
}
