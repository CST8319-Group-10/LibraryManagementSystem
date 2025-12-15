package com.ac.cst8319.lms.model;


import lombok.*;

/**
 * WaitListStatus DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WaitlistStatus {
    private int waitListStatusId;
    private String name;
}