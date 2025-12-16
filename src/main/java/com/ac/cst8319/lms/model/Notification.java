package com.ac.cst8319.lms.model;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.*;

/**
 * Notification DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Notification {
    private long notificationId;
    private long userId;
    private String message;
    private int notificationTypeId;
    private LocalDateTime sentAt;
    
	public Notification(long notificationId, long userId, String message, int notificationTypeId,
			LocalDateTime sentAt) {
		super();
		this.notificationId = notificationId;
		this.userId = userId;
		this.message = message;
		this.notificationTypeId = notificationTypeId;
		this.sentAt = sentAt;
	}

	public Notification() {
		super();
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(int notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", userId=" + userId + ", message=" + message
				+ ", notificationTypeId=" + notificationTypeId + ", sentAt=" + sentAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, notificationId, notificationTypeId, sentAt, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		return Objects.equals(message, other.message) && notificationId == other.notificationId
				&& notificationTypeId == other.notificationTypeId && Objects.equals(sentAt, other.sentAt)
				&& userId == other.userId;
	}
}