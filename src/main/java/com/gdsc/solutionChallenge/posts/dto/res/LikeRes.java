package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "추천, 추천 취소 요청 response body")
public class LikeRes {
    private String time;
    private String message;
    private boolean details;
}
