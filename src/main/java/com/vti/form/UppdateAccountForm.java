package com.vti.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.vti.Validate.Account.AccountUsernameNotExists;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UppdateAccountForm {
	private int id;
	
	@NotBlank(message = "The name mustn't be null value")
	@Length(max = 50, message = "The name's length is max 50 characters")
	@AccountUsernameNotExists
	private String userName;
	@NotBlank(message = "The passWord mustn't be null value")
	@Length(max = 12,min=6, message = "The name's length is max 12 characters")
	private String passWord;
	@NotBlank(message = "The name mustn't be null value")
	@Length(max = 50, message = "The name's length is max 50 characters")
	private String firstName;
	@NotBlank(message = "The name mustn't be null value")
	@Length(max = 50, message = "The name's length is max 50 characters")
	private String lastName;
	private int departmentId;
	
	private String role;
}
