package com.ppiyong.backend.api.manual.entity;

import com.ppiyong.backend.api.common.BaseEntity;
import com.ppiyong.backend.api.manual.common.Category;
import jakarta.persistence.*;

import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "manual", cascade = CascadeType.ALL)
    private List<ManualKeyword> manualKeywordList;

    @Column(name = "imgurl")
    private String imgurl;

}
