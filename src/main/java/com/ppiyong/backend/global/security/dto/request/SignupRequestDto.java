package com.ppiyong.backend.global.security.dto.request;

import com.ppiyong.backend.api.member.common.Role;
import com.ppiyong.backend.api.member.common.Type;
import com.ppiyong.backend.api.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "회원가입 Request DTO")
@Builder
public record SignupRequestDto (
        @Schema(name = "이메일", example = "aaa1111@example.com")
        String email,
        @Schema(name = "사용자 이름", example = "홍길동")
        String username,
        @Schema(name = "비밀번호", example = "1234")
        String password,
        @Schema(name = "전화번호", example = "010-1111-1111")
        String phoneNumber,
        @Schema(name = "성별", example = "female")
        String gender,
        @Schema(name = "보호자 전화번호", example = "010-1111-1111")
        String parentPhoneNumber,
        @Schema(name = "거주 주소", example = "서울특별시")
        String address,
        @Schema(name = "가입 형식", example = "FORM")
        Type type,
        @Schema(name = "가입 권한", example = "ROLE_USER")
        Role role
) {
        public Member toEntity() {
                return Member.builder()
                        .email(this.email())
                        .password(this.password())
                        .name(this.username())  // 여기서 'this'는 record의 getter를 사용합니다
                        .phoneNumber(this.phoneNumber())
                        .gender(this.gender())
                        .parentPhoneNumber(this.parentPhoneNumber())
                        .address(this.address())
                        .type(this.type())
                        .role(this.role())
                        .build();
        }
}