package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "모든 과목 조회 response body")
public class ListSubjectsRes {
    private String time;
    private String message;
    private List<String> details;
}
