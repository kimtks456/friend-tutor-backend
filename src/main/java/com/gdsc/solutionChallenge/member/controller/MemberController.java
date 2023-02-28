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
    @Autowired
    private MemberService memberService;


    @GetMapping
    @Operation(summary = "BE 연결 테스트", description = "정상적으로 BE와 연결되면, '성공' 이라는 String 반환")
    public String test() {
        return "성공";
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공 : 해당 username을 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ResponseForm.class))),
            @ApiResponse(responseCode = "406", description = "회원가입 실패 : 회원가입 request body 제약조건 확인", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDto signUpDto) {
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
    @Operation(summary = "로그인", description = "ID, PW로 로그인 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 : Type과 accessToken, refreshToken을 보냅니다.", content = @Content(schema = @Schema(implementation = TokenInfo.class))),
            @ApiResponse(responseCode = "406", description = "로그인 실패 : ID or PW 틀린 경우", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
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
    @Operation(summary = "회원 탈퇴", description = "accessToken, PW로 회원탈퇴 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공 : 탈퇴한 username을 details에 담아 보냅니다.", content = @Content(schema = @Schema(implementation = ResponseForm.class))),
            @ApiResponse(responseCode = "406", description = "회원탈퇴 실패 : accessToken or PW 틀린 경우", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> withdraw(@Valid @RequestBody WithdrawDto withdrawDto) {
        String deletedUsername;

        try {
            deletedUsername = memberService.withdraw(withdrawDto.checkPassword());
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
        ResponseForm responseForm = ResponseForm.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .message("회원탈퇴 성공")
                .details(deletedUsername)
                .build();
        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }
}
