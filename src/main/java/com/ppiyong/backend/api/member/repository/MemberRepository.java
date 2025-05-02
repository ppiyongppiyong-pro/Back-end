package com.ppiyong.backend.api.member.repository;

import com.ppiyong.backend.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);


}
