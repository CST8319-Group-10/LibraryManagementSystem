package com.ac.cst8319.lms.model;

import com.ac.cst8319.lms.model.Prototype.BookStatusClone;

import lombok.*;

/**
 * Book DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookStatus implements BookStatusClone {
    private int statusId;
    private String name;
    private String description;

	/**
	 * Builder enabling constructor.
	 * @param builder The builder to use for parameters.
	 */
	public BookStatus(BookStatusBuilder builder) {
		this.statusId = builder.statusId;
		this.name = builder.name;
		this.description = builder.description;
	}

	/**
	 * Creating a book status by cloning the prototype
	 * @return book status that is created by cloning the prototype
	 */
	@Override
	public BookStatus clone() {
		return new BookStatus(this.statusId, this.name, this.description);
	}
}

