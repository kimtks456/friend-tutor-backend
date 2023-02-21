package com.gdsc.solutionChallenge.member.dto;

import jakarta.validation.constraints.NotBlank;

public record WithdrawDto(
        @NotBlank(message = "비밀번호를 입력해주세요")
        String checkPassword) {
}
