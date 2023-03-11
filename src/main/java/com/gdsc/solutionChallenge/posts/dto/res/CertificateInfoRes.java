package com.gdsc.solutionChallenge.posts.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "인증서 발급 response body")
public class CertificateInfoRes {
    private String time;
    private String message;
    private CertificateInfo details;
}
