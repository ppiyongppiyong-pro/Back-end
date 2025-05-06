package com.ppiyong.backend.api.member.entity;

import com.ppiyong.backend.api.common.BaseEntity;
import com.ppiyong.backend.api.member.common.Role;
import com.ppiyong.backend.api.member.common.Type;
import com.ppiyong.backend.api.member.dto.response.MypageResponseDto;
import com.ppiyong.backend.global.security.dto.response.SignupResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "member")
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "parent_phone_number")
    private String parentPhoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "resident_no", length = 8)
    private String residentNo;

    public static SignupResponseDto toSignupDto(Member member) {

        return SignupResponseDto.builder()
                .email(member.getEmail())
                .username(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .parentPhoneNumber(member.getParentPhoneNumber())
                .address(member.getAddress())
                .build();
    }

    public static MypageResponseDto toMypageDto(Member member) {

        return MypageResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender())
                .parentPhoneNumber(member.getParentPhoneNumber())
                .address(member.getAddress())
                .residentNo(member.getResidentNo())
                .build();
    }

    // 정보 수정하기
    public void updateMember(String name, String phoneNumber, String address, String parentPhoneNumber) {
        if (StringUtils.hasText(name)) this.name = name;
        if (StringUtils.hasText(phoneNumber)) this.phoneNumber = phoneNumber;
        if (StringUtils.hasText(address)) this.address = address;
        if (StringUtils.hasText(parentPhoneNumber)) this.parentPhoneNumber = parentPhoneNumber;
    }
}
