package com.axis.account.exception;

import lombok.*;

import java.io.Serial;
import java.util.UUID;

/**
 * Exception thrown when an account with the specified identifier cannot be found.
 *
 * @author Mahmoud Shtayeh
 */
@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class AccountNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4328740L;

    /**
     * The unique identifier of the account that was not found.
     */
    private final UUID accountId;
}