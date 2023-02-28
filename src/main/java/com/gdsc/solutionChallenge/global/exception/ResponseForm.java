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
    @Schema(description = "성공 , error logic 범위 설명", example = "성공 message OR error logic 범위 설명")
    private String message;
    @Schema(description = "성공 : description 참고 , 실패 : error message", example = "성공 : description 참고 , 실패 : error message")
    private String details;
}
