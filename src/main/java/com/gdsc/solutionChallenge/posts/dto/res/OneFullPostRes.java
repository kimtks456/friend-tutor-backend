package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "강의글 하나 조회 response body")
public class OneFullPostRes {
    private String time;
    private String message;
    @Schema(name = "/게시글 상세 정보/")
    private FullPost details;
}
