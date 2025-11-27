package com.ac.cst8319.lms.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Role {
    private int roleId;
    private String name;
    private String description;
}