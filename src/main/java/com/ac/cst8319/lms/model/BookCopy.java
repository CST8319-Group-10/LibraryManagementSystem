package com.ac.cst8319.lms.model;

import java.time.LocalDate;

import com.ac.cst8319.lms.model.Prototype.BookCopyClone;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BookCopy implements BookCopyClone {
    private long bookCopyId;
    private long bookId;
    private int statusId;
    private String location;
    private LocalDate acquisitionDate;

	public BookCopy(BookCopyBuilder builder) {
		this.bookCopyId = builder.bookCopyId;
		this.bookId = builder.bookId;
		this.statusId = builder.statusId;
		this.location = builder.location;
		this.acquisitionDate = builder.acquisitionDate;
	}

	public BookCopy(Long bookId, int statusId) {
		this.bookId = bookId;
		this.statusId = statusId;
	}

	/**
	 * Creating a book copy by cloning the prototype
	 * @return book copy that is created by cloning the prototype
	 */
	@Override
	public BookCopy clone() {
		return new BookCopy(this.bookId, this.statusId);
	}

}

