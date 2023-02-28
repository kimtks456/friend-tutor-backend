package com.gdsc.solutionChallenge.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@Schema(name = "로그인 성공 response body")
public class TokenInfo {

    @Schema(description = "Bearer 고정", example = "Bearer")
    private String grantType; // Bearer
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZHNjIiwiYXV0aCI6IltVU0VSXSIsImV4cCI6MTY3NzU3NDQ1OX0.d-pbTAoRYhv7qPkO5U_qgGwVfiV1NoStmZ-zEuvGUcs")
    private String accessToken;
    @Schema(description = "사용 안함. accessToken만 사용", example = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Nzc1NzQ0NTl9.vPVMOSwhw9_G6Xbgpsh_OfI8K41SmBPPWA-vnLY8hnw")
    private String refreshToken;
}
