package com.charter.reward.db.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private Long id;

	@NotBlank(message = "The username is required.")
	@Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
	private String username;

	@NotEmpty(message = "The email is required.")
	@Email(message = "The email is not a valid email.")
	private String email;

	@NotBlank(message = "The password is required.")
	private String password;

	private String phoneNumber;

	

	@Override
	public String toString() {
		return "Customer [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + "]";
	}

}
