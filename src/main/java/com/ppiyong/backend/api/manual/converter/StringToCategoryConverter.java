package com.ppiyong.backend.api.manual.converter;

import com.ppiyong.backend.api.manual.common.Category;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import org.springframework.core.convert.converter.Converter;

public class StringToCategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(String source) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(source)) {
                return category;
            }
        }
        throw CustomException.of(ErrorCode.MISSING_CATEGORY_NAME);
    }
}
