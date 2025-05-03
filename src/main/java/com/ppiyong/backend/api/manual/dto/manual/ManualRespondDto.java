package com.ppiyong.backend.api.manual.dto.manual;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualRespondDto {
    private Long manualId;
    private String name;
    private String manualSummary;
    private String imgurl;
    private Boolean isLiked;
}
