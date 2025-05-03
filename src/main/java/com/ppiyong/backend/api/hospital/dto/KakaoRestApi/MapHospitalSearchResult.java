package com.ppiyong.backend.api.hospital.dto.KakaoRestApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MapHospitalSearchResult {
    private PaginationInfo paginationInfo;
    private List<HospitalInfoOnMap> hospitalInfoOnMaps;
}
