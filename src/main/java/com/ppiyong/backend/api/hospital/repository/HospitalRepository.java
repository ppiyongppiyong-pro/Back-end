package com.ppiyong.backend.api.hospital.repository;

import com.ppiyong.backend.api.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, String> {
    Optional<Hospital> findByPlaceId(String placeId);
    List<Hospital> findByPlaceIdIn(List<String> placeIds);
}