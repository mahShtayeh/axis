package com.axis.account.service;

import com.axis.account.dto.AccountDTO;
import com.axis.account.exception.AccountNotFoundException;
import com.axis.account.mapper.AccountMapper;
import com.axis.account.model.Account;
import com.axis.account.repository.AccountRepository;
import com.axis.account.service.impl.AccountServiceImpl;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Axis accounts service tests
 *
 * @author Mahmoud Shtayeh
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    /**
     * Axis accounts repository mock
     */
    @Mock
    private AccountRepository accountRepository;

    /**
     * Axis accounts POJOs mapper
     */
    @Mock
    private AccountMapper accountMapper;

    /**
     * AccountService being tested.
     * Injected with mocked dependencies.
     */
    @InjectMocks
    private AccountServiceImpl accountService;

    /**
     * Simulation of a valid account username.
     */
    private static final String TEST_USERNAME = "test@axis.com";

    /**
     * Balance value used in the account-related test cases.
     */
    private static final BigDecimal TEST_BALANCE = new BigDecimal("1000.00");

    /**
     * A unique identifier for testing account-related operations in the service layer.
     */
    private static final UUID TEST_ACCOUNT_ID = UUID.randomUUID();

    /**
     * A transient `Account` instance used for testing purposes.
     * This account does not have an ID and represents an unsaved entity.
     */
    private static final Account TRANSIENT_ACCOUNT = Account.builder()
            .balance(TEST_BALANCE)
            .build();

    /**
     * A static, immutable representation of a saved test `Account` instance used in account-related
     * test cases. This constant represents an account that has been persisted in the system,
     * with a unique identifier and predefined balance.
     */
    private static final Account SAVED_ACCOUNT = Account.builder()
            .id(TEST_ACCOUNT_ID)
            .balance(TEST_BALANCE)
            .build();

    /**
     * This nested test class contains tests related to the functionality of opening
     * accounts using the {@link AccountServiceImpl}.
     */
    @Nested
    @NoArgsConstructor
    class OpenAccountTests {
        /**
         * Tests the {@code openAccount} method in {@link AccountServiceImpl} to ensure
         * that it correctly creates a new account and returns the generated account ID.
         */
        @Test
        void openAccount_withValidAccountDetails_returnsAccountId() {
            when(accountRepository.save(any(Account.class))).thenReturn(SAVED_ACCOUNT);
            when(accountMapper.toEntity(any(AccountDTO.class))).thenReturn(TRANSIENT_ACCOUNT);

            final UUID generatedAccountId = accountService.openAccount(AccountDTO.builder()
                    .username(TEST_USERNAME)
                    .balance(TEST_BALANCE)
                    .build());

            verify(accountMapper).toEntity(argThat(argument ->
                    TEST_USERNAME.equals(argument.username()) && TEST_BALANCE.equals(argument.balance())));
            verify(accountRepository).save(TRANSIENT_ACCOUNT);
            assertThat(generatedAccountId).isEqualTo(TEST_ACCOUNT_ID);
        }
    }

    /**
     * Nested class containing test cases for the {@code checkBalance} method in {@link AccountServiceImpl}.
     */
    @Nested
    @NoArgsConstructor
    class CheckBalanceTests {
        /**
         * Tests the {@code checkBalance} method in {@link AccountServiceImpl} to verify that it returns
         * the correct balance for a given account ID.
         */
        @Test
        void checkBalance_withValidAccountId_returnsCorrectBalance() {
            when(accountRepository.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.of(SAVED_ACCOUNT));

            final BigDecimal currentBalance = accountService.checkBalance(TEST_ACCOUNT_ID);

            verify(accountRepository).findById(TEST_ACCOUNT_ID);
            assertThat(currentBalance).isEqualTo(TEST_BALANCE);
        }

        /**
         * Tests the behavior of the {@code checkBalance} method when called with an invalid account ID.
         * This verifies that the {@code checkBalance} method throws the appropriate exception
         * when the account cannot be found in the repository.
         */
        @Test
        void checkBalance_withInvalidAccountId_throwsNoFoundException() {
            when(accountRepository.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> accountService.checkBalance(TEST_ACCOUNT_ID))
                    .isInstanceOf(AccountNotFoundException.class)
                    .extracting("accountId")
                    .isEqualTo(TEST_ACCOUNT_ID);
        }
    }
}