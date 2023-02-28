package com.gdsc.solutionChallenge.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "회원탈퇴 request body")
public record WithdrawDto(
        @Schema(description = "재확인용 비밀번호", example = "1234")
        @NotBlank(message = "비밀번호를 입력해주세요")
        String checkPassword) {
}
