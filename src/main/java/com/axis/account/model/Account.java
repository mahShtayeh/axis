package com.axis.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Axis account
 *
 * @author Mahmoud Shtayeh
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends Auditable {
    /**
     * DB generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Owner username
     */
    @Email(message = "error.account.invalidUsername")
    @NotEmpty(message = "error.account.missingOrEmptyUsername")
    private String username;

    /**
     * Current balance
     */
    @NotNull(message = "error.account.missingBalance")
    @Positive(message = "error.account.invalidBalance")
    private BigDecimal balance;

    /**
     * Represents the list of transactions associated with the account.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();
}