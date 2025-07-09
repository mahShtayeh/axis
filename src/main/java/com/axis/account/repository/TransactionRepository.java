package com.axis.account.repository;

import com.axis.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for managing {@code Transaction} entities.
 * Extends {@code JpaRepository} to provide common JPA database operations.
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}