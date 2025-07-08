package com.axis.account.service;

import com.axis.account.repository.AccountRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Axis accounts service tests
 *
 * @author Mahmoud Shtayeh
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    /**
     * Axis accounts repository mock
     */
    @Mock
    private AccountRepository accountRepository;

    /**
     * AccountService being tested.
     * Injected with mocked dependencies.
     */
    @InjectMocks
    private AccountService accountService;
}