package com.ppiyong.backend.api.member.entity;

import com.ppiyong.backend.api.common.BaseEntity;
import com.ppiyong.backend.api.member.common.Role;
import com.ppiyong.backend.api.member.common.Type;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "member")
@Getter
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
}
