package com.axis.account.service;

import com.axis.account.dto.AccountDTO;
import com.axis.account.exception.AccountNotFoundException;
import com.axis.account.exception.DBFailureException;

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

    /**
     * Deposits a specified amount into the account with the given account ID.
     * Updates the account balance and records the transaction.
     *
     * @param accountId the unique identifier of the account into which the amount is to be deposited
     * @param amount    the amount to be deposited into the account
     * @return the unique identifier of the transaction created for the deposit
     * @throws AccountNotFoundException if the account with the specified ID does not exist
     * @throws DBFailureException       if the transaction fails to save or its ID is not generated
     */
    UUID deposit(UUID accountId, BigDecimal amount);
}