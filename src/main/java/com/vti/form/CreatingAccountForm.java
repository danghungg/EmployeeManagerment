package com.vti.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.vti.Validate.Account.AccountUsernameNotExists;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingAccountForm {
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
	@NotNull(message = "The Total Member mustn't be null value")
	@PositiveOrZero(message = "The Total Member must be greater than or equal 0")
	private int departmentId;
	@Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER", message = "The type must be ADMIN, EMPLOYEE or MANAGER")
	private String role;
// dùng cái nào gọi api thì postman lấy thuộc tính cái đó.
}
