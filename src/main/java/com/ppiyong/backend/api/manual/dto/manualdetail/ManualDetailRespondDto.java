package com.ppiyong.backend.api.manual.dto.manualdetail;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ManualDetailRespondDto {
    private Long manualId;
    private String name;
    private String detail;
}
