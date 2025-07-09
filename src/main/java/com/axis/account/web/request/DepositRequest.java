package com.axis.account.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Represents a deposit request in the Axis account management system.
 *
 * @param amount Amount to be deposited into the account
 */
@Schema(description = "REST API request params wrapper for account deposit")
public record DepositRequest(
        @Schema(description = "Deposited amount", example = "1000.00")
        @NotNull(message = "error.account.missingDepositAmount")
        @Positive(message = "error.account.invalidDepositAmount")
        BigDecimal amount
) {
}