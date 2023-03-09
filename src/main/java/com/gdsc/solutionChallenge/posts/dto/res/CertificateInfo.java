package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "인증서 발급 response body")
public class CertificateInfo {
    @Schema(description = "인증서 id", example = "1")
    private Integer certificate_id;

    @Schema(description = "수여자 실몀", example = "San Kim")
    private String name;

    @Schema(description = "활동명", example = "Peer Mentoring")
    private String title;

    @Schema(description = "활동 시작일", example = "2022.01.01")
    private String start;

    @Schema(description = "활동 종료일 = 마지막 게시글 일자 + 1달", example = "2023.01.01")
    private String end;

    @Schema(description = "게시한 강의 수", example = "9")
    private Integer number_of_lectures;

    @Schema(description = "받은 총 추천수", example = "27")
    private Integer number_of_referrals;

    @Schema(description = "발급일", example = "2023.02.")
    private String issue_date;
    private String rank;

}
