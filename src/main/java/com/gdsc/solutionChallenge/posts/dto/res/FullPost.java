package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "강의글 상세 정보")
public class FullPost {
    private Long course_id;
    private Integer grade;
    @Schema(description = "강의 과목(영어소문자, snake case)", example = "korean")
    private String subject;
    @Schema(description = "게시글 작성자 닉네임", example = "mt._.kim")
    private String writer; // nickName
    private String title;
    private String description;
    private String video_id;
    private String drive_link;
    private Integer likes;
    private String created_at;
    private Boolean is_liked;
}
