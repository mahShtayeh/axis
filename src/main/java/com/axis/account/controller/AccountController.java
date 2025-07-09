package com.axis.account.controller;

import com.axis.account.mapper.AccountMapper;
import com.axis.account.service.AccountService;
import com.axis.account.web.RestResponse;
import com.axis.account.web.request.AccountCreationRequest;
import com.axis.account.web.request.TransactionRequest;
import com.axis.account.web.response.AccountBalanceResponse;
import com.axis.account.web.response.AccountCreationResponse;
import com.axis.account.web.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * APIs for managing Axis financial accounts.
 *
 * @author Mahmoud Shtayeh
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts API", description = "Manage Axis financial accounts")
public class AccountController {
    /**
     * Axis accounts services provider
     */
    private final AccountService accountService;

    /**
     * Axis account POJOs mapper
     */
    private final AccountMapper accountMapper;

    /**
     * Open an account in Axis
     *
     * @param request Account open request
     * @return ApiResponse contains the account ID
     */
    @Operation(summary = "Open Account", description = "Open an account in Axis")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<AccountCreationResponse> openAccount(@RequestBody @Valid final AccountCreationRequest request) {
        final UUID accountId = accountService.openAccount(accountMapper.toDTO(request));
        return RestResponse.ok(AccountCreationResponse.builder()
                .accountId(accountId)
                .build());
    }

    /**
     * Retrieves the current balance of the specified account.
     *
     * @param accountId Unique identifier of the account whose balance is to be retrieved.
     * @return A {@code RestResponse} containing an {@code AccountBalanceResponse} with the current balance of the account.
     */
    @Operation(summary = "Check Account Balance", description = "Retrieve the current balance of the specified account")
    @ApiResponse(responseCode = "200", description = "Account balance retrieved successfully")
    @GetMapping("{accountId}/balance")
    public RestResponse<AccountBalanceResponse> checkBalance(@PathVariable final UUID accountId) {
        final BigDecimal balance = accountService.checkBalance(accountId);
        return RestResponse.ok(AccountBalanceResponse.builder()
                .balance(balance)
                .build());
    }

    /**
     * Deposits a specified amount into the account identified by the given account ID.
     *
     * @param accountId the unique identifier of the account into which the deposit is to be made
     * @param request   the deposit request containing the amount to be deposited
     * @return a {@code RestResponse} containing a {@code DepositResponse} with the transaction ID of the completed deposit
     */
    @Operation(summary = "Deposit to Account", description = "Deposit to an account in Axis")
    @ApiResponse(responseCode = "201", description = "Deposit created successfully")
    @PostMapping("/{accountId}/deposits")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<TransactionResponse> deposit(@PathVariable final UUID accountId,
                                                     final TransactionRequest request) {
        final UUID transactionId = accountService.deposit(accountId, request.amount());
        return RestResponse.ok(TransactionResponse.builder()
                .transactionId(transactionId)
                .build());
    }

    /**
     * Performs a withdrawal from the specified account.
     *
     * @param accountId the unique identifier of the account from which the withdrawal is to be made
     * @param request   the withdrawal request containing the amount to be withdrawn
     * @return a {@code RestResponse} containing a {@code TransactionResponse} with the transaction ID of the completed withdrawal
     */
    @Operation(summary = "Withdraw from Account", description = "Withdraw from an account in Axis")
    @ApiResponse(responseCode = "201", description = "Withdraw created successfully")
    @PostMapping("/{accountId}/withdraws")
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<TransactionResponse> withdraw(@PathVariable final UUID accountId,
                                                      final TransactionRequest request) {
        final UUID transactionId = accountService.withdraw(accountId, request.amount());
        return RestResponse.ok(TransactionResponse.builder()
                .transactionId(transactionId)
                .build());
    }
}
