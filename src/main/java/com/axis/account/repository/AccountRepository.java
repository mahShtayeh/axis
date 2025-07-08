package com.axis.account.repository;

import com.axis.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Axis accounts repository
 *
 * @author Mahmoud Shtayeh
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {
}