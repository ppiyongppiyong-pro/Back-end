package com.ppiyong.backend.api.hospital.controller;

import com.ppiyong.backend.api.hospital.dto.HospitalSaveRequest;
import com.ppiyong.backend.api.hospital.service.LikeHospitalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hospitals")
@RequiredArgsConstructor
public class LikeHospitalController {

    private final LikeHospitalService likeHospitalService;

    @Operation(summary = "근처 병원 좋아요")
    @PostMapping("/like")
    public void likeHospital(
            @RequestHeader("Authorization") String authToken,
            @RequestBody HospitalSaveRequest hospitalSaveRequest) {
        likeHospitalService.like(authToken, hospitalSaveRequest);
    }

    @Operation(summary = "근처 병원 좋아요 취소하기")
    @DeleteMapping("/unlike/{hospitalId}")
    public void unlikeHospital(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("hospitalId") Long hospitalId) {
        likeHospitalService.unlike(authToken, hospitalId);
    }

    @Operation(summary = "맵 좋아요 조회하기")
    @GetMapping("/liked")
    public List<HospitalSaveRequest> getLikedHospitals(
            @RequestHeader("Authorization") String authToken) {
        return likeHospitalService.getLikedHospitals(authToken);
    }
}
