package com.ppiyong.backend.global.security.dto.response;

import lombok.Builder;

@Builder
public record SignupResponseDto(
        String email,
        String username,
        String phoneNumber,
        String gender,
        String parentPhoneNumber,
        String address
) {

}
