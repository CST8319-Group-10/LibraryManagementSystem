package com.ac.cst8319.lms.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "BookCopy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookCopy {
    @Id
    @GeneratedValue
    private long bookCopyId;
    private long bookId;
    private int statusId;
    private String location;
    private LocalDate aquisitionDate;

    /* TODO: Add the JOINed members as needed by services
    */
}