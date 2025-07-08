package com.axis.account.controller;

import com.axis.account.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}