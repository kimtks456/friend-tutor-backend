package com.gdsc.solutionChallenge.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "로그인 request body")

public record LoginDto(
    @Schema(description = "아이디", example = "gdsc1")
    @NotBlank(message = "아이디를 입력해주세요.") String username,
    @Schema(description = "비밀번호", example = "1234")
    @NotBlank(message = "비밀번호를 입력해주세요.") String password) {

}
