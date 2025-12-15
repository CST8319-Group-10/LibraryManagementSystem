package com.ac.cst8319.lms.model;

/**
 * Helper class for constructing User objects.
 */
public class UserBuilder {
	public long userId;
    public String email;
    public String passwordHash;
    public String firstName;
    public String lastName;
    public String phone;
    public String address;
    public int roleId;
    public int accountStanding;

	/**
	 * Set the builder's user ID.
	 * @param userId The user ID.
	 * @return This builder.
	 */
	public UserBuilder setUserId(long userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * Set the builder's email.
	 * @param email The email string.
	 * @return This builder.
	 */
	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	/**
	 * Set the builder's password hash.
	 * @param passwordHash The password hash as a string.
	 * @return This builder.
	 */
	public UserBuilder setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}

	/**
	 * Set the builder's first name.
	 * @param firstName The first name.
	 * @return This builder.
	 */
	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * Set the builder's last name.
	 * @param lastName The last name.
	 * @return This builder.
	 */
	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * Set the builder's phone number.
	 * @param phone The phone number string.
	 * @return This builder.
	 */
	public UserBuilder setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * Set the builder's address.
	 * @param address The address string.
	 * @return This builder.
	 */
	public UserBuilder setAddress(String address) {
		this.address = address;
		return this;
	}

	/**
	 * Set the builder's role ID.
	 * @param roleId The role ID.
	 * @return This builder.
	 */
	public UserBuilder setRoleId(int roleId) {
		this.roleId = roleId;
		return this;
	}

	/**
	 * Set the builder's account standing.
	 * @param accountStanding The account standing.
	 * @return This builder.
	 */
	public UserBuilder setAccountStanding(int accountStanding) {
		this.accountStanding = accountStanding;
		return this;
	}

	/**
	 * Creates a new User object from this builder.
	 * @return Newly created User object.
	 */
	public User build() {
		return new User(this);
	}
    
    
}
