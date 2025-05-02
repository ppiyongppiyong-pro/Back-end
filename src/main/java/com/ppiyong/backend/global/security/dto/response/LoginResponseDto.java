package com.ppiyong.backend.global.security.dto.response;

import com.ppiyong.backend.api.member.common.Role;
import com.ppiyong.backend.api.member.common.Type;
import lombok.Builder;

@Builder
public record LoginResponseDto (
        String accessToken,
        String email,
        String name,
        Role role,
        Type type
){
}
