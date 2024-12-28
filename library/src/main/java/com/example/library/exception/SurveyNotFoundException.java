package com.example.library.exception;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SurveyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1500970911344655842L;
    private int errorCode;
    private String errorMessage;

    public SurveyNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public SurveyNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public SurveyNotFoundException(String msg) {
        super(msg);
    }
}


