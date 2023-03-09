package com.gdsc.solutionChallenge.posts.controller;

import com.gdsc.solutionChallenge.global.exception.PostException;
import com.gdsc.solutionChallenge.global.exception.ResponseForm;
import com.gdsc.solutionChallenge.posts.dto.res.CertificateInfo;
import com.gdsc.solutionChallenge.posts.dto.res.CertificateInfoRes;
import com.gdsc.solutionChallenge.posts.dto.res.CheckCertificateRes;
import com.gdsc.solutionChallenge.posts.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증서", description = "인증서 가능 여부, 발급")
@RestController
@Slf4j
@RequestMapping(path = "/certificate", produces = "application/json;charset=UTF-8")
public class CertificateController {
    @Autowired
    CertificateService certificateService;

    @GetMapping("/check")
    @Operation(summary = "인증서 가능 여부", description = "인증서 발급이 가능한지, 불가하다면 부족한 점수를 알려줍니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증서 발급 가능, 불가 : message가 0,1,2냐로 3가지 상태 분리. Schema에 response body 참고 바람", content = @Content(schema = @Schema(implementation = CheckCertificateRes.class))),
            @ApiResponse(responseCode = "406", description = "인증서 발급 로직 에러 : 발급 과정에 error 발생", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> check() {
        CheckCertificateRes checkCertificateRes;
        try {
            checkCertificateRes = certificateService.checkCertificate();
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        checkCertificateRes.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ResponseEntity<>(checkCertificateRes, HttpStatus.OK);
    }

    @GetMapping("/issue")
    @Operation(summary = "인증서 발급", description = "인증서 발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증서 발급 성공", content = @Content(schema = @Schema(implementation = CertificateInfoRes.class))),
            @ApiResponse(responseCode = "406", description = "인증서 발급 로직 에러 : 발급 과정에 error 발생", content = @Content(schema = @Schema(implementation = ResponseForm.class)))})
    public ResponseEntity<?> issue() {
        CertificateInfo certificateInfo;
        try {
            certificateInfo = certificateService.issueCertificate();
        } catch (Exception e) {
            throw new PostException(e.getMessage());
        }

        CertificateInfoRes certificateInfoRes = CertificateInfoRes.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .message("인증서 발급 성공")
                .details(certificateInfo)
                .build();
    }
}
