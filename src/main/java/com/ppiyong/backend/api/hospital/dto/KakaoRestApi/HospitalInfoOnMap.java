package com.ppiyong.backend.api.hospital.dto.KakaoRestApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalInfoOnMap {
    private Long id;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("category_group_code")
    private String categoryGroupCode;

    @JsonProperty("category_group_name")
    private String categoryGroupName;

    private String phone;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("road_address_name")
    private String roadAddressName;

    @JsonProperty("x")
    private BigDecimal pointX; //x가 경도
    @JsonProperty("y")
    private BigDecimal pointY; //y가 위도

    @JsonProperty("place_url")
    private String placeUrl;

    private String distance;

    private Boolean isLike = false;

    public void favorite() {
        this.isLike = true;
    }



//    public void favorite() {
//        this.isFavorite = true;
//    }


}