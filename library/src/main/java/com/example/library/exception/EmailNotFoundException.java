package com.example.library.exception;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class EmailNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1500970911344655842L;
    private int errorCode;
    private String errorMessage;

    public EmailNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public EmailNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public EmailNotFoundException(String msg) {
        super(msg);
    }
}


