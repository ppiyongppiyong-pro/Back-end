package com.ppiyong.backend.api.hospital.entity;

import com.ppiyong.backend.api.hospital.domain.Department;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "hospital")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {
    @Id
    @Column(name = "hospital_id")
    private Long hospitalId; //

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private Department categoryName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "point_x", precision = 9, scale = 6)
    private BigDecimal pointX;

    @Column(name = "point_y", precision = 10, scale = 6)
    private BigDecimal pointY;

    @Builder
    public Hospital(Long hospitalId, String placeName, String addressName, String roadAddressName,
                    Department categoryName, String phone, BigDecimal pointX, BigDecimal pointY) {
        this.hospitalId = hospitalId;
        this.placeName = placeName;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.categoryName = categoryName;
        this.phone = phone;
        this.pointX = pointX;
        this.pointY = pointY;
    }
}
