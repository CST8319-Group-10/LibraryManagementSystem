package com.ac.cst8319.lms.model;


import lombok.*;

/**
 * ActionCategory DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ActionCategory {
    private int categoryId;
    private String category;
}