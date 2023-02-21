package com.gdsc.solutionChallenge.member.controller;

import com.gdsc.solutionChallenge.member.dto.LoginRequestDto;
import com.gdsc.solutionChallenge.member.dto.SignUpDto;
import com.gdsc.solutionChallenge.member.dto.TokenInfo;
import com.gdsc.solutionChallenge.member.dto.WithdrawDto;
import com.gdsc.solutionChallenge.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String test() {
        return "test";
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public String signup(@Valid @RequestBody SignUpDto signUpDto) throws Exception {
        String savedUsername = memberService.signup(signUpDto);
        return savedUsername;
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
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
