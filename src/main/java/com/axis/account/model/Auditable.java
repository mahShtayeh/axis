package com.axis.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * JPA Auditing base class
 *
 * @author Mahmoud Shtayeh
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auditable {
    /**
     * Entity creator name
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * Entity creation timestamp
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    /**
     * Entity last modifier name
     */
    @Column
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * Entity last modification timestamp
     */
    @Column
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}