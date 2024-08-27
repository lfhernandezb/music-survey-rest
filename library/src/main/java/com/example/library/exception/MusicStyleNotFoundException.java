package com.example.library.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MusicStyleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1500970926744655842L;
    private int errorCode;
    private String errorMessage;

    public MusicStyleNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public MusicStyleNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public MusicStyleNotFoundException(String msg) {
        super(msg);
    }

}
