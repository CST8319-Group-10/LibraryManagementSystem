package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "ActionCategory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ActionCategory {
    @Id
    @GeneratedValue
    private int categoryId;
    private String category;
}