package com.gdsc.solutionChallenge.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm {
    private String time;
    private String message;
    private String details;
}
