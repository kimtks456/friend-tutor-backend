package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "최근 등록된 강의글들 조회 response body")
public class ListSummPostsRes {
    private String time;
    private String message;
    private List<SummPost> details;
}
