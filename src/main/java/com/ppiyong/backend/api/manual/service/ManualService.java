package com.ppiyong.backend.api.manual.service;

import com.ppiyong.backend.api.manual.common.Category;
import com.ppiyong.backend.api.manual.dto.manual.ManualRespondDto;
import com.ppiyong.backend.api.manual.dto.manualcategory.ManualCategoryRespondDto;
import com.ppiyong.backend.api.manual.dto.manualdetail.ManualDetailRespondDto;
import com.ppiyong.backend.api.manual.dto.manualkeyword.ManualKeywordRespondDto;
import com.ppiyong.backend.api.manual.entity.Manual;
import com.ppiyong.backend.api.manual.mapper.ManualMapper;
import com.ppiyong.backend.api.manual.repository.ManualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManualService {
    private final ManualRepository manualRepository;
    private final ManualMapper manualMapper;

    // 1. 이름으로 매뉴얼 검색
    public List<ManualRespondDto> getManuals(String name) {
        List<Manual> manuals = (name == null || name.isBlank())
                ? manualRepository.findAll()
                : manualRepository.findByNameContaining(name);

        return manuals.stream()
                .map(manualMapper::toDto)
                .collect(Collectors.toList());
    }

    // 2. 검색 자동완성
    public List<String> autocomplete(String name) {
        return manualRepository.autocompleteByName(name);
    }

    // 3. 카테고리별 매뉴얼 조회
    public List<ManualCategoryRespondDto> getManualsByCategory(String category) {
        // 기존: Category cat = Category.valueOf(category.toUpperCase());
        Category cat = Category.fromDisplayName(category);
        List<Manual> manuals = manualRepository.findByCategory(cat);

        return manuals.stream()
                .map(m -> manualMapper.toCategoryDto(m, null))
                .collect(Collectors.toList());
    }


    // 4. 매뉴얼 상세 조회
    public ManualDetailRespondDto getManualDetail(Long manualId) {
        Manual manual = manualRepository.findById(manualId)
                .orElseThrow(() -> new RuntimeException("매뉴얼을 찾을 수 없습니다."));
        return manualMapper.toDetailDto(manual);
    }

    // 5. 키워드로 매뉴얼 검색
    public List<ManualKeywordRespondDto> searchByKeyword(String keyword) {
        List<Manual> manuals = manualRepository.findByKeyword(keyword);
        return manuals.stream()
                .map(manualMapper::toKeywordDto)
                .collect(Collectors.toList());
    }
}
