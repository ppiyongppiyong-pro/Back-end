package com.ppiyong.backend.api.manual.entity;

import com.ppiyong.backend.api.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "manual")
public class Manual extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manual_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "manual_summary")
    private String manualSummary;

    @Column(name = "detail")
    private String detail;

    @Column(name = "category")
    private String category;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "imgurl")
    private String imgurl;

}
