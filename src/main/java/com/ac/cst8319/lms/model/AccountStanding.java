package com.ac.cst8319.lms.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountStanding {
    private int standingId;
    private String name;
    private String description;
}