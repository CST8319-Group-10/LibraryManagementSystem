package com.ac.cst8319.lms.model;

import jakarta.persistence.*;

import lombok.*;

@Entity(name = "Role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Role {
    @Id
    @GeneratedValue
    private int roleId;
    private String name;
    private String description;
}