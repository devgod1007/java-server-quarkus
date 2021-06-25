package org.demo.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.demo.model.Account;

@ApplicationScoped
public class AccountService implements IAccountService {

	@Inject
	private EntityManager entityManager;
	
	public List<Account> list() {
		return entityManager.createNamedQuery("Accounts.findAll", Account.class).getResultList();
	}
	
	public Account find(Long id) {
		return entityManager.find(Account.class, id);
	}
	
	
	@Override
	@Transactional
	public Account register(Account account) {
    	entityManager.persist(account);
        return account;
	}
	
}
