package com.gdsc.solutionChallenge.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm {
    @Schema(description = "response 보내는 시각", example = "2023-02-28 02:40:12.767")
    private String time;
    @Schema(description = "성공 OR error logic 범위 설명", example = "회원가입 성공 OR User 관련 로직 에러입니다")
    private String message;
    @Schema(description = "성공시 username 반환 OR 실제 error message", example = "{username} OR 이미 존재하는 아이디입니다.")
    private String details;
}
