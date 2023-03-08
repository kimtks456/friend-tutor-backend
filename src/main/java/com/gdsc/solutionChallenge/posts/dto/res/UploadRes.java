package com.gdsc.solutionChallenge.posts.dto.res;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadRes {
    private String time;
    private String message;
    private SummPost details;
}
