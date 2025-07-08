package com.axis.account.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountDTO(
        String username,
        BigDecimal balance
) {
}