package com.ac.cst8319.lms.model;

import java.time.Instant;
import java.util.Objects;

import lombok.*;

/**
 * UserAccount DTO.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAccount {
    private long userId;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private int roleId;
    private int accountStanding;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant lastLoginAt;
    private Instant lastReminderSentAt;
    
	public UserAccount(long userId, String email, String passwordHash, String firstName, String lastName, String phone,
			String address, int roleId, int accountStanding, Instant createdAt, Instant updatedAt, Instant lastLoginAt,
			Instant lastReminderSentAt) {
		super();
		this.userId = userId;
		this.email = email;
		this.passwordHash = passwordHash;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.roleId = roleId;
		this.accountStanding = accountStanding;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.lastLoginAt = lastLoginAt;
		this.lastReminderSentAt = lastReminderSentAt;
	}

	public UserAccount() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getAccountStanding() {
		return accountStanding;
	}

	public void setAccountStanding(int accountStanding) {
		this.accountStanding = accountStanding;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Instant getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Instant lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public Instant getLastReminderSentAt() {
		return lastReminderSentAt;
	}

	public void setLastReminderSentAt(Instant lastReminderSentAt) {
		this.lastReminderSentAt = lastReminderSentAt;
	}

	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", email=" + email + ", passwordHash=" + passwordHash + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phone=" + phone + ", address=" + address + ", roleId="
				+ roleId + ", accountStanding=" + accountStanding + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", lastLoginAt=" + lastLoginAt + ", lastReminderSentAt=" + lastReminderSentAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountStanding, address, createdAt, email, firstName, lastLoginAt, lastName,
				lastReminderSentAt, passwordHash, phone, roleId, updatedAt, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		return accountStanding == other.accountStanding && Objects.equals(address, other.address)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastLoginAt, other.lastLoginAt)
				&& Objects.equals(lastName, other.lastName)
				&& Objects.equals(lastReminderSentAt, other.lastReminderSentAt)
				&& Objects.equals(passwordHash, other.passwordHash) && Objects.equals(phone, other.phone)
				&& roleId == other.roleId && Objects.equals(updatedAt, other.updatedAt) && userId == other.userId;
	}
	
}