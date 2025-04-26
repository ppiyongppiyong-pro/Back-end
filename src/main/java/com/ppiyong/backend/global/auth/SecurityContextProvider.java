package com.ppiyong.backend.global.auth;

import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextProvider {

    public Long getCurrentMemberId() {
        return getCurrentMember().getMemberId();
    }

    // 현재 인증된 Member를 반환
    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof MemberAuthentication memberAuth)) {
            throw CustomException.of(ErrorCode.UNAUTHORIZED);
        }
        Member member = memberAuth.getMember();
        if (member == null) {
            throw CustomException.of(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }
}
