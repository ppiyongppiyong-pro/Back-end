package com.ppiyong.backend.api.manual.converter;

import com.ppiyong.backend.api.manual.common.Category;
import org.springframework.core.convert.converter.Converter;

public class StringToCategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(source)) {
                return category;
            }
        }
        throw new IllegalArgumentException("잘못된 카테고리 값입니다: " + source);
    }
}
