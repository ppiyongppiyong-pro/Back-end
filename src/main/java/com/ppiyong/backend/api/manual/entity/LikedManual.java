package com.ppiyong.backend.api.manual.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "liked_manual")
public class LikedManual {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_manual_id")
    private Long likedManualId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manual")
    private Manual manual;

    @Column(name = "is_like")
    private Boolean isLike;
}
