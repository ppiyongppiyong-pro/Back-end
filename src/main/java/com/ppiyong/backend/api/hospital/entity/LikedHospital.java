package com.ppiyong.backend.api.hospital.entity;

import com.ppiyong.backend.api.member.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "liked_hospital")
public class LikedHospital {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_hospital_id")
    private Long likedHospitalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_like")
    private Boolean isLike;
}
