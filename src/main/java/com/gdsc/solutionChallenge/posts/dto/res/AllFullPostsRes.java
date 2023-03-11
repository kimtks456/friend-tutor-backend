package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "[TEST] 모든 강의글의 상세정보 조회 response body")
public class AllFullPostsRes {
    private String time;
    private String message;
    private List<FullPost> details;
}
