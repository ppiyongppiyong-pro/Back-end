package com.ppiyong.backend.api.member.service;

import com.ppiyong.backend.api.member.dto.request.MypageRequestDto;
import com.ppiyong.backend.api.member.dto.response.MypageResponseDto;

public interface MemberService {

    MypageResponseDto addMemberInfo(String authToken, MypageRequestDto requestDto);
    MypageResponseDto getMyInfo(String authToken);
}
