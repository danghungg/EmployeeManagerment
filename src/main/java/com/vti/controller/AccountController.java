package com.vti.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.lang.reflect.Type;
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
import com.vti.dto.AccountDTO;
import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import com.vti.form.CreatingAccountForm;
import com.vti.form.UppdateAccountForm;
import com.vti.service.IAccountService;

import net.bytebuddy.description.type.TypeVariableToken;

@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@Validated
public class AccountController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IAccountService service;

	@GetMapping()
	public Page<AccountDTO> getAllAccounts(
			Pageable pageable, 
			@RequestParam(value = "search", required = false) String search,
			AccountFilterForm filterForm) {

		Page<Account> entityPages = service.getAllAccounts(pageable, search, filterForm);

		// convert entities --> dtos
		List<AccountDTO> dtos = modelMapper.map(
				entityPages.getContent(), 
				new TypeToken<List<AccountDTO>>() {}.getType());

		Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;
	}
	
	@GetMapping(value = "/username/{userName}")
	public AccountDTO getAccountByUserName(@PathVariable(name = "userName") String userName) {
		Account acc = service.getAccountByUserName(userName);
		AccountDTO dto = modelMapper.map(acc, AccountDTO.class );
		System.out.println(dto);
		return dto;
		
	}
	
	@GetMapping(value = "/{id}")
	public AccountDTO getAccountByID(@PathVariable(name = "id") int id) {
		Account entity = service.getAccountByID(id);

		// convert entity to dto
		AccountDTO dto = modelMapper.map(entity, AccountDTO.class);
		
		dto.add(linkTo(methodOn(AccountController.class).getAccountByID(id)).withSelfRel());

		return dto;
	}
//	@GetMapping(value = "/{id}")
//	public Account getAccountByID(@PathVariable(name = "id") @DepartmentIDExists int id) {
//		return service.getAccountByID(id);
//	}
	
	@PostMapping()
	public void createAccount(@RequestBody @Valid CreatingAccountForm accountForm) {
		service.createAccount(accountForm);
	}

	@PutMapping(value = "/{id}")
	public void updateAccount(
			@PathVariable(name = "id") int id, 
			@RequestBody @Valid UppdateAccountForm form) {
		form.setId(id);
		service.updateAccount(form);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteAccount(@PathVariable(name = "id") int id) {
		service.deleteAccount(id);
	}

}
