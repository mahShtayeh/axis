package com.axis.account.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Axis account creation request
 *
 * @param username Owner username
 * @param balance  Owner initial balance
 * @author Mahmoud Shtayeh
 */
@Schema(description = "REST API request for Axis account creation")
public record AccountCreationRequest(
        @Schema(description = "Owner username", example = "examble@axis.com")
        @Email(message = "error.account.invalidUsername")
        @NotEmpty(message = "error.account.missingOrEmptyUsername")
        String username,

        @Schema(description = "Owner initial balance", example = "1000.00")
        @NotNull(message = "error.account.missingBalance")
        @Positive(message = "error.account.invalidBalance")
        BigDecimal balance
) {
}