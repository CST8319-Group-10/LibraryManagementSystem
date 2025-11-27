package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "Checkout")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Checkout {
    @Id
    @GeneratedValue
    private long checkoutId;
    private long loanedTo;
    private long bookCopyId;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private long checkedOutBy;
    private long returnedBy;
    private BigDecimal lateFeeAssessed;
    private boolean lateFeePaid;
    private Instant createdAt;

    /* TODO: Add the JOINed members as needed by services
    */
}