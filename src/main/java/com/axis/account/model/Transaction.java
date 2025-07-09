package com.axis.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Axis account transaction
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
public class Transaction extends Auditable {
    /**
     * DB generated UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The monetary amount associated with a transaction.
     * Represents the value of the transaction, which can be either a deposit or a withdrawal.
     */
    @NotNull(message = "error.account.missingTransactionAmount")
    @Positive(message = "error.account.invalidTransactionAmount")
    private BigDecimal amount;

    /**
     * The type of the transaction.
     * This can be either a deposit or a withdrawal, defined by the {@code TransactionType} enum.
     */
    @NotNull(message = "error.transaction.missingType")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    /**
     * Represents a reference to an {@link Account} associated with a transaction.
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    /**
     * Represents the type of financial transaction.
     *
     * @author Mahmuod Shtayeh
     */
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }
}