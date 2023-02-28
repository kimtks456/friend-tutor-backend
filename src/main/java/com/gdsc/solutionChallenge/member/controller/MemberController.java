package com.gdsc.solutionChallenge.member.controller;

import static com.gdsc.solutionChallenge.global.utils.JwtUtil.getUsernameFromToken;

import com.gdsc.solutionChallenge.global.exception.ResponseForm;
import com.gdsc.solutionChallenge.global.exception.UserException;
import com.gdsc.solutionChallenge.member.dto.LoginDto;
import com.gdsc.solutionChallenge.member.dto.SignUpDto;
import com.gdsc.solutionChallenge.member.dto.TokenInfo;
import com.gdsc.solutionChallenge.member.dto.WithdrawDto;
import com.gdsc.solutionChallenge.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "사용자 인증", description = "회원가입, 로그인, 탈퇴")
@RestController
@Slf4j
@RequestMapping(path = "/user", produces = "application/json;charset=UTF-8")
public class MemberController {

//    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Invlaid inputs")
//    public class DirectoryNotFoundException extends RuntimeException {
//    }

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String test() {
        return "test";
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "ID, PW로 회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResponseForm.class))),
            @ApiResponse(responseCode = "406", description = "제약조건 지키지 않아, 회원가입 실패", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDto signUpDto) throws Exception {
        String username;
        try {
            username = memberService.signup(signUpDto);
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }

        ResponseForm responseForm = ResponseForm.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .message("회원가입 성공")
                .details(username)
                .build();
        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    @PostMapping("/login")
    public TokenInfo login(@Valid @RequestBody LoginDto loginDto) {
        TokenInfo tokenInfo;
        try {
            tokenInfo = memberService.login(loginDto.username(), loginDto.password());
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        return tokenInfo;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String withdraw(@Valid @RequestBody WithdrawDto withdrawDto) throws Exception {
        String deletedUsername = memberService.withdraw(withdrawDto.checkPassword());
        return deletedUsername;
    }
}
