package com.ppiyong.backend.api.manual.dto.manualcategory;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualCategoryRespondDto {
    private Long manualId;
    private String name;
    private String manualSummary;
    private String imgurl;
    private String category;
    private Boolean isLiked;
}
