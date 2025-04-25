package com.ppiyong.backend.api.hospital.entity;

import com.ppiyong.backend.api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "hospital")
public class Hospital extends BaseEntity {

    // 추가된 메서드
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id", unique = true, nullable = false)
    private Long hospitalId;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    // TODO : Enum class로 필터링 고려
    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "point_x")
    private float x;

    @Column(name = "point_y")
    private String y;
    private float y;

}
