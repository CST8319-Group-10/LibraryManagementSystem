package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "AccountStanding")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountStanding {
    @Id
    @GeneratedValue
    private int standingId;
    private String name;
    private String description;
}