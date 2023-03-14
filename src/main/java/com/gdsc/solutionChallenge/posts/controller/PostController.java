package com.gdsc.solutionChallenge.posts.controller;


import com.gdsc.solutionChallenge.global.exception.PostException;
import com.gdsc.solutionChallenge.global.exception.ResponseForm;
import com.gdsc.solutionChallenge.posts.dto.req.PostSaveDto;
import com.gdsc.solutionChallenge.posts.dto.res.AllFullPostsRes;
import com.gdsc.solutionChallenge.posts.dto.res.FullPost;
import com.gdsc.solutionChallenge.posts.dto.res.ListSubjectsRes;
import com.gdsc.solutionChallenge.posts.dto.res.ListSummPostsRes;
import com.gdsc.solutionChallenge.posts.dto.res.OneFullPostRes;
import com.gdsc.solutionChallenge.posts.dto.res.OneSummPostRes;
import com.gdsc.solutionChallenge.posts.dto.res.SummPost;
import com.gdsc.solutionChallenge.posts.dto.res.UploadRes;
import com.gdsc.solutionChallenge.posts.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @ApiResponse(responseCode = "200", description = "강의글 생성 성공 : 강의글의 title을 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = UploadRes.class))),
            @ApiResponse(responseCode = "406", description = "강의글 생성 실패 : 강의글 생성 request body 제약조건 확인", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> createPost(@Valid @RequestBody PostSaveDto postSaveDto) {
        SummPost post;
        try {
            post = postService.createPost(postSaveDto);
        } catch (Exception e) {
            e.printStackTrace();
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
    @Operation(summary = "[TEST] 모든 강의글의 상세정보 조회", description = "모든 강의글의 상세정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 강의글 조회 성공 : 모든 강의글들을 details에 배열로 담아 보냅니다.", content = @Content(schema = @Schema(implementation = AllFullPostsRes.class))),
            @ApiResponse(responseCode = "406", description = "모든 강의글 조회 실패 : 조회된 게시물이 하나도 없는 경우.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> getAllFullPosts() {
        List<FullPost> result;
        try {
            result = postService.getAllPosts();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PostException(e.getMessage());
        }

        AllFullPostsRes allFullPostsRes = AllFullPostsRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("모든 강의글 전체정보 조회 성공")
                .details(result)
                .build();
        return new ResponseEntity<>(allFullPostsRes, HttpStatus.OK);
    }

    @GetMapping("/{course_id}")
    @Operation(summary = "강의글 하나 조회", description = "해당 강의글의 상세정보를 조회합니다.")
    @Parameter(name = "course_id", description = "조회할 강의글의 id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의글 전체정보 조회 성공 : 해당 강의글의 모든 정보를 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = OneFullPostRes.class))),
            @ApiResponse(responseCode = "406", description = "강의글 전체정보 조회 실패 : 해당 강의글이 존재하지 않는 경우.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> getFullPost(@PathVariable("course_id") Long courseId) {
        FullPost result;

        try {
            result = postService.getFullPost(courseId);
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        OneFullPostRes oneFullPostRes = OneFullPostRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("해당 강의글 전체정보 조회 성공")
                .details(result)
                .build();
        return new ResponseEntity<>(oneFullPostRes, HttpStatus.OK);
    }

    @GetMapping("/trending")
    @Operation(summary = "최다 추천수 강의글 조회", description = "특정 학년의 인기 강의글의 요약정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인기 강의글 조회 성공 : 해당 학년의 인기 강의글의 요약정보를 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = OneSummPostRes.class))),
            @ApiResponse(responseCode = "406", description = "인기 강의글 조회 실패 : 해당 학년의 인기 강의글이 존재하지 않는 경우.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> getTopTrendingPost(
            @Parameter(name = "grade", description = "학년을 입력해주세요.", required = true, example = "6")
            @RequestParam Integer grade) {
        SummPost result;
        try {
            result = postService.getTopTrendingSummPost(grade);
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        OneSummPostRes oneSummPostRes = OneSummPostRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("인기 강의글 조회 성공")
                .details(result)
                .build();
        return new ResponseEntity<>(oneSummPostRes, HttpStatus.OK);
    }

    @GetMapping("/recent")
    @Operation(summary = "최근 등록된 강의글들 조회", description = "특정 학년의 최근 등록된 강의글 N개의 요약정보를 조회합니다. \n\n 4,5,6학년의 경우, dummy로 12개 이상씩 넣어뒀고, 7,8학년은 12개 미만으로 넣어뒀습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최근 등록된 강의글들 조회 성공 : 해당 학년의 최근 등록된 강의글 N개의 요약정보를 details에 배열로 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ListSummPostsRes.class))),
            @ApiResponse(responseCode = "406", description = "최근 등록된 강의글들 조회 실패 : 해당 학년의 최근 등록된 강의글이 존재하지 않는 경우.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> getRecentSummPosts(
            @Parameter(name = "grade", description = "학년을 입력해주세요.", required = true, example = "4")
            @RequestParam Integer grade,
            @Parameter(name = "number", description = "몇개까지 조회할지 입력해주세요.", required = true, example = "12")
            @RequestParam Integer number) {
        List<SummPost> result;
        try {
            result = postService.getRecentSummPosts(grade, number);
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        ListSummPostsRes listSummPostsRes = ListSummPostsRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("최근 등록된 강의글들 조회 성공")
                .details(result)
                .build();
        return new ResponseEntity<>(listSummPostsRes, HttpStatus.OK);
    }

    @GetMapping("/subjects")
    @Operation(summary = "모든 과목 조회", description = "입력 가능한 과목 list를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "과목 조회 성공 : 모든 과목을 details에 배열로 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ListSubjectsRes.class))),
            @ApiResponse(responseCode = "406", description = "과목 조회 실패 : 과목이 존재하지 않는 경우.", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> getSubjects() {
        List<String> subjects = postService.getSubjects();

        ListSubjectsRes listSubjectsRes = ListSubjectsRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("과목 조회 성공")
                .details(subjects)
                .build();
        return new ResponseEntity<>(listSubjectsRes, HttpStatus.OK);
    }
}
