package com.vti.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountFilterForm;
import com.vti.form.CreatingAccountForm;
import com.vti.form.UppdateAccountForm;

import com.vti.repository.IAccountRepository;
import com.vti.specification.account.AccountSpecification;

@Service
public  class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	public Page<Account> getAllAccounts(
			Pageable pageable, 
			String search, 
			AccountFilterForm filterForm) {
		
		Specification<Account> where = AccountSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	@Override
	public Account getAccountByUserName(String userName) {
		
		return repository.findByUserName(userName);
	}

	@Override
	public Account getAccountByID(int id) {
		// TODO Auto-generated method stub
		return repository.getById(id);
	}

	@Transactional
	public void createAccount(CreatingAccountForm accountForm) {
		TypeMap<CreatingAccountForm, Account> typeMap = modelMapper.getTypeMap(CreatingAccountForm.class, Account.class);
		if (typeMap == null) {
			modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {

				@Override
				protected void configure() {
					skip(destination.getId());
					
				}
			});
		}
		Account account = modelMapper.map(accountForm, Account.class);
		repository.save(account);
		
	}
	
	@Transactional
public void updateAccount(UppdateAccountForm form) {
		
		// convert form to entity
		Account account = modelMapper.map(form, Account.class);
		
		repository.save(account);

	
}
	public boolean isAccountExistsByUsername(String username) {
		return repository.existsByUserName(username);
	}

	
	
//	public boolean isAccountExistsById(int id) {
//		return repository.isAccountExistsById(id);
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = repository.findByUserName(username);

		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(
				account.getUserName(), 
				account.getPassWord(), 
				AuthorityUtils.createAuthorityList(account.getRole().toString()));
	}
	
	public void deleteAccount(int id) {
		repository.deleteById(id);
	}

	
	
	
}


