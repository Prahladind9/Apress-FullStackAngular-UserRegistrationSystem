package com.apress.rao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="Users")
public class UsersDTO {
	
	//JSR 303 Annotations
	
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long id;

	@NotEmpty(message = "error.name.empty")
	@Length(max = 50, message = "error.name.length")
	@Column(name = "NAME")
	private String name;
	
	@NotEmpty(message = "error.address.empty")
	@Length(max = 150, message = "error.address.length")
	@Column(name = "ADDRESS")
	private String address;
	
	@Email(message = "error.email.email")
	@NotEmpty(message = "error.email.empty")
	@Length(max = 80, message = "error.email.length")
	@Column(name = "EMAIL")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getErrorMessage(){
		return "";
	}
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + "]";
	}
	
	
}
