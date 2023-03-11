package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "강의 업로드 response body")
public class UploadRes {
    private String time;
    private String message;
    private SummPost details;
}
