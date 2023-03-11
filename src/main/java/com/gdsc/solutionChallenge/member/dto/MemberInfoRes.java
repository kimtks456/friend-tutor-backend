package com.gdsc.solutionChallenge.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "사용자 정보 조회 response body")
public class MemberInfoRes {
    private String time;
    private String message;
    private MemberInfo details;
}
