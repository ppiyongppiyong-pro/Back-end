package com.ppiyong.backend.api.hospital.entity;

import com.ppiyong.backend.api.hospital.domain.Department;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Hospital {

    @Id
    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    @Column(name = "place_id")
    private String placeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private Department categoryName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "point_x", precision = 9, scale = 6)
    private BigDecimal pointX;

    @Column(name = "point_y", precision = 10, scale = 6)
    private BigDecimal pointY;
}
