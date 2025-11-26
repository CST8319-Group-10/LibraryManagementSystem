package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "WaitListStatus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WaitlistStatus {
    @Id
    @GeneratedValue
    private int waitListStatusId;
    private String name;
}