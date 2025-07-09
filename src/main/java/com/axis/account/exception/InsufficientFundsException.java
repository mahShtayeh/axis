package com.axis.account.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Exception thrown when a transaction is attempted on an account with insufficient funds.
 * This exception captures details about the account, the requested transaction amount,
 * and the current balance of the account at the time of the exception.
 *
 * @author Mahmoud Shtayeh
 */
@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class InsufficientFundsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4328742L;

    /**
     * Represents the unique identifier of the account involved in an exception.
     */
    private final UUID accountId;

    /**
     * Represents the monetary amount involved in the insufficient funds' exception.
     */
    private final BigDecimal amount;

    /**
     * Represents the current balance of an account involved in an insufficient funds exception.
     */
    private final BigDecimal balance;
}