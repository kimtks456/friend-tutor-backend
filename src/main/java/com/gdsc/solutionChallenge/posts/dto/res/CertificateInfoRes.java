package com.gdsc.solutionChallenge.posts.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificateInfoRes {
    private String time;
    private String message;
    private CertificateInfo details;
}
