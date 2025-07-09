package com.axis.account.service;

import com.axis.account.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Axis accounts services declaration
 *
 * @author Mahmoud Shtayeh
 */
public interface AccountService {
    /**
     * Open an account in Axis
     *
     * @param accountDTO Account details to create
     * @return Created account ID
     */
    UUID openAccount(AccountDTO accountDTO);

    /**
     * Retrieves the current balance for a specific account.
     *
     * @param accountId the unique identifier of the account
     * @return the current balance of the account
     */
    BigDecimal checkBalance(UUID accountId);
}