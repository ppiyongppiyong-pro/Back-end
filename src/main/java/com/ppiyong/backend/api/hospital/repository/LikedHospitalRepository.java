package com.ppiyong.backend.api.hospital.repository;

import com.ppiyong.backend.api.hospital.entity.Hospital;
import com.ppiyong.backend.api.hospital.entity.LikedHospital;
import com.ppiyong.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedHospitalRepository extends JpaRepository<LikedHospital, Long> {
    Optional<LikedHospital> findByMemberAndHospital(Member member, Hospital hospital);
    List<LikedHospital> findByMemberAndIsLikeTrue(Member member);
}
