package com.axis.account.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * An account balance retrieval operation in the REST API.
 *
 * @param balance Current available balance.
 * @author Mahmoud Shtayeh
 */
@Schema(description = "REST API response wrapper for account balance")
@Builder
public record AccountBalanceResponse(
        @Schema(description = "Account balance", example = "1000.00")
        BigDecimal balance
) {
}