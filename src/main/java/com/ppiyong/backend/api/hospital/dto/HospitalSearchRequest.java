package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.domain.Department;
import lombok.Getter;

@Getter
public class HospitalSearchRequest {
    private Float x;
    private Float y;
    private Department categoryName;
    private Integer page;
    private Integer size;

    public HospitalSearchRequest(Float x, Float y, Department categoryName) {
        this.x = x;
        this.y = y;
        this.categoryName=categoryName;
    }

    public void initPage() {
        this.page = 1;
    }

    public void initSize() {
        this.size = 15;
    }


}