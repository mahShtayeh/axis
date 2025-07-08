package com.axis.account.controller;

import com.axis.account.mapper.AccountMapper;
import com.axis.account.service.AccountService;
import com.axis.account.web.RestResponse;
import com.axis.account.web.request.AccountCreationRequest;
import com.axis.account.web.response.AccountCreationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
