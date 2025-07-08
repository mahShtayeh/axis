package com.axis.account.service.impl;

import com.axis.account.repository.AccountRepository;
import com.axis.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Axis accounts services
 *
 * @author Mahmoud Shtayeh
 */
@RequiredArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    /**
     * Axis accounts repository
     */
    private final AccountRepository accountRepository;
}