package com.axis.account.service.impl;

import com.axis.account.dto.AccountDTO;
import com.axis.account.mapper.AccountMapper;
import com.axis.account.model.Account;
import com.axis.account.repository.AccountRepository;
import com.axis.account.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

        Assert.notNull(savedAccount, "error.account.notSaved");
        Assert.notNull(savedAccount.getId(), "error.account.idNotGenerated");
        return savedAccount.getId();
    }
}