package com.axis.account.web;

import lombok.*;

import java.util.List;

/**
 * Rest API response wrapper
 *
 * @param <P> API payload type
 * @author Mahmoud Shtayeh
 */
@Builder
public record ApiResponse<P>(
        P payload,
        List<ApiError> errors,
        String executionTime
) {
    /**
     * Convenient method to create a valid response
     *
     * @param payload Rest API payload
     * @param <T>     Payload type
     * @return Rest API response
     */
    public static <T> ApiResponse<T> ok(final T payload) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .build();
    }

    /**
     * Convenient method to create a response of a single error
     *
     * @param error Rest API error
     * @param <T>   Payload type
     * @return Rest API response
     */
    public static <T> ApiResponse<T> error(final ApiError error) {
        return ApiResponse.<T>builder()
                .errors(List.of(error))
                .build();
    }
}