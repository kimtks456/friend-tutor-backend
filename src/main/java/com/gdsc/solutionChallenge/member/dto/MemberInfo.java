package com.gdsc.solutionChallenge.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "사용자 정보")
public class MemberInfo {
    @Schema(description = "사용자 고유 번호")
    private Long member_id;
    @Schema(description = "아이디")
    private String username;
    @Schema(description = "실명")
    private String name;
    @Schema(description = "닉네임")
    private String nickName;
    @Schema(description = "학년")
    private Integer grade;
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "점수")
    private Integer score;
}
