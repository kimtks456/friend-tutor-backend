package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(name = "인증서 발급가능 여부 response body")
public class CheckCertificateRes {
    private String time;
    @Schema(description = "0 : 응답 성공, 발급 가능 \n 1 : 응답 성공, 발급 실패", example = "1")
    private Integer message;
    @Schema(description = "0 : 응답 성공, 발급 가능 \n N : 응답 성공, 인증서 발급(70점이상)까지 부족한 점수", example = "30")
    private Integer details;
}
