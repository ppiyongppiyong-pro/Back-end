package com.ppiyong.backend.global.test;

import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "응답 테스트 API", description = "json 형식의 응답에 대한 테스트입니다.")
public class TestController {

    @Operation(summary = "API 응답 테스트(성공, 데이터 없음)")
    @GetMapping("/no-data")
    public void testOnSuccess() {
    }

    @Operation(summary = "API 응답 테스트(성공, 데이터 String)")
    @GetMapping("/string-data")
    public String testOnSuccessResult() {
        return "응답 테스트";
    }

    @Operation(summary = "API 응답 테스트(성공, 데이터 객체)")
    @GetMapping("/object-data")
    public Object testOnSuccessResultObject() {
        Object obj = "test";
        return obj;
    }

    @Operation
    @GetMapping("/error")
    public String errorResponse() {
        throw CustomException.of(ErrorCode.TEST_ERROR);
    }

}
