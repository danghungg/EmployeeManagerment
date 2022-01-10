package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Department;
import com.vti.form.CreatingDepartmentForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.UppdateDepartmentForm;

public interface IDepartmentService {

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm);
	
	//public Department getDepartmentByID(int id);
	
	public Department getDepartmentByName(String name);

	public void createDepartment(CreatingDepartmentForm form);

	public void updateDepartment(UppdateDepartmentForm form);

	public boolean isDepartmentExistsByName(String name);
	
	public boolean isDepartmentExistsByID(int id);
	
	public Department getDepartmentByID(int id);

	public void deleteDepartment(int id);
	
	
}
