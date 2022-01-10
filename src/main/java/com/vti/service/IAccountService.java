package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.AccountFilterForm;
import com.vti.form.CreatingAccountForm;
import com.vti.form.UppdateAccountForm;


public interface IAccountService extends UserDetailsService{
	 
	public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm);	
	
	public Account getAccountByID(int id);
	
	public Account getAccountByUserName(String userName);

	public void createAccount(CreatingAccountForm accountForm);

	public void updateAccount(UppdateAccountForm form);

	public boolean isAccountExistsByUsername(String username);
	
	//public boolean isAccountExistsById(int id);
	
	public void deleteAccount(int id);
}
