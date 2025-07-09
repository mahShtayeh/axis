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

import java.text.MessageFormat;
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
     * @param <T>       the type of the payload in the {@link RestResponse}.
     * @return ApiResponse wrapping the error user-friendly details
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseEntity<RestResponse<T>> handleException(final Exception exception) {
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
     * @param <T>       the type of the payload in the {@link RestResponse}.
     * @return ApiResponse wrapping the error user-friendly details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseEntity<RestResponse<T>> handleValidationException(final MethodArgumentNotValidException exception) {
        final List<ApiError> apiErrors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(apiError -> apiErrors.add(ApiError.builder()
                .message(messageSource.getMessage(Objects.requireNonNull(apiError.getDefaultMessage()),
                        null, LocaleContextHolder.getLocale()))
                .timestamp(LocalDateTime.now())
                .build()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.<T>builder()
                        .errors(apiErrors)
                        .build());
    }

    /**
     * Account not-found exception handler
     *
     * @param exception Account not-found
     * @param <T>       the type of the payload in the {@link RestResponse}.
     * @return ApiResponse wrapping the error user-friendly details
     */
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public <T> ResponseEntity<RestResponse<T>> handleException(final AccountNotFoundException exception) {
        final String message = messageSource
                .getMessage("error.account.notFound", null, LocaleContextHolder.getLocale());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(RestResponse.error(ApiError.builder()
                        .message(MessageFormat.format(message, exception.getAccountId()))
                        .timestamp(LocalDateTime.now())
                        .build()));
    }

    /**
     * Handles {@link InsufficientFundsException} thrown during the execution of the application.
     *
     * @param exception the {@link InsufficientFundsException} to handle, containing details on
     *                  the account ID, transaction amount, and current balance.
     * @param <T>       the type of the payload in the {@link RestResponse}.
     * @return a {@link ResponseEntity} containing a {@link RestResponse} with error details
     * and a {@code BAD_REQUEST} HTTP status.
     */
    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> ResponseEntity<RestResponse<T>> handleException(final InsufficientFundsException exception) {
        final String message = messageSource
                .getMessage("error.transaction.insufficientFunds", null, LocaleContextHolder.getLocale());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.error(ApiError.builder()
                        .message(MessageFormat.format(message,
                                exception.getAccountId(), exception.getBalance(), exception.getAmount()))
                        .timestamp(LocalDateTime.now())
                        .build()));
    }
}