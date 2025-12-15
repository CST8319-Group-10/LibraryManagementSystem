package com.ac.cst8319.lms.model;

/**
 * User DTO.
 */
public class User {
	private long userId;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private int roleId;
    private int accountStanding;

	/**
	 * Get the User's ID.
	 * @return The user's ID.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Set the user's ID.
	 * @param userId The user's ID.
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * Get the user's email.
	 * @return The email as a string.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the user's email.
	 * @param email The email as a string
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the user's password hash
	 * @return The password hash as a string.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Set the user's password hash.
	 * @param passwordHash The user's password hash as a string.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Get the user's first name.
	 * @return The user's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the user's first name.
	 * @param firstName The user's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the user's last name.
	 * @return The user's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the user's last name.
	 * @param lastName The user's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the user's phone number.
	 * @return The user's phone number.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set the user's phone number.
	 * @param phone The phone number as a string.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get the user's address.
	 * @return The user's address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the user's address.
	 * @param address The user's address as a string.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the user's role ID.
	 * @return The user's role ID.
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * Set the user's role ID.
	 * @param roleId The role ID.
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * Get the user's account standing.
	 * @return The user's account standing.
	 */
	public int getAccountStanding() {
		return accountStanding;
	}

	/**U
	 * Set the user's account standing ID.
	 * @param accountStanding The account standing ID.
	 */
	public void setAccountStanding(int accountStanding) {
		this.accountStanding = accountStanding;
	}

	/**
	 * Builder enabling constructor.
	 * @param builder The builder to use for construction parameters.
	 */
	public User(UserBuilder builder) {
		this.userId = builder.userId;
		this.email = builder.email;
		this.passwordHash = builder.passwordHash;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.phone = builder.phone;
		this.address = builder.address;
		this.roleId = builder.roleId;
		this.accountStanding = builder.accountStanding;
	}
    
}
