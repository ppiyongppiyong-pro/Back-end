package com.ppiyong.backend.api.manual.common;

public enum Category {
    BASIC("기본"),
    SITUATIONS("상황별"),
    MEDICAL("의학적"),
    TRAUMATIC("외상성");

    private String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromDisplayName(String displayName) {
        for (Category category : Category.values()) {
            if (category.displayName.equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("잘못된 카테고리명: " + displayName);
    }
}
