package com.gdsc.solutionChallenge.posts.controller;


import com.gdsc.solutionChallenge.global.exception.PostException;
import com.gdsc.solutionChallenge.global.exception.ResponseForm;
import com.gdsc.solutionChallenge.posts.dto.PostSaveDto;
import com.gdsc.solutionChallenge.posts.entity.Post;
import com.gdsc.solutionChallenge.posts.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
@RequestMapping(path = "/post", produces = "application/json;charset=UTF-8")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공 : 게시글의 title을 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ResponseForm.class))),
            @ApiResponse(responseCode = "406", description = "게시글 생성 실패 : 게시글 생성 request body 제약조건 확인", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> createPost(@Valid @RequestBody PostSaveDto postSaveDto) {
        String title;
        try {
            title = postService.createPost(postSaveDto);
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        ResponseForm responseForm = ResponseForm.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("게시글 생성 성공")
                .details(title)
                .build();
        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

//    @GetMapping("/all")
//    @Operation(summary = "모든 게시글 조회", description = "모든 게시글을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "모든 게시글 조회 성공 : 모든 게시글을 배열에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ResponseForm.class))),
//            @ApiResponse(responseCode = "406", description = "모든 게시글 조회 실패 : 게시글이 하나도 없습니다.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
//    public List<Post> getAllPosts() {
//        List<Post> result = postService.getAllPosts();
//        if (result.isEmpty()) {
//            throw new PostException("게시글이 하나도 없습니다.");
//        }
//        return result;
//    }
}
