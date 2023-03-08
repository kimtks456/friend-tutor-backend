package com.gdsc.solutionChallenge.posts.controller;


import com.gdsc.solutionChallenge.global.exception.PostException;
import com.gdsc.solutionChallenge.global.exception.ResponseForm;
import com.gdsc.solutionChallenge.posts.dto.req.PostSaveDto;
import com.gdsc.solutionChallenge.posts.dto.res.FindAllRes;
import com.gdsc.solutionChallenge.posts.dto.res.FullPost;
import com.gdsc.solutionChallenge.posts.dto.res.SummPost;
import com.gdsc.solutionChallenge.posts.dto.res.UploadRes;
import com.gdsc.solutionChallenge.posts.entity.Post;
import com.gdsc.solutionChallenge.posts.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물", description = "게시글 생성, 조회, 삭제")
@RestController
@Slf4j
@RequestMapping(path = "/course", produces = "application/json;charset=UTF-8")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(summary = "강의 업로드", description = "강의글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의글 생성 성공 : 강의글의 title을 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ResponseForm.class))),
            @ApiResponse(responseCode = "406", description = "강의글 생성 실패 : 강의글 생성 request body 제약조건 확인", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> createPost(@Valid @RequestBody PostSaveDto postSaveDto) {
        SummPost post;
        try {
            post = postService.createPost(postSaveDto);
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        UploadRes uploadRes = UploadRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("강의글 생성 성공")
                .details(post)
                .build();
        return new ResponseEntity<>(uploadRes, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "[TEST] 모든 강의글 조회", description = "모든 강의글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 강의글 조회 성공 : 모든 강의글들을 details에 배열로 담아 보냅니다.", content = @Content(schema = @Schema(implementation = FindAllRes.class))),
            @ApiResponse(responseCode = "406", description = "모든 강의글 조회 실패 : 조회된 게시물이 하나도 없는 경우.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> getAllPosts() {
        List<FullPost> result = postService.getAllPosts();
        if (result.isEmpty()) {
            throw new PostException("게시물이 하나도 없습니다.");
        }

        FindAllRes findAllRes = FindAllRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("모든 강의글 조회 성공")
                .details(result)
                .build();
        return new ResponseEntity<>(findAllRes, HttpStatus.OK);
    }
}
