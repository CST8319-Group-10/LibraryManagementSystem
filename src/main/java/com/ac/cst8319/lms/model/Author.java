package com.ac.cst8319.lms.model;

import java.time.Instant;


import lombok.*;

/**
 * Author DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Author {
    private long authorId;
    private String firstName;
    private String lastName;
    private String biography;
    private Instant createdAt;
    private Instant updatedAt;

	/**
	 * Builder enabling constructor.
	 * @param builder The builder to get parameters from.
	 */
	public Author(AuthorBuilder builder) {
		this.authorId = builder.authorId;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
	}

	/**
	 * Set the first name and last name from a full name.
	 * @param fullName A string containing both first and last name.
	 */
	public void setFullName(String fullName) {
		String[] names = fullName.split(" ");
		this.firstName = names[0];
		this.lastName = names[1];
	}
}
