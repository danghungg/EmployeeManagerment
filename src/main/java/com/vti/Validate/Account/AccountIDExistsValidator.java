package com.vti.Validate.Account;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vti.Validate.Department.DepartmentIDExists;
import com.vti.service.IDepartmentService;

public class AccountIDExistsValidator implements ConstraintValidator<AccountIDExists, Integer> {

	@Autowired
	private IDepartmentService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(id)) {
			return true;
		}

		return service.isDepartmentExistsByID(id);
	}
}
