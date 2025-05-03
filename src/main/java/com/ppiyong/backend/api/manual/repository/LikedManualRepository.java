package com.ppiyong.backend.api.manual.repository;

import com.ppiyong.backend.api.manual.entity.LikedManual;
import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.api.manual.entity.Manual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedManualRepository extends JpaRepository<LikedManual, Long> {
    Optional<LikedManual> findByMemberAndManual(Member member, Manual manual);

    List<LikedManual> findByMember(Member member);

    List<LikedManual> findByMemberAndIsLikeTrue(Member member);

}
