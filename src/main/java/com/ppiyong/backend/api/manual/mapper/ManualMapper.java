package com.ppiyong.backend.api.manual.mapper;


import com.ppiyong.backend.api.manual.dto.manual.ManualRespondDto;
import com.ppiyong.backend.api.manual.dto.manualcategory.ManualCategoryRespondDto;
import com.ppiyong.backend.api.manual.dto.manualdetail.ManualDetailRespondDto;
import com.ppiyong.backend.api.manual.dto.manualkeyword.ManualKeywordRespondDto;
import com.ppiyong.backend.api.manual.entity.Manual;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManualMapper {
    // 기본 매핑
    public ManualRespondDto toDto(Manual manual) {
        return ManualRespondDto.builder()
                .manualId(manual.getManualId())
                .name(manual.getName())
                .manualSummary(manual.getManualSummary())
                .imgurl(manual.getImgurl())
                .build();
    }

    // 세부 정보 매핑
    public ManualDetailRespondDto toDetailDto(Manual manual) {
        return ManualDetailRespondDto.builder()
                .manualId(manual.getManualId())
                .name(manual.getName())
                .detail(manual.getDetail())
                .build();
    }

    // 카테고리 매핑
    public ManualCategoryRespondDto toCategoryDto(Manual manual, Boolean isLiked) {
        return ManualCategoryRespondDto.builder()
                .manualId(manual.getManualId())
                .name(manual.getName())
                .manualSummary(manual.getManualSummary())
                .imgurl(manual.getImgurl())
                .category(manual.getCategory().name())
                .isLiked(isLiked)
                .build();
    }

    // 키워드 매핑
    public ManualKeywordRespondDto toKeywordDto(Manual manual) {
        return ManualKeywordRespondDto.builder()
                .manualId(manual.getManualId())
                .name(manual.getName())
                .manualSummary(manual.getManualSummary())
                .imgurl(manual.getImgurl())
                .build();
    }

    // 리스트 매핑 메소드
    public List<ManualRespondDto> toDtoList(List<Manual> manuals) {
        return manuals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
