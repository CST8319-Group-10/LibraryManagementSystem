package com.ac.cst8319.lms.model;

import java.util.Objects;

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

	public BookStatus(int statusId2, String name2, String description2) {
	}
	
	public BookStatus() {
	}

	/**
	 * Creating a book status by cloning the prototype
	 * @return book status that is created by cloning the prototype
	 */
	@Override
	public BookStatus clone() {
		return new BookStatus(this.statusId, this.name, this.description);
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BookStatus [statusId=" + statusId + ", name=" + name + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name, statusId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookStatus other = (BookStatus) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& statusId == other.statusId;
	}

	
}

