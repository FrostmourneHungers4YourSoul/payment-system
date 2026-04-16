package org.example.payment.api.errors;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request
    ) {
        logger.error("Handling exception", ex);
        ApiError body = new ApiError(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getReason(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(ex.getStatusCode().value())
                .body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        logger.error("Handling exception", ex);
        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "validation error",
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        logger.error("Handling exception", ex);
        ApiError body = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
