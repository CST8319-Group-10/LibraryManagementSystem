package com.ac.cst8319.lms.model;


import java.util.Objects;

import lombok.*;

/**
 * ActionCategory DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ActionCategory {
    private int categoryId;
    private String category;
    
	public ActionCategory(int categoryId, String category) {
		super();
		this.categoryId = categoryId;
		this.category = category;
	}
	
	public ActionCategory() {
		super();
		this.categoryId = 0;
		this.category = null;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ActionCategory [categoryId=" + categoryId + ", category=" + category + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionCategory other = (ActionCategory) obj;
		return Objects.equals(category, other.category) && categoryId == other.categoryId;
	}
}