package com.axis.account.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * JPA Auditing configurations
 *
 * @author Mahmoud Shtayeh
 */
@Configuration
@NoArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {
    /**
     * Static username for simplicity
     * In real system SecurityContextHolder would be used
     */
    private static final String USERNAME = "Axis";

    /**
     * Auditor provider
     *
     * @return Auditor created using the static username
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(USERNAME);
    }
}