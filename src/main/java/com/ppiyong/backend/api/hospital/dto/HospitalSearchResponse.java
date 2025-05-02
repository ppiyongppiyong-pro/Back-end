package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.Document;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.KakaoCategorySearchResponse;
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
            KakaoCategorySearchResponse response,
            List<Document> documents,
            Set<Long> likedHospitalIds // 추가
    ) {
        HospitalSearchMeta meta = new HospitalSearchMeta(
                response.getMeta().getEnd(),
                response.getMeta().getPageableCount()
        );

        // isLike 값을 likedHospitalIds 기반으로 설정
        List<HospitalInfo> hospitalInfos = documents.stream()
                .map(document ->
                        HospitalInfo.of(
                                document,
                                likedHospitalIds.contains(document.getId()) // isLike 계산
                        )
                )
                .toList();

        return new HospitalSearchResponse(meta, hospitalInfos);
    }
}
