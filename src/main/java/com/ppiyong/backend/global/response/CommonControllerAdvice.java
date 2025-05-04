package com.ppiyong.backend.global.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CommonControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        String className = returnType.getDeclaringClass().getName();

        if (className.contains("springdoc") || className.contains("OpenApiResource")) {
            return false;
        }

        return !ResponseEntity.class.isAssignableFrom(returnType.getParameterType())
                && !String.class.isAssignableFrom(returnType.getParameterType());
}

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        // CommonResponse(일반) & ErrorResponse(오류) 제외
        if (body instanceof CommonResponse<?> || body instanceof ErrorResponse) {
            return body;
        }
        return CommonResponse.ok(body);
    }
}
