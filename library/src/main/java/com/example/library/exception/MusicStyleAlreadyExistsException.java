package com.example.library.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MusicStyleAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -1510970921344655842L;
    private int errorCode;
    private String errorMessage;

    public MusicStyleAlreadyExistsException(Throwable throwable) {
        super(throwable);
    }

    public MusicStyleAlreadyExistsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public MusicStyleAlreadyExistsException(String msg) {
        super(msg);
    }
}
