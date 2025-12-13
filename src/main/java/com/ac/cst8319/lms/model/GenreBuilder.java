package com.ac.cst8319.lms.model;

public class GenreBuilder {
	public int genreId;
    public String name;
    public String description;
    
    public GenreBuilder setGenreId(int genreId) {
    	this.genreId = genreId;
    	return this;
    }
    
    public GenreBuilder setName(String name) {
    	this.name = name;
    	return this;
    }
    
    public GenreBuilder setDescription(String description) {
    	this.description = description;
    	return this;
    }
    
    public Genre build() {
    	return new Genre(this);
    }
}
