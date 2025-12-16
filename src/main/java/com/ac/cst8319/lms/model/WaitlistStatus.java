package com.ac.cst8319.lms.model;


import java.util.Objects;

import lombok.*;

/**
 * WaitListStatus DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WaitlistStatus {
    private int waitListStatusId;
    private String name;
    
	public WaitlistStatus(int waitListStatusId, String name) {
		super();
		this.waitListStatusId = waitListStatusId;
		this.name = name;
	}
    
	public WaitlistStatus() {
		super();
		this.waitListStatusId = 0;
		this.name = null;
	}

	public int getWaitListStatusId() {
		return waitListStatusId;
	}

	public void setWaitListStatusId(int waitListStatusId) {
		this.waitListStatusId = waitListStatusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WaitlistStatus [waitListStatusId=" + waitListStatusId + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, waitListStatusId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WaitlistStatus other = (WaitlistStatus) obj;
		return Objects.equals(name, other.name) && waitListStatusId == other.waitListStatusId;
	}
}