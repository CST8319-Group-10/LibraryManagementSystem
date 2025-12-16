package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.util.Objects;

import lombok.*;

/**
 * Enhanced AuditLog entry with JOINed user and action information for display.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLogEntry {
    // From AuditLog
    private long auditEntryId;
    private long userId;
    private int actionId;
    private String details;
    private String ipAddress;
    private Instant createdAt;

    // From UserAccount (JOIN)
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private int roleId;

    // From Role (JOIN)
    private String roleName;

    // From AuditLogAction (JOIN)
    private String actionName;
    private String actionDescription;
    
	public AuditLogEntry(long auditEntryId, long userId, int actionId, String details, String ipAddress,
			Instant createdAt, String userEmail, String userFirstName, String userLastName, int roleId, String roleName,
			String actionName, String actionDescription) {
		super();
		this.auditEntryId = auditEntryId;
		this.userId = userId;
		this.actionId = actionId;
		this.details = details;
		this.ipAddress = ipAddress;
		this.createdAt = createdAt;
		this.userEmail = userEmail;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.actionName = actionName;
		this.actionDescription = actionDescription;
	}
    
	public AuditLogEntry() {
		super();
		this.auditEntryId = 0;
		this.userId = 0;
		this.actionId = 0;
		this.details = null;
		this.ipAddress = null;
		this.createdAt = null;
		this.userEmail = null;
		this.userFirstName = null;
		this.userLastName = null;
		this.roleId = 0;
		this.roleName = null;
		this.actionName = null;
		this.actionDescription = null;
		
		
	}

	public long getAuditEntryId() {
		return auditEntryId;
	}

	public void setAuditEntryId(long auditEntryId) {
		this.auditEntryId = auditEntryId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	@Override
	public String toString() {
		return "AuditLogEntry [auditEntryId=" + auditEntryId + ", userId=" + userId + ", actionId=" + actionId
				+ ", details=" + details + ", ipAddress=" + ipAddress + ", createdAt=" + createdAt + ", userEmail="
				+ userEmail + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName + ", roleId="
				+ roleId + ", roleName=" + roleName + ", actionName=" + actionName + ", actionDescription="
				+ actionDescription + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(actionDescription, actionId, actionName, auditEntryId, createdAt, details, ipAddress,
				roleId, roleName, userEmail, userFirstName, userId, userLastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditLogEntry other = (AuditLogEntry) obj;
		return Objects.equals(actionDescription, other.actionDescription) && actionId == other.actionId
				&& Objects.equals(actionName, other.actionName) && auditEntryId == other.auditEntryId
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(details, other.details)
				&& Objects.equals(ipAddress, other.ipAddress) && roleId == other.roleId
				&& Objects.equals(roleName, other.roleName) && Objects.equals(userEmail, other.userEmail)
				&& Objects.equals(userFirstName, other.userFirstName) && userId == other.userId
				&& Objects.equals(userLastName, other.userLastName);
	}
}
