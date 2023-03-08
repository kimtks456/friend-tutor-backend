package com.gdsc.solutionChallenge.posts.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OneFullPostRes {
    private String time;
    private String message;
    private FullPost details;
}
