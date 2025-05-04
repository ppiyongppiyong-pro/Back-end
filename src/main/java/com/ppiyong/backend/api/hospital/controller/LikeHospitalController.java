package com.ppiyong.backend.api.hospital.controller;

import com.ppiyong.backend.api.hospital.dto.HospitalSaveRequest;
import com.ppiyong.backend.api.hospital.service.LikeHospitalService;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
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
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;

        likeHospitalService.like(token, hospitalSaveRequest);
        if (authToken == null || authToken.isEmpty()) {
            throw CustomException.of(ErrorCode.EMPTY_TOKEN);
        }
        likeHospitalService.like(authToken, hospitalSaveRequest);
    }

    @Operation(summary = "근처 병원 좋아요 취소하기")
    @DeleteMapping("/unlike/{hospitalId}")
    public void unlikeHospital(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("hospitalId") Long hospitalId) {

        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        likeHospitalService.unlike(token, hospitalId);
        if (authToken == null || authToken.isEmpty()) {
            throw CustomException.of(ErrorCode.EMPTY_TOKEN);
        }
        likeHospitalService.unlike(authToken, hospitalId);
    }

    @Operation(summary = "맵 좋아요 조회하기")
    @GetMapping("/liked")
    public List<HospitalSaveRequest> getLikedHospitals(
            @RequestHeader("Authorization") String authToken) {

        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;
        return likeHospitalService.getLikedHospitals(token);
        if (authToken == null || authToken.isEmpty()) {
            throw CustomException.of(ErrorCode.EMPTY_TOKEN);
        }
        return likeHospitalService.getLikedHospitals(authToken);
    }
}
