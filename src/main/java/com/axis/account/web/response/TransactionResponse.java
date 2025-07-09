package com.axis.account.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

/**
 * Represents the response for a deposit operation in the REST API.
 *
 * @param transactionId the transaction ID of the completed deposit.
 */
@Schema(description = "REST API response wrapper for account deposit")
@Builder
public record TransactionResponse(
        @Schema(description = "Deposit transaction Id")
        UUID transactionId
) {
}