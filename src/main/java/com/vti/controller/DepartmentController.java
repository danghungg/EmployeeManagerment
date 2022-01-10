package com.vti.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.Validate.Department.DepartmentIDExists;
import com.vti.dto.DepartmentDTO;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.CreatingDepartmentForm;
import com.vti.form.DepartmentFilterForm;
import com.vti.form.UppdateDepartmentForm;
import com.vti.service.IDepartmentService;

@RestController
@RequestMapping(value = "api/v1/departments")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Validated
public class DepartmentController {

	@Autowired
	private IDepartmentService service;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	public Page<DepartmentDTO> getAllDepartments(Pageable pageable, DepartmentFilterForm filterForm,
			@RequestParam(name = "search", required = false) String search) {
		System.out.println(pageable);
		Page<Department> entityPages = service.getAllDepartments(pageable, search, filterForm);

		// convert entities --> dtos
		List<DepartmentDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<DepartmentDTO>>() {
		}.getType());

		Page<DepartmentDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;

	}

	@GetMapping(value = "/{id}")
	public DepartmentDTO getDepartmentByID(@PathVariable(name = "id") @DepartmentIDExists int id) {
		Department entity = service.getDepartmentByID(id);

		// convert entity to dto
		DepartmentDTO dto = modelMapper.map(entity, DepartmentDTO.class);

		// dto.add(linkTo(methodOn(DepartmentController.class).getDepartmentByID(id)).withSelfRel());//
		// hiện link "http://localhost:8080/api/v1/accounts/1"

		return dto;
	}

//	@GetMapping(value = "/{id}")
//	public Account getDepartmentByID(@PathVariable(name = "id") @DepartmentIDExists int id) {
//		return service.getAccountByID(id);
//	}
//	

	@PostMapping()
	public void createDepartment(@RequestBody @Valid CreatingDepartmentForm form) {
		service.createDepartment(form);
	}

	@PutMapping(value = "/{id}")
	public void updateDepartment(@DepartmentIDExists @PathVariable(name = "id") int id,
			@RequestBody @Valid UppdateDepartmentForm form) {
		form.setId(id);
		service.updateDepartment(form);

	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteDepartment(@PathVariable(name = "id") int id) {
		service.deleteDepartment(id);
	}

}
