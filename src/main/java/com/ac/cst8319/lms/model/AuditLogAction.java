package com.ac.cst8319.lms.model;


import java.util.Objects;

import lombok.*;

/**
 * AuditLogAction DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLogAction {
    private int auditActionId;
    private String action;
    private int category;
    private String description;
    
	public AuditLogAction(int auditActionId, String action, int category, String description) {
		super();
		this.auditActionId = auditActionId;
		this.action = action;
		this.category = category;
		this.description = description;
	}
    
	public AuditLogAction() {
		super();
		this.auditActionId = 0;
		this.action = null;
		this.category = 0;
		this.description = null;
	}

	public int getAuditActionId() {
		return auditActionId;
	}

	public void setAuditActionId(int auditActionId) {
		this.auditActionId = auditActionId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AuditLogAction [auditActionId=" + auditActionId + ", action=" + action + ", category=" + category
				+ ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, auditActionId, category, description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditLogAction other = (AuditLogAction) obj;
		return Objects.equals(action, other.action) && auditActionId == other.auditActionId
				&& category == other.category && Objects.equals(description, other.description);
	}
}