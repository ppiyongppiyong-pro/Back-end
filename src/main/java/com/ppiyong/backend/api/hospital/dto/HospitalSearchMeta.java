package com.ppiyong.backend.api.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class HospitalSearchMeta {
    private Integer pageableCount;
    private Boolean end;

    public HospitalSearchMeta(Boolean end, Integer pageableCount) {
        this.end = end;
        this.pageableCount = pageableCount;
    }
}
