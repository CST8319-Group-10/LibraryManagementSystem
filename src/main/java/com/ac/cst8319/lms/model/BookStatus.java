package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "BookStatus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookStatus {
    @Id
    @GeneratedValue
    private int statusId;
    private String name;
    private String description;
}