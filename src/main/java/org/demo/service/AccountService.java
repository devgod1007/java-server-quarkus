package org.demo.service;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.demo.model.Account;

@ApplicationScoped
public class AccountService implements IAccountService {

	@Inject
	private EntityManager entityManager;
	
	public List<Account> list(int firstResult, int maxResults) {
		firstResult = (firstResult <= 0)? 1 : firstResult;
		maxResults = (maxResults <= 0)? 1 : maxResults;
		return entityManager.createNamedQuery("Accounts.findAll", Account.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}
	
	public Account find(long id) {
		return entityManager.find(Account.class, id);
	}
	
	
	@Override
	@Transactional
	public Account register(Account account) {
		Objects.requireNonNull(account);
    	entityManager.persist(account);
        return account;
	}
	
}
