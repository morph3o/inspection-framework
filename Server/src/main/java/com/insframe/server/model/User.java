package com.insframe.server.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("serial")
@Document(collection="users")
public class User implements Serializable{
	
	@Id
	private String id;
	@Indexed(unique=true)
	private String userName;
	private String emailAddress;
	private String password;
	private String role;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String mobileNumber;
	
	public User() {
		super();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", emailAddress="
				+ emailAddress + ", password=" + password + ", role=" + role
				+ ", lastName=" + lastName + ", firstName=" + firstName
				+ ", phoneNumber=" + phoneNumber + ", mobileNumber="
				+ mobileNumber + "]";
	}

	public User(String userName, String emailAddress, String password,
			String role, String lastName, String firstName, String phoneNumber,
			String mobileNumber) {
		super();
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.role = role;
		this.lastName = lastName;
		this.firstName = firstName;
		this.phoneNumber = phoneNumber;
		this.mobileNumber = mobileNumber;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
