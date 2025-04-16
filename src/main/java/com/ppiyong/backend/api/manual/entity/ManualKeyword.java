package com.ppiyong.backend.api.manual.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "manual_keyword")
public class ManualKeyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manual_keyword_id")
    private Long id;
}
