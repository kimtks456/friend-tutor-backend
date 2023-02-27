package com.gdsc.solutionChallenge.member.controller;

import com.gdsc.solutionChallenge.global.exception.ResponseForm;
import com.gdsc.solutionChallenge.global.exception.UserException;
import com.gdsc.solutionChallenge.member.dto.LoginDto;
import com.gdsc.solutionChallenge.member.dto.SignUpDto;
import com.gdsc.solutionChallenge.member.dto.TokenInfo;
import com.gdsc.solutionChallenge.member.dto.WithdrawDto;
import com.gdsc.solutionChallenge.member.service.MemberService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpDto signUpDto) throws Exception {
        String username;
        try {
            username = memberService.signup(signUpDto);
        } catch (Exception e) {
            throw new UserException();
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
        String username = loginDto.username();
        String password = loginDto.password();
        TokenInfo tokenInfo = memberService.login(username, password);
        return tokenInfo;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String withdraw(@Valid @RequestBody WithdrawDto withdrawDto) throws Exception {
        String deletedUsername = memberService.withdraw(withdrawDto.checkPassword());
        return deletedUsername;
    }
}
