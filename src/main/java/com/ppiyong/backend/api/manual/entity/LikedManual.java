package com.ppiyong.backend.api.manual.entity;

import com.ppiyong.backend.api.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "liked_manual")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LikedManual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_manual_id")
    private Long memberManualId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manual_id", nullable = false)
    private Manual manual;

    @Column(name = "is_like")
    private Boolean isLike;
}
