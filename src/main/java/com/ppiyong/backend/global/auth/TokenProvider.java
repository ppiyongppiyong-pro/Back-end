package com.ppiyong.backend.global.auth;

import com.ppiyong.backend.api.member.entity.Member;
import com.ppiyong.backend.global.exception.CustomException;
import com.ppiyong.backend.global.exception.ErrorCode;
import com.ppiyong.backend.global.response.CommonResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private static final String KEY_ROLES = "roles";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${spring.jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    public String generateToken(Member member, long expiration, boolean isRefreshToken) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + expiration*1000);

            return Jwts.builder()
                    .setHeaderParam("type", isRefreshToken ? "refresh" : "access")
                    .setSubject(String.valueOf(member.getMemberId()))
                    .claim(KEY_ROLES, member.getRole())
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS256, this.secretKey)
                    .compact();
        } catch (CustomException e) {
            throw CustomException.of(ErrorCode.FAILED_GENERATE_TOKEN);
        }
    }

    // Access
    public String generateAccessToken(Member member) {
        return generateToken(member, accessTokenExpiration, false);
    }

    // Refresh
    public String generateRefreshToken(Member member) {
        return generateToken(member, refreshTokenExpiration, true);
    }

    // token으로부터 Member ID(PK) 얻기
    public long getMemberIdFromToken(String token) throws CustomException {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String memberId = claims.getSubject();

        if (!StringUtils.hasText(memberId)) {
            throw CustomException.of(ErrorCode.FAILED_JWT_INFO);
        }
        return Long.parseLong(memberId);
    }

    // Payload(body) 정보 얻기
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 검증
    public CommonResponse<?> validationToken(String token) throws CustomException {
        try {
            final Claims claims = getClaimsFromToken(token);
            return CommonResponse.ok(claims);
        } catch (MalformedJwtException e) {
            log.warn("유효하지 않은 JWT token: {}", e.getMessage());
            throw CustomException.of(ErrorCode.INVALID_TOKEN);
        } catch (SignatureException e) {
            log.warn("유효하지 않은 서명의 JWT token: {}", e.getMessage());
            throw CustomException.of(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT token: {}", e.getMessage());
            throw CustomException.of(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.warn("지원하지 않는 JWT token: {}", e.getMessage());
            throw CustomException.of(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException ex) {
            log.warn("Empty JWT token: {}", ex.getMessage());
            throw CustomException.of(ErrorCode.EMPTY_TOKEN);
        }
    }
}