package com.ppiyong.backend.api.manual.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "keyword")
public class Keyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<ManualKeyword> manualKeywordList;
}
