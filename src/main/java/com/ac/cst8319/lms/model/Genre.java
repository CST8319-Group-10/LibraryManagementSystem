package com.ac.cst8319.lms.model;


import lombok.*;

/**
 * Genre DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Genre {
    private int genreId;
    private String name;
    private String description;

	/**
	 * Builder enabling constructor.
	 * @param builder The builder to use for parameters.
	 */
	public Genre(GenreBuilder builder) {
		this.genreId = builder.genreId;
		this.name = builder.name;
		this.description = builder.description;
	}
}
