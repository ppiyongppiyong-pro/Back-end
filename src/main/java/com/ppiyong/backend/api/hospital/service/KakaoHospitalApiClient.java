package com.ppiyong.backend.api.hospital.service;

import com.ppiyong.backend.api.hospital.config.KakaoFeignConfig;
import com.ppiyong.backend.api.hospital.dto.KakaoRestApi.MapHospitalSearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoHospitalApiClient", url = "https://dapi.kakao.com/", configuration = KakaoFeignConfig.class)
public interface KakaoHospitalApiClient {

    @GetMapping("v2/local/search/category.json")
    MapHospitalSearchResult searchHospitals(
            @RequestParam("category_group_code") String categoryGroupCode,
            @RequestParam("x") Float x,
            @RequestParam("y") Float y,
            @RequestParam("category_name") String categoryName,
            @RequestParam("radius") int radius,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String distance
    );
}