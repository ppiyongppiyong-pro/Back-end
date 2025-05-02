package com.ppiyong.backend.api.hospital.service;

import com.ppiyong.backend.api.hospital.dto.HospitalSaveRequest;
import com.ppiyong.backend.api.hospital.entity.Hospital;
import com.ppiyong.backend.api.hospital.entity.LikedHospital;
import com.ppiyong.backend.api.hospital.repository.HospitalRepository;
import com.ppiyong.backend.api.hospital.repository.LikedHospitalRepository;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeHospitalService {

    private final LikedHospitalRepository likedHospitalRepository;
    private final HospitalRepository hospitalRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void like(String authToken, HospitalSaveRequest request) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId).orElseThrow();

        Hospital hospital = hospitalRepository.findByHospitalId(request.getHospitalId())
                .orElseGet(() -> createHospitalFromRequest(request));

        LikedHospital likedHospital = likedHospitalRepository.findByMemberAndHospital(member, hospital)
                .orElse(new LikedHospital(member, hospital));

        likedHospital.like();
        likedHospitalRepository.save(likedHospital);
    }

    @Transactional
    public void unlike(String authToken, Long hospitalId) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId).orElseThrow();
        Hospital hospital = hospitalRepository.findByHospitalId(hospitalId).orElseThrow();

        LikedHospital likedHospital = likedHospitalRepository.findByMemberAndHospital(member, hospital)
                .orElseThrow(() -> new IllegalArgumentException("좋아요 내역 없음"));

        likedHospital.unlike();
        likedHospitalRepository.save(likedHospital);
    }

    public List<HospitalSaveRequest> getLikedHospitals(String authToken) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId).orElseThrow();

        return likedHospitalRepository.findByMemberAndIsLikeTrue(member).stream()
                .map(lh -> convertToSaveRequest(lh.getHospital()))
                .collect(Collectors.toList());
    }

    // ======================== 내부 메서드 ========================
    private Hospital createHospitalFromRequest(HospitalSaveRequest request) {
        return hospitalRepository.save(
                Hospital.builder()
                        .placeName(request.getPlaceName())
                        .addressName(request.getAddressName())
                        .roadAddressName(request.getRoadAddressName())
                        .hospitalId(request.getHospitalId())
                        .categoryName(request.getCategoryName())
                        .phone(request.getPhone())
                        .pointX(request.getPointX())
                        .pointY(request.getPointY())
                        .build()
        );
    }

    private HospitalSaveRequest convertToSaveRequest(Hospital hospital) {
        return HospitalSaveRequest.builder()
                .hospitalId(hospital.getHospitalId())
                .placeName(hospital.getPlaceName())
                .addressName(hospital.getAddressName())
                .roadAddressName(hospital.getRoadAddressName())
                .categoryName(hospital.getCategoryName())
                .phone(hospital.getPhone())
                .pointX(hospital.getPointX())
                .pointY(hospital.getPointY())
                .build();
    }
}
