package com.ppiyong.backend.api.hospital.dto.KakaoRestApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoCategorySearchResponse {
    private Meta meta;
    private List<Document> documents;
}
