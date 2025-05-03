package com.ppiyong.backend.api.manual.dto.manualkeyword;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualKeywordRespondDto {
    private Long manualId;
    private String name;
    private String manualSummary;
    private String imgurl;
}
