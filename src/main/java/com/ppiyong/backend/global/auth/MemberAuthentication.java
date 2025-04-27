package com.ppiyong.backend.global.auth;

import com.ppiyong.backend.api.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class MemberAuthentication extends UsernamePasswordAuthenticationToken {

    private final Member member;

    public MemberAuthentication(Member member, Object credentials) {
        super(member.getMemberId(), credentials);
        this.member = member;
    }

    public MemberAuthentication(Member member, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(member.getMemberId(), credentials, authorities);
        this.member = member;
    }

    @Override
    public Object getPrincipal() {
        return member.getMemberId(); // PK인 member ID 반환
    }

    public Member getMember() {
        return this.member;
    }

    public static MemberAuthentication createMemberAuthentication(Member member) {

        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));

        return new MemberAuthentication(
                member,
                null,
                authorities
        );
    }
}
