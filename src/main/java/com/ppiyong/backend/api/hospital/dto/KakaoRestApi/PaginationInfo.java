package com.ppiyong.backend.api.hospital.dto.KakaoRestApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PaginationInfo {
    @JsonProperty("same_name")
    private SearchRegionInfo searchRegionInfo;

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("pageable_count")
    private Integer pageableCount;

    @JsonProperty("is_end")
    private Boolean end; // 소문자!
}


