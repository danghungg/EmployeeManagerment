package com.vti.form;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.vti.Validate.Account.AccountIDExists;
import com.vti.Validate.Account.AccountUsernameNotExists;
import com.vti.Validate.Department.DepartmentNameNotExists;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UppdateDepartmentForm {

	private int id;
	@NotBlank(message = "The name mustn't be null value")
	@Length(max = 50, message = "The name's length is max 50 characters")
	@DepartmentNameNotExists
	private String name;

	@Pattern(regexp = "DEV|TEST|PM|ScrumMaster", message = "The type must be DEV, TEST or PM")
	private String type;

	private Date createDate;

	private List<@Valid Account> accounts;

	@Data
	@NoArgsConstructor
	public static class Account {
		@PositiveOrZero(message = "The Total Member must be greater than or equal 0")
		@AccountIDExists
		private int id;

	}
}
