package com.axis.account.service.impl;

import com.axis.account.dto.AccountDTO;
import com.axis.account.exception.AccountNotFoundException;
import com.axis.account.exception.DBFailureException;
import com.axis.account.mapper.AccountMapper;
import com.axis.account.model.Account;
import com.axis.account.repository.AccountRepository;
import com.axis.account.service.AccountService;
import com.axis.account.util.AssertUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Axis accounts services
 *
 * @author Mahmoud Shtayeh
 */
@Transactional
@RequiredArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    /**
     * Axis accounts repository
     */
    private final AccountRepository accountRepository;

    /**
     * Axis account POJOs mapper
     */
    private final AccountMapper accountMapper;

    /**
     * Open an account in Axis
     *
     * @param accountDTO Account details to create
     * @return Created account ID
     */
    @Override
    public UUID openAccount(final AccountDTO accountDTO) {
        final Account account = accountMapper.toEntity(accountDTO);
        final Account savedAccount = accountRepository.save(account);

        AssertUtil.notNull(savedAccount, () -> new DBFailureException("error.account.notSaved"));
        AssertUtil.notNull(savedAccount.getId(), () -> new DBFailureException("error.account.idNotGenerated"));
        return savedAccount.getId();
    }

    /**
     * Retrieves the current balance for a specific account.
     *
     * @param accountId the unique identifier of the account
     * @return the current balance of the account
     */
    @Override
    public BigDecimal checkBalance(final UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId))
                .getBalance();
    }
}