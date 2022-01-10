package com.vti.dto;

import org.springframework.hateoas.RepresentationModel;

import com.vti.entity.Account;
import com.vti.entity.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO extends RepresentationModel<DepartmentDTO>{

	

	private int id;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String departmentName;
	
	private Role role;
}
