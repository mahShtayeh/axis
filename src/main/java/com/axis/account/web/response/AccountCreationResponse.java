package com.axis.account.web.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

/**
 * Axis account creation response
 *
 * @param accountId Create Axis account ID
 * @author Mahmoud Shtayeh
 */
@Schema(description = "REST API response wrapper for account creation")
@Builder
public record AccountCreationResponse(
        @Schema(description = "Created account ID")
        UUID accountId
) {
}