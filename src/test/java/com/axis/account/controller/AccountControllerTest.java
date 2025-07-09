package com.axis.account.controller;

import com.axis.account.web.RestResponse;
import com.axis.account.web.request.AccountCreationRequest;
import com.axis.account.web.response.AccountBalanceResponse;
import com.axis.account.web.response.AccountCreationResponse;
import com.axis.account.web.response.TransactionResponse;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AccountController integration tests.
 *
 * @author Mahmoud Shtayeh
 */
@NoArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerTest {
    /**
     * Test server random port
     */
    @LocalServerPort
    private int port;

    /**
     * A final instance of {@link RestTemplate} used for sending HTTP requests during
     * integration tests for the AccountController.
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Unique identifier for an account used in integration tests.
     */
    private static UUID accountId;

    /**
     * The base URL used for making HTTP requests in the AccountControllerTest.
     */
    private static final String BASE_URL = "http://localhost:";

    /**
     * The base API endpoint for managing account-related operations.
     */
    private static final String ACCOUNTS_API = "/api/v1/accounts";

    /**
     * Simulation of a valid account username.
     */
    private static final String TEST_USERNAME = "test@axis.com";

    /**
     * Balance value used in the account-related test cases.
     */
    private static final BigDecimal TEST_BALANCE = new BigDecimal("1000.00");

    /**
     * Tests that a new account is successfully created via the AccountController
     * and that a non-null account ID is returned in the response payload.
     */
    @Test
    @Order(1)
    void openAccount_withValidDetails_returnsAccountId() {
        final AccountCreationRequest accountCreationRequest = new AccountCreationRequest(TEST_USERNAME, TEST_BALANCE);

        final ResponseEntity<RestResponse<AccountCreationResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API,
                HttpMethod.POST,
                new HttpEntity<>(accountCreationRequest),
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final RestResponse<AccountCreationResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNotNull();
                    assertThat(body.getPayload().accountId()).isNotNull();
                });

        accountId = responseBody.getPayload().accountId();
    }

    /**
     * Tests that attempting to create an account with an invalid email address as the username
     * results in a {@code BAD_REQUEST} HTTP status response from the AccountController.
     */
    @Test
    @Order(2)
    void openAccount_withInvalidEmail_returnsBadRequest() {
        final AccountCreationRequest invalidRequest = new AccountCreationRequest("invalid-email", TEST_BALANCE);

        final ResponseEntity<RestResponse<AccountCreationResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API,
                HttpMethod.POST,
                new HttpEntity<>(invalidRequest),
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        final RestResponse<AccountCreationResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNull();
                    assertThat(body.getErrors())
                            .isNotNull()
                            .hasSize(1)
                            .satisfies(errors ->
                                    assertThat(errors.getFirst().message())
                                            .isEqualTo("Invalid username, must be a valid email"));
                });
    }

    /**
     * Tests that attempting to create an account with an invalid balance
     * (a balance of zero or negative value) results in a {@code BAD_REQUEST}
     * HTTP status response from the AccountController.
     */
    @Test
    @Order(3)
    void openAccount_withInvalidBalance_returnsBadRequest() {
        final AccountCreationRequest invalidRequest = new AccountCreationRequest(TEST_USERNAME, BigDecimal.valueOf(-1000));

        final ResponseEntity<RestResponse<AccountCreationResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API,
                HttpMethod.POST,
                new HttpEntity<>(invalidRequest),
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        final RestResponse<AccountCreationResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNull();
                    assertThat(body.getErrors())
                            .isNotNull()
                            .hasSize(1)
                            .satisfies(errors ->
                                    assertThat(errors.getFirst().message())
                                            .isEqualTo("Invalid balance, enter a valid balance"));
                });
    }

    /**
     * Verifies that retrieving the balance for a valid account ID via the AccountController
     * returns the correct balance and an HTTP status of {@code OK}.
     */
    @Test
    @Order(4)
    void checkBalance_withValidAccountId_returnsCorrectBalance() {
        final ResponseEntity<RestResponse<AccountBalanceResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API + "/{accountId}/balance",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final RestResponse<AccountBalanceResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNotNull();
                    assertThat(body.getPayload().balance()).isEqualByComparingTo(TEST_BALANCE);
                });
    }

    /**
     * Tests that attempting to retrieve the balance of a non-existing account
     * results in an {@code AccountNotFoundException} being thrown.
     */
    @Test
    @Order(5)
    void checkBalance_withNonExistingAccount_returnsNoFound() {
        final UUID nonExistingId = UUID.randomUUID();

        final ResponseEntity<RestResponse<AccountBalanceResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API + "/{accountId}/balance",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                nonExistingId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        final RestResponse<AccountBalanceResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNull();
                    assertThat(body.getErrors()).isNotNull()
                            .hasSize(1)
                            .satisfies(errors -> {
                                assertThat(errors.getFirst().message())
                                        .isEqualTo(MessageFormat
                                                .format("Account with ID: {0}, NOT FOUND", nonExistingId));
                            });
                });
    }

    /**
     * Tests that performing a deposit operation on a valid account with valid details
     * correctly returns a response containing a non-null transaction ID and an HTTP status of {@code CREATED}.
     */
    @Test
    @Order(6)
    void deposit_withValidDetails_returnsTransactionId() {
        final ResponseEntity<RestResponse<TransactionResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API + "/{accountId}/deposits?amount=1000.00",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                },
                accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final RestResponse<TransactionResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNotNull();
                    assertThat(body.getPayload().transactionId()).isNotNull();
                });
    }

    /**
     * Tests that performing a withdrawal operation on a valid account with valid details
     * correctly returns a response containing a non-null transaction ID and an HTTP status of {@code CREATED}.
     */
    @Test
    @Order(7)
    void withdraw_withValidDetails_returnsTransactionId() {
        final ResponseEntity<RestResponse<TransactionResponse>> response = restTemplate.exchange(
                BASE_URL + port + ACCOUNTS_API + "/{accountId}/withdraws?amount=1000.00",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                },
                accountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        final RestResponse<TransactionResponse> responseBody = response.getBody();
        assertThat(responseBody)
                .isNotNull()
                .satisfies(body -> {
                    assertThat(body.getPayload()).isNotNull();
                    assertThat(body.getPayload().transactionId()).isNotNull();
                });
    }
}