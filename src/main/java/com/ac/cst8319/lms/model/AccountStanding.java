package com.ac.cst8319.lms.model;

import java.util.Objects;

import lombok.*;

/**
 * AccountStanding DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountStanding {
    private int standingId;
    private String name;
    private String description;
    
	public AccountStanding(int standingId, String name, String description) {
		super();
		this.standingId = standingId;
		this.name = name;
		this.description = description;
	}
	
	public AccountStanding() {
		super();
		this.standingId = 0;
		this.name = null;
		this.description = null;
	}
	
	
	public int getStandingId() {
		return standingId;
	}

	public void setStandingId(int standingId) {
		this.standingId = standingId;
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
		return "AccountStanding [standingId=" + standingId + ", name=" + name + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name, standingId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountStanding other = (AccountStanding) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& standingId == other.standingId;
	}
	
}