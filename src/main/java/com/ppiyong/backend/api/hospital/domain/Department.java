package com.ppiyong.backend.api.hospital.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
    INTERNAL_MEDICINE("내과"),
    SURGERY("외과"),
    ORTHOPEDICS("정형외과"),
    OBSTETRICS("산부인과"),
    DERMATOLOGY("피부과"),
    OTOLARYNGOLOGY("이비인후과"),
    DENTISTRY("치과"),
    NEUROSURGERY("신경외과"),
    PEDIATRICS("소아과"),
    OPHTHALMOLOGY("안과"),
    UROLOGY("비뇨기과"),
    PSYCHIATRY("정신건강의학과"),
    FAMILY_MEDICINE("가정의학과");

    private final String displayName;

    public static Department from(String displayName) {
        for (Department department : values()) {
            if (department.getDisplayName().equals(displayName)) {
                return department;
            }
        }
        throw new IllegalArgumentException("Unknown department: " + displayName);
    }
}
