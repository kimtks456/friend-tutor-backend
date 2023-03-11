package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "최다 추천수 강의글 조회 response body")
public class OneSummPostRes {
    private String time;
    private String message;
    private SummPost details;
}
