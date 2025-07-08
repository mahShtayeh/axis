package com.axis.account.web;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        String message,
        LocalDateTime timestamp
) {
}