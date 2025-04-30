package com.ppiyong.backend.api.manual.entity;

import com.ppiyong.backend.api.manual.common.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manual")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Manual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manual_id")
    private Long manualId;

    private String name;
    @Column(name = "manual_summary")
    private String manualSummary;
    @Lob
    private String detail;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String keyword;
    private String imgurl;
}
