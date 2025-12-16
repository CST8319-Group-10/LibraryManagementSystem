package com.ac.cst8319.lms.model;


import java.util.Objects;

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
	
	public Genre() {
		this.genreId = 0;
		this.name = null;
		this.description = null;
	}
    public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
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
		return "Genre [genreId=" + genreId + ", name=" + name + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, genreId, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		return Objects.equals(description, other.description) && genreId == other.genreId
				&& Objects.equals(name, other.name);
	}

}
