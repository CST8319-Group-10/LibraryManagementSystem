package com.ac.cst8319.lms.model;

import java.time.LocalDate;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookCopy {
    private long bookCopyId;
    private long bookId;
    private int statusId;
    private String location;
    private LocalDate aquisitionDate;

    /* TODO: Add the JOINed members as needed by services
    */
}