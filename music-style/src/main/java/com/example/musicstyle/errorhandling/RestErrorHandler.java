package com.example.musicstyle.errorhandling;

import com.example.library.exception.MusicStyleAlreadyExistsException;
import com.example.library.exception.MusicStyleNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestErrorHandler { //extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

    //@Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex/*, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request*/) {
        Map<String, List<String>> body = new HashMap<>();

        logger.info("ex: " + ex.toString());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestClientException.class)
    protected ResponseEntity<Object> handleRestClientException(
            RestClientException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        logger.info("RestClientException thrown");

        //return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
        Map<String, Object> body = new LinkedHashMap<>();
        //body.put("timestamp", LocalDateTime.now());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // custom errors
    @ExceptionHandler(MusicStyleNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException(
            MusicStyleNotFoundException ex, WebRequest request) {

        logger.info("MusicStyleNotFoundException thrown");

        Map<String, Object> body = new LinkedHashMap<>();
        //body.put("timestamp", LocalDateTime.now());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MusicStyleAlreadyExistsException.class)
    public ResponseEntity<Object> handleClientNotFoundException(
            MusicStyleAlreadyExistsException ex, WebRequest request) {

        logger.info("MusicStyleAlreadyExistsException thrown");

        Map<String, Object> body = new LinkedHashMap<>();
        //body.put("timestamp", LocalDateTime.now());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(
            ExecutionException ex, WebRequest request) {

        logger.info("ExecutionException thrown " + ex);

        Map<String, Object> body = new LinkedHashMap<>();
        //body.put("timestamp", LocalDateTime.now());
        body.put("error", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex, WebRequest request) {

        logger.info("Exception thrown " + ex);

        Map<String, Object> body = new LinkedHashMap<>();
        //body.put("timestamp", LocalDateTime.now());

        if (ex.getMessage().contains("HttpClientErrorException$NotFound")) {
            body.put("error", "Entidad no encontrada");
        }
        else {
            body.put("error", ex.getMessage());
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
