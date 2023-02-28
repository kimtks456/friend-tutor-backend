package com.gdsc.solutionChallenge.global.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ResponseForm responseForm = ResponseForm.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .message("유효성 검증에 실패했습니다.")
                .details(ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage())
                .build();
        return new ResponseEntity<>(responseForm, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<Object> handleUserException(Exception ex) {
        ResponseForm responseForm = ResponseForm.builder()
                .time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
                .message("User logic 관련 예외가 발생했습니다.")
                .details(ex.getMessage())
                .build();
        return new ResponseEntity<>(responseForm, HttpStatus.NOT_ACCEPTABLE);
    }
}
