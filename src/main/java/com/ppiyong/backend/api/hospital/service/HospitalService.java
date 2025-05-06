package com.ppiyong.backend.api.hospital.service;


import com.ppiyong.backend.api.hospital.domain.Department;
import com.ppiyong.backend.api.hospital.dto.HospitalSearchResponse;

public interface HospitalService {
    HospitalSearchResponse searchHospitals(String authToken, Integer page, Integer size, Float x, Float y, String categoryName );
}