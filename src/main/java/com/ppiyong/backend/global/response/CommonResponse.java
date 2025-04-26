package com.ppiyong.backend.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {

    @Schema(example = "200")
    private int httpStatus;
    @Schema(example = "success")
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 200 OK
    public static<T> CommonResponse<T> ok(final T data) {
        return CommonResponse.<T>builder()
                .httpStatus(200)
                .message("success")
                .data(data)
                .build();
    }
}
