package com.axis.account.exception;

import com.axis.account.web.ApiError;
import com.axis.account.web.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Global RestFull APIs exception advisor
 *
 * @author Mahmoud Shtayeh
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvisor {
    /**
     * I18n supported messages source
     */
    private final MessageSource messageSource;

    /**
     * UnKnown exceptions handler
     *
     * @param exception UnKnown exception
     * @return ApiResponse wrapping the error user-friendly details
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RestResponse<Object>> handleException(final Exception exception) {
        final String message = messageSource
                .getMessage(exception.getMessage(), null, LocaleContextHolder.getLocale());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse.error(ApiError.builder()
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()));
    }

    /**
     * MethodArgumentNotValidException exception handler
     *
     * @param exception MethodArgumentNotValidException exception
     * @return ApiResponse wrapping the error user-friendly details
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handleValidationException(final MethodArgumentNotValidException exception) {
        final List<ApiError> apiErrors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(apiError -> apiErrors.add(ApiError.builder()
                .message(messageSource.getMessage(Objects.requireNonNull(apiError.getDefaultMessage()),
                        null, LocaleContextHolder.getLocale()))
                .timestamp(LocalDateTime.now())
                .build()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.builder()
                        .errors(apiErrors)
                        .build());
    }
}