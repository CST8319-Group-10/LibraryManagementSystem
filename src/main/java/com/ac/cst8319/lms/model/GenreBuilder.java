package com.ac.cst8319.lms.model;

/**
 * Helper class for constructing Genre objects.
 */
public class GenreBuilder {
	public int genreId;
    public String name;
    public String description;

    /**
     * Set the builder's Genre ID.
     * @param genreId The genre ID.
     * @return This builder.
     */
    public GenreBuilder setGenreId(int genreId) {
    	this.genreId = genreId;
    	return this;
    }

    /**
     * Set the builder's name.
     * @param name The name string.
     * @return This builder.
     */
    public GenreBuilder setName(String name) {
    	this.name = name;
    	return this;
    }

    /**
     * Set the builder's description.
     * @param description The description string.
     * @return This builder.
     */
    public GenreBuilder setDescription(String description) {
    	this.description = description;
    	return this;
    }

    /**
     * Create a new Genre object from the builder.
     * @return Newly created Genre object.
     */
    public Genre build() {
    	return new Genre(this);
    }
}
