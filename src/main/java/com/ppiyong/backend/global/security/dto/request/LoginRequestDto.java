package com.ppiyong.backend.global.security.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record LoginRequestDto (
        @Schema(description = "이메일",
                example = "ppiyong@example.com")
        String email,
        @Schema(description = "비밀번호",
                example = "1234")
        String password
) {
}

