package com.axis.account.service;

import com.axis.account.dto.AccountDTO;

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
}