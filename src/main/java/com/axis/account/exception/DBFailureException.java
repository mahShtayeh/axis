package com.axis.account.exception;

import java.io.Serial;

/**
 * Exception thrown to indicate a database operation failure.
 *
 * @author Mahmoud Shtayeh
 */
public class DBFailureException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4328741L;

    /**
     * Constructs a new DBFailureException with a specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public DBFailureException(final String message) {
        super(message);
    }
}
