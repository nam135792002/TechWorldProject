package com.techworld.common.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends PersonalBaseEntity{
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastName;
	
	@Column(length = 100)
	private String photos;
	
	private boolean enabled;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public User() {
		
	}

	public User(Integer idUser){
		id = idUser;
	}
	
	public User(String email, String password, String firstName, String lastName, String phoneNumber) {
		
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + this.email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", phoneNumber='" + this.phoneNumber + '\'' +
				", role=" + role +
				'}';
	}



	public boolean hasRole(String roleName){
		if(role.getName().equals(roleName)){
			return true;
		}
		return false;
	}

	@Transient
	public String getFullName(){
		return this.firstName + " " + this.lastName;
	}
}
