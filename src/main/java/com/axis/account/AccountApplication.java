package com.axis.account;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountApplication {
    public static void main(final String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}