package com.gdsc.solutionChallenge.posts.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullPost {
    private Long course_id;
    private Integer grade;
    private String subject;
    private String writer; // nickName
    private String title;
    private String description;
    private String video_id;
    private String drive_link;
    private Integer likes;
    private String created_at;
}
