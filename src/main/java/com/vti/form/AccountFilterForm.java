package com.vti.form;


import com.vti.entity.Department;
import com.vti.entity.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AccountFilterForm {

	private Role role;

	private Department departmentId;

}


