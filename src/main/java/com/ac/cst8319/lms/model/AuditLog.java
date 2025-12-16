package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.util.Objects;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AuditLog {
    private long auditEntryId;
    private long userId;
    private int actionId;
    private String details;
    private String ipAddress;
    private Instant createdAt;
    
	public AuditLog(long auditEntryId, long userId, int actionId, String details, String ipAddress, Instant createdAt) {
		super();
		this.auditEntryId = auditEntryId;
		this.userId = userId;
		this.actionId = actionId;
		this.details = details;
		this.ipAddress = ipAddress;
		this.createdAt = createdAt;
	}
	
	public AuditLog() {
		super();
		this.auditEntryId = 0;
		this.userId = 0;
		this.actionId = 0;
		this.details = null;
		this.ipAddress = null;
		this.createdAt = null;
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

	@Override
	public String toString() {
		return "AuditLog [auditEntryId=" + auditEntryId + ", userId=" + userId + ", actionId=" + actionId + ", details="
				+ details + ", ipAddress=" + ipAddress + ", createdAt=" + createdAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(actionId, auditEntryId, createdAt, details, ipAddress, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditLog other = (AuditLog) obj;
		return actionId == other.actionId && auditEntryId == other.auditEntryId
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(details, other.details)
				&& Objects.equals(ipAddress, other.ipAddress) && userId == other.userId;
	}
}