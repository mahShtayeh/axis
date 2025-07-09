package com.axis.account.model;

import jakarta.persistence.*;
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
    private BigDecimal amount;

    /**
     * The type of the transaction.
     * This can be either a deposit or a withdrawal, defined by the {@code TransactionType} enum.
     */
    private TransactionType type;

    /**
     * Represents a reference to an {@link Account} associated with a transaction.
     */
    @ManyToOne
    @JoinColumn
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