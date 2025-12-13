package com.ac.cst8319.lms.model;

public class Genre {
	private int genreId;
    private String name;
    private String description;
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
    
	public Genre(GenreBuilder builder) {
		this.genreId = builder.genreId;
		this.name = builder.name;
		this.description = builder.description;
	}
}
