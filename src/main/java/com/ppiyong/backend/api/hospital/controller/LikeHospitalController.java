package com.ppiyong.backend.api.hospital.controller;

import com.ppiyong.backend.api.hospital.dto.HospitalSaveRequest;
import com.ppiyong.backend.api.hospital.service.LikeHospitalService;
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


    @Operation(summary = "근처 병원 좋아요", description = """
            좋아요할 병원정보 기반으로 좋아요를 추가합니다.<br>
            헤더에 accessToken을 넣어주세요.<br>
            """)
    @PostMapping("/like")
    public ResponseEntity<?> likeHospital(@RequestHeader("Authorization") String authToken,
                                          @RequestBody HospitalSaveRequest hospitalSaveRequest) {
        return likeHospitalService.like(authToken, hospitalSaveRequest);
    }



    @Operation(summary = "근처 병원 좋아요 취소하기", description = """
            취소할 병원아이디 기반으로 좋아요를 취소합니다.<br>
            헤더에 accessToken을 넣어주세요.<br>
            """)
    @PostMapping("/unlike/{hospitalId}")
    public ResponseEntity<?> unlikeHospital(@RequestHeader("Authorization") String authToken,
                                            @PathVariable("hospitalId") Long hospitalId) {
        return likeHospitalService.unlike(authToken, hospitalId);
    }


    @Operation(summary = "맵 좋아요 조회하기", description = """
            맵 좋아요를 조회합니다.<br>
            헤더에 accessToken을 넣어주세요.
            """)
    @GetMapping("/liked")
    public ResponseEntity<?> getLikedHospitals(@RequestHeader("Authorization") String authToken) {
        return likeHospitalService.getLikedHospitals(authToken);
    }
}
