package com.vti.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.CreatingDepartmentForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.UppdateDepartmentForm;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.department.DepartmentSpecification;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IDepartmentRepository repository;

	@Autowired
	private IAccountRepository accountRepository;

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm) {

		Specification<Department> where = DepartmentSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	@Override
	public Department getDepartmentByID(int id) {
		// TODO Auto-generated method stub
		return repository.getById(id);
	}

	@Override
	public Department getDepartmentByName(String name) {
		// TODO Auto-generated method stub
		return repository.findByName(name);
	}

	@Transactional
	public void createDepartment(CreatingDepartmentForm form) {
		// convert form to entity
		Department departmentEntity = modelMapper.map(form, Department.class);
		// create department
		Department department = repository.save(departmentEntity);
		// update accounts
		List<Account> accountEntities = departmentEntity.getAccounts();
		if (accountEntities != null && accountEntities.size() > 0) {
			for (Account account : accountEntities) {
				account = accountRepository.getById(account.getId());
				account.setDepartment(department);
				accountRepository.save(account);
			}
		}
		// accountRepository.saveAll(accountEntities);
	}

	@Transactional
	public void updateDepartment(UppdateDepartmentForm form) {
		// load lại department
		Department department_old = repository.findById(form.getId()).get();
		// convert form to entity
		Department department = modelMapper.map(form, Department.class);
		department.setTotalMember(department_old.getTotalMember());
		department.setCreateDate(department_old.getCreateDate());
		repository.save(department);

	}

	public boolean isDepartmentExistsByName(String name) {
		return repository.existsByName(name);
	}

	public boolean isDepartmentExistsByID(int id) {
		return repository.existsById(id);
	}

	public void deleteDepartment(int id) {
		repository.deleteById(id);
	}
}
