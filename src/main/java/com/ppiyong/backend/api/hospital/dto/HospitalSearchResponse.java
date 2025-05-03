package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.HospitalInfoOnMap;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.MapHospitalSearchResult;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class HospitalSearchResponse {
    private final HospitalSearchMeta meta;
    private final List<HospitalInfo> hospitals;

    private HospitalSearchResponse(HospitalSearchMeta meta, List<HospitalInfo> hospitals) {
        this.meta = meta;
        this.hospitals = hospitals;
    }

    // likedHospitalIds 추가
    public static HospitalSearchResponse ofFiltered(
            MapHospitalSearchResult response,
            List<HospitalInfoOnMap> hospitalInfoOnMaps,
            Set<Long> likedHospitalIds // 추가
    ) {
        HospitalSearchMeta meta = new HospitalSearchMeta(
                response.getPaginationInfo().getEnd(),
                response.getPaginationInfo().getPageableCount()
        );

        // isLike 값을 likedHospitalIds 기반으로 설정
        List<HospitalInfo> hospitalInfos = hospitalInfoOnMaps.stream()
                .map(hospitalInfoOnMap ->
                        HospitalInfo.of(
                                hospitalInfoOnMap,
                                likedHospitalIds.contains(hospitalInfoOnMap.getId()) // isLike 계산
                        )
                )
                .toList();

        return new HospitalSearchResponse(meta, hospitalInfos);
    }
}
