package com.ppiyong.backend.api.hospital.dto;

import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.Document;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.KakaoCategorySearchResponse;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class HospitalSearchResponse {
    private final HospitalSearchMeta meta;
    private final List<HospitalInfo> hospitals;

    private HospitalSearchResponse(HospitalSearchMeta meta, List<HospitalInfo> hospitals) {
        this.meta = meta;
        this.hospitals = hospitals;
    }

    public static HospitalSearchResponse ofFiltered(KakaoCategorySearchResponse response, List<Document> documents) {
        HospitalSearchMeta meta = new HospitalSearchMeta(
                response.getMeta().getEnd(),
                response.getMeta().getPageableCount()
        );

        List<HospitalInfo> hospitalInfos = documents.stream()
                .map(HospitalInfo::of)
                .toList();

        return new HospitalSearchResponse(meta, hospitalInfos);
    }
}
