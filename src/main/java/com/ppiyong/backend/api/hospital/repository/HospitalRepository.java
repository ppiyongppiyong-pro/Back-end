package com.ppiyong.backend.api.hospital.repository;

import com.ppiyong.backend.api.hospital.entity.Hospital;
import com.ppiyong.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByHospitalId(Long HospitalId);
}


