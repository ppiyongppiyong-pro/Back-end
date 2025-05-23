package com.ppiyong.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    // 공통
    MEMBER_NOT_FOUND(404, "ME001", "회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(404, "ME002", "이미 존재하는 회원입니다."),
    INTERVAL_SERVER_ERROR(500, "SE001", "서버 내부의 오류입니다."),
    TEST_ERROR(404, "TEST", "테스트 에러입니다."),


    // Manual 파라미터별 구체적 누락 예외
    MISSING_NAME_PARAM(400, "RE002", "이름 파라미터가 누락되었습니다."),
    MISSING_CATEGORY_PARAM(400, "RE003", "카테고리 파라미터가 누락되었습니다."),
    MISSING_KEYWORD_PARAM(400, "RE004", "키워드 파라미터가 누락되었습니다."),
    MISSING_PATH_VARIABLE(400, "RE005", "경로 변수가 누락되었습니다."),
    // Manual like
    MANUAL_NOT_FOUND(404, "ME001", "매뉴얼을 찾을 수 없습니다."),
    MANUAL_IS_EMPTY(404, "ME002", "즐겨찾기한 매뉴얼이 없습니다."),



    // Hospital 파라미터별 구체적 누락 예외
    MISSING_CATEGORY_NAME(400, "RE004", "카테고리 이름이 없습니다."),
    INVALID_COORDINATE_FORMAT(400, "RE007", "좌표 형식이 유효하지 않습니다."),
    MISSING_X_COORDINATE(400, "RE002", "X 좌표가 없습니다."),
    MISSING_Y_COORDINATE(400, "RE003", "Y 좌표가 없습니다."),
    //hosptial like
    HOSPITAL_NOT_FOUND(404, "HO001", "병원을 찾을 수 없습니다."),
    HOSPITAL_IS_EMPTY(404, "HO002", "좋아요 내역이 없습니다."),


    // Token
    INVALID_TOKEN(401, "TE001","유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401, "TE002", "토큰이 만료되었습니다."),
    UNMATCHED_REFRESH_TOKEN(401, "TE003", "리프레시 토큰이 일치하지 않습니다."),
    INVALID_KAKAO_ACCESS_TOKEN(401, "TE004", "카카오 토큰이 유효하지 않습니다."),
    EMPTY_TOKEN(401, "TE005", "토큰이 비어있습니다."),
    EMPTY_COOKIE(401, "TE006", "쿠키가 비어있습니다."),
    FAILED_GENERATE_TOKEN(401, "TE007", "토큰 생성에 실패하였습니다."),
    FAILED_JWT_INFO(401, "TE006", "토큰으로부터 회원의 정보를 얻을 수 없습니다."),
    INVALID_JWT_SIGNATURE(401, "TE007", "유효하지 않은 서명의 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(401, "TE008", "지원하지 않는 형식의 JWT 토큰입니다."),
    FAILED_KAKAO_REQUEST(401, "TE009", "카카오로부터 요청에 실패하였습니다."),

    // login
    UNAUTHORIZED(401, "LE001", "인증되지 않았습니다."),
    UNMATCHED_PASSWORD(404, "LE002", "비밀번호가 일치하지 않습니다."),
    SHOULD_PERMISSION(404, "LE003", "동의가 필요합니다."),
    UNMATCHED_CODE(404, "LE004", "인증 코드가 불일치합니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}