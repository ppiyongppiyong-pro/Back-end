package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.HospitalInfoOnMap;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.MapHospitalSearchResult;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.PaginationInfo;
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
        // PaginationInfo null 체크
        PaginationInfo paginationInfo = response.getPaginationInfo();
        boolean end = paginationInfo != null ? paginationInfo.getEnd() : false;
        int pageableCount = paginationInfo != null ? paginationInfo.getPageableCount() : 0;

        HospitalSearchMeta meta = new HospitalSearchMeta(end, pageableCount);


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
