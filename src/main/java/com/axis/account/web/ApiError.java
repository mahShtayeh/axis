package com.axis.account.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Rest API Error
 *
 * @param message   Error message
 * @param timestamp Error creation timestamp
 * @author Mahmoud Shtayeh
 */
@Schema(description = "REST API error")
@Builder
public record ApiError(
        @Schema(description = "Error message")
        String message,

        @Schema(description = "Error timestamp")
        LocalDateTime timestamp
) {
}