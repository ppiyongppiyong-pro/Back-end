package com.ppiyong.backend.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "마이페이지 응답 DTO")
public record MypageResponseDto(
        @Schema(description = "회원 이메일", example = "aaa1111@example.com")
        String email,
        @Schema(description = "회원 이름", example = "홍길동")
        String name,
        @Schema(description = "회원 전화번호", example = "010-1111-1111")
        String phoneNumber,
        @Schema(description = "회원 성별", example = "female",
                allowableValues = {"male", "female"})
        String gender,
        @Schema(description = "회원 보호자 전화번호",
        example = "010-1111-1111")
        String parentPhoneNumber,
        @Schema(description = "회원 거주 주소")
        String address,
        @Schema(description = "회원 주민등록번호",
        example = "011111-4")
        String residentNo
) {
}
