package com.ppiyong.backend.api.hospital.dto.KakaoRestApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRegionInfo {
    private String[] region;

    private String keyword;

    @JsonProperty("selected_region")
    private String selectedRegion;
}