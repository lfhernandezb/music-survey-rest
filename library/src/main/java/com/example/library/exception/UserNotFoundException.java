package com.example.library.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1500970921344655842L;
    private int errorCode;
    private String errorMessage;

    public UserNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public UserNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

}
