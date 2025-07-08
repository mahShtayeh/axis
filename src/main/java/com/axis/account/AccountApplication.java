package com.axis.account;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Axis financial account main class
 *
 * @author Mahmoud Shtayeh
 */
@SpringBootApplication
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountApplication {
    /**
     * Application main entry point
     *
     * @param args Passed in command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}