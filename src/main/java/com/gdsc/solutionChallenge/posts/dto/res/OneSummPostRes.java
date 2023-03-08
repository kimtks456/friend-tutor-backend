package com.gdsc.solutionChallenge.posts.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OneSummPostRes {
    private String time;
    private String message;
    private SummPost details;
}
