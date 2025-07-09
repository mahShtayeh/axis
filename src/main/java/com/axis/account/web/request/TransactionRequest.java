package com.axis.account.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Represents a transaction request in the Axis account management system.
 *
 * @param amount Amount to be deposited into or withdraw from the account
 */
@Schema(description = "REST API request params wrapper for account transaction")
public record TransactionRequest(
        @Schema(description = "Transaction amount", example = "1000.00")
        @NotNull(message = "error.account.missingTransactionAmount")
        @Positive(message = "error.account.invalidTransactionAmount")
        BigDecimal amount
) {
}