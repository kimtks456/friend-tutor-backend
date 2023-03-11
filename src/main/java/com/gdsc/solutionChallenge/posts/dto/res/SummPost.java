package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "게시글 요약 정보")
public class SummPost {
    @Schema(description = "게시글 고유 번호", example = "1")
    private Long course_id;;
    @Schema(description = "게시글 작성자 닉네임", example = "mt._.kim")
    private String writer; // nickName
    @Schema(description = "게시글 제목", example = "곱셈공식에 대하여...")
    private String title;
    @Schema(description = "Youtube Video ID", example = "YNm2-30qfdk")
    private String video_id;
    @Schema(description = "게시글 추천수", example = "10")
    private Integer likes;
}
