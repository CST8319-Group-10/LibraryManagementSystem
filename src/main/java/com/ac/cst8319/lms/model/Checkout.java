package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;


import lombok.*;

/**
 * Checkout DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Checkout {
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
}