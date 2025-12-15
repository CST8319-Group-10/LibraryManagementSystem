package com.ac.cst8319.lms.model;

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
    
	public UserBuilder setUserId(long userId) {
		this.userId = userId;
		return this;
	}
	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	public UserBuilder setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}
	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public UserBuilder setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public UserBuilder setAddress(String address) {
		this.address = address;
		return this;
	}
	public UserBuilder setRoleId(int roleId) {
		this.roleId = roleId;
		return this;
	}
	public UserBuilder setAccountStanding(int accountStanding) {
		this.accountStanding = accountStanding;
		return this;
	}
	
	public User build() {
		return new User(this);
	}
    
    
}
