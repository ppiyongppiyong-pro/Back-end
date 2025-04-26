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
}
