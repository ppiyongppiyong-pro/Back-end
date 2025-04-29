package com.ppiyong.backend.api.hospital.service;

import com.ppiyong.backend.api.hospital.dto.HospitalInfo;
import com.ppiyong.backend.api.hospital.dto.HospitalSaveRequest;
import com.ppiyong.backend.api.hospital.entity.Hospital;
import com.ppiyong.backend.api.hospital.entity.LikedHospital;
import com.ppiyong.backend.api.hospital.repository.HospitalRepository;
import com.ppiyong.backend.api.hospital.repository.LikedHospitalRepository;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.member.repository.MemberRepository;
import com.ppiyong.backend.global.auth.TokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeHospitalService {
    private final LikedHospitalRepository likedHospitalRepository;
    private final HospitalRepository hospitalRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity<?> like(String authToken, HospitalSaveRequest request) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId).orElseThrow();
        Hospital hospital = hospitalRepository.findByHospitalId(request.getHospitalId())
                .orElseGet(() -> hospitalRepository.save(
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
                ));

        Optional<LikedHospital> likeOpt = likedHospitalRepository.findByMemberAndHospital(member, hospital);
        if (likeOpt.isPresent()) {
            LikedHospital like = likeOpt.get();
            if (Boolean.TRUE.equals(like.getIsLike())) {
                return ResponseEntity.badRequest().body("이미 좋아요한 병원입니다.");
            } else {
                like.like();
                likedHospitalRepository.save(like);
                return ResponseEntity.ok("다시 좋아요 처리되었습니다.");
            }
        }

        LikedHospital newLike = new LikedHospital(member, hospital);
        likedHospitalRepository.save(newLike);
        return ResponseEntity.ok("즐겨찾기에 추가되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> unlike(String authToken, Long HospitalId) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId).orElseThrow();
        Hospital hospital = hospitalRepository.findByHospitalId(HospitalId).orElseThrow();

        LikedHospital like = likedHospitalRepository.findByMemberAndHospital(member, hospital)
                .orElseThrow(() -> new IllegalArgumentException("좋아요 내역 없음"));

        if (Boolean.FALSE.equals(like.getIsLike())) {
            return ResponseEntity.badRequest().body("이미 좋아요 취소된 병원입니다.");
        }

        like.unlike();
        likedHospitalRepository.save(like);
        return ResponseEntity.ok("즐겨찾기에서 병원이 취소되었습니다.");
    }

    public ResponseEntity<List<HospitalInfo>> getLikedHospitals(String authToken) {
        Long memberId = tokenProvider.getMemberIdFromToken(authToken);
        Member member = memberRepository.findById(memberId).orElseThrow();

        List<LikedHospital> likedList = likedHospitalRepository.findByMemberAndIsLikeTrue(member);
        List<HospitalInfo> likedHospitals = likedList.stream()
                .map(lh -> HospitalInfo.of(lh.getHospital(), true))
                .toList();

        return ResponseEntity.ok(likedHospitals);
    }
}
