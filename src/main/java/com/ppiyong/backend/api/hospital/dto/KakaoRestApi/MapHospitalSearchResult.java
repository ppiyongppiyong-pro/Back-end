package com.ppiyong.backend.api.hospital.dto.KakaoRestApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MapHospitalSearchResult {
    @JsonProperty("meta") //meta 꼭 쓰기
    private PaginationInfo paginationInfo;

    @JsonProperty("documents") //documents 꼭 쓰기
    private List<HospitalInfoOnMap> hospitalInfoOnMaps;
}
