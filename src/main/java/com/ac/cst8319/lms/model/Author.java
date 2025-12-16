package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.util.Objects;

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

	public Author() {
		super();
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Author [authorId=" + authorId + ", firstName=" + firstName + ", lastName=" + lastName + ", biography="
				+ biography + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId, biography, createdAt, firstName, lastName, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		return authorId == other.authorId && Objects.equals(biography, other.biography)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(updatedAt, other.updatedAt);
	}
}
