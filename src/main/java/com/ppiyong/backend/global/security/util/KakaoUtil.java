package com.ppiyong.backend.global.security.util;

import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import com.ppiyong.backend.global.security.dto.response.KakaoInfoResponseDto;
import com.ppiyong.backend.global.security.dto.response.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoUtil {

    @Value("${spring.security.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.kakao.redirect-uri}")
    private String redirectUri;

    private static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private static final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    // 인증코드 URL
    public String buildLoginUrl() {
        return String.format(
                "%s/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                KAUTH_TOKEN_URL_HOST, clientId, redirectUri
        );
    }

    // 카카오로부터 AccessToken 받아오기
    public KakaoTokenDto requestAccessToken(String authorizationCode) {

        try {
            return createWebClient(KAUTH_TOKEN_URL_HOST)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/oauth/token")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("client_id", clientId)
                            .queryParam("redirect_uri", redirectUri)
                            .queryParam("code", authorizationCode)
                            .build())
                    .retrieve()
                    .bodyToMono(KakaoTokenDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("카카오 서버로 AccessToken 요청 중 오류 : {} ", e.getResponseBodyAsString());
            throw CustomException.of(ErrorCode.INVALID_KAKAO_ACCESS_TOKEN);
        }
    }

    // 카카오로부터 Profile 얻어오기
    public KakaoInfoResponseDto requestProfile(String token) {
        try {
            return createWebClient(KAUTH_USER_URL_HOST)
                    .get()
                    .uri("/v2/user/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(KakaoInfoResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("카카오로부터 사용자 Info 요청 중 오류 : {} ", e.getResponseBodyAsString());
            throw CustomException.of(ErrorCode.FAILED_KAKAO_REQUEST);
        }
    }

    // WebClient를 이용한 요청
    private WebClient createWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                // KAKAO open API 필수 Header
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();
    }

    // KAKAO로부터 얻어온 token의 정보 콘솔 로그 출력
    public void logTokenInfo(KakaoTokenDto tokenDto) {
        log.info("[Kakao Token] Access Token: {}", tokenDto.getAccessToken());
        log.info("[Kakao Token] Refresh Token: {}", tokenDto.getRefreshToken());
        log.info("[Kakao Token] Scope: {}", tokenDto.getScope());
    }
}
