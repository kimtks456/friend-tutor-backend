package com.gdsc.solutionChallenge.posts.dto;

import com.gdsc.solutionChallenge.posts.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "게시글 생성 request body")
public record PostSaveDto(
//        @Schema(description = "학년", example = "6")
//        @NotBlank(message = "학년을 입력해주세요.") Integer grade,
//        @Schema(description = "과목", example = "수학")
//        @NotBlank(message = "과목을 입력해주세요.") String subject,
        @Schema(description = "제목", example = "원의 넓이 5분 정리")
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @Schema(description = "설명", example = "원의 넓이는 3.14r^2입니다.")
        String description,
        @Schema(description = "구글 드라이브 링크", example = "https://drive.google.com/drive/folders/1j2k3l4m5n6o7p8q9r0s1t2u3v4w5x6y7z8?usp=sharing")
        String drive_link,
        @Schema(description = "유튜브 링크", example = "https://www.youtube.com/watch?v=xYxTzitqJ2g&list=PLSa6-sw1zyw0BTs54_SM-jRe66jeqJkoD")
        String video_link) {

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .description(description)
                .drive_link(drive_link)
                .video_link(video_link)
                .likes(0)
                .grade(6)
                .subject("수학")
                .build();
    }
}
