package com.axis.account.controller;

import lombok.NoArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * AccountController integration tests.
 *
 * @author Mahmoud Shtayeh
 */
@NoArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
    /**
     * Test server random port
     */
    @LocalServerPort
    private int port;
}