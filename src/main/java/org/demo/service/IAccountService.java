package org.demo.service;

import java.util.List;

import org.demo.model.Account;

public interface IAccountService {
	
	List<Account> list();
	
	Account find(Long id);

	Account register(Account account);

}
