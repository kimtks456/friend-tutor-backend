package com.gdsc.solutionChallenge.posts.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummPost {
    private Long course_id;;
    private String writer; // nickName
    private String title;
    private String video_id;
    private Integer likes;
}
