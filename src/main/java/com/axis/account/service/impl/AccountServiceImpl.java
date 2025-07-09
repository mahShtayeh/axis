package com.axis.account.service.impl;

import com.axis.account.dto.AccountDTO;
import com.axis.account.exception.AccountNotFoundException;
import com.axis.account.exception.DBFailureException;
import com.axis.account.exception.InsufficientFundsException;
import com.axis.account.mapper.AccountMapper;
import com.axis.account.model.Account;
import com.axis.account.model.Transaction;
import com.axis.account.repository.AccountRepository;
import com.axis.account.repository.TransactionRepository;
import com.axis.account.service.AccountService;
import com.axis.account.util.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Axis accounts' Transactions repository
     */
    private final TransactionRepository transactionRepository;

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
    @Transactional(readOnly = true)
    public BigDecimal checkBalance(final UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId))
                .getBalance();
    }

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
    @Override
    public UUID deposit(final UUID accountId, final BigDecimal amount) {
        final Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.setBalance(account.getBalance().add(amount));
        final Transaction transientTransaction = Transaction.builder()
                .account(account)
                .amount(amount)
                .type(Transaction.TransactionType.DEPOSIT)
                .build();
        final Transaction savedTransaction = transactionRepository.save(transientTransaction);

        AssertUtil.notNull(savedTransaction, () -> new DBFailureException("error.transaction.notSaved"));
        AssertUtil.notNull(savedTransaction.getId(), () -> new DBFailureException("error.transaction.idNotGenerated"));
        return savedTransaction.getId();
    }

    /**
     * Withdraws a specified amount from the account with the given account ID.
     * Updates the account balance and records the transaction.
     *
     * @param accountId the unique identifier of the account from which the amount is to be withdrawn
     * @param amount    the amount to be withdrawn from the account
     * @return the unique identifier of the transaction created for the withdrawal
     * @throws AccountNotFoundException if the account with the specified ID does not exist
     * @throws DBFailureException       if the transaction fails to save, its ID is not generated,
     *                                  or the account has insufficient funds
     */
    @Override
    public UUID withdraw(final UUID accountId, final BigDecimal amount) {
        final Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        AssertUtil.isTrue(account.getBalance().compareTo(amount) >= 0,
                () -> InsufficientFundsException.builder()
                        .accountId(accountId)
                        .balance(account.getBalance())
                        .amount(amount)
                        .build());

        account.setBalance(account.getBalance().subtract(amount));
        final Transaction transientTransaction = Transaction.builder()
                .account(account)
                .amount(amount)
                .type(Transaction.TransactionType.WITHDRAWAL)
                .build();
        final Transaction savedTransaction = transactionRepository.save(transientTransaction);

        AssertUtil.notNull(savedTransaction, () -> new DBFailureException("error.transaction.notSaved"));
        AssertUtil.notNull(savedTransaction.getId(), () -> new DBFailureException("error.transaction.idNotGenerated"));
        return savedTransaction.getId();
    }
}