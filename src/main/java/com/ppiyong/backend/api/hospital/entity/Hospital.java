package com.ppiyong.backend.api.hospital.entity;

import com.ppiyong.backend.api.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "hospital")
public class Hospital extends BaseEntity {

    // 추가된 메서드
    @Getter
    @Id
    @Column(name = "place_id", unique = true, nullable = false)
    private String placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "point_x")
    private String x;

    @Column(name = "point_y")
    private String y;
}
