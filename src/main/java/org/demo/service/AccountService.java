package org.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.demo.ApplicationException;
import org.demo.model.Account;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AccountService implements IAccountService {
	
	private static final Logger LOG = Logger.getLogger(AccountService.class);

	@Inject
	private EntityManager entityManager;
	
	@Override
	public List<Account> list(int firstResult, int maxResults) {
		firstResult = (firstResult <= 0)? 1 : firstResult;
		maxResults = (maxResults <= 0)? 1 : maxResults;
		return entityManager.createNamedQuery("Accounts.findAll", Account.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}
	
	@Override
	public Account find(long id) {
		return entityManager.find(Account.class, id);
	}
	
	@Override
	public Account findByAccontNumber(long accountNumber) {
		List<Account> matches = entityManager
				 .createNamedQuery("Accounts.findByAccountNumber", Account.class) 
				 .setParameter("accountNumber", accountNumber)
				 .setMaxResults(1)
				 .getResultList();
		if (matches.isEmpty()) {
			throw new ApplicationException();
		}
		return matches.get(0);
	}
	
	@Override @Transactional
	public Account deposit(long id, BigDecimal amount) {
		LOG.debug(String.format("Deposit %s to Account %s", amount, id));
		Objects.requireNonNull(amount);
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException();
		}
		Account account = entityManager.find(Account.class, id);
		if (account == null) {
			throw new ApplicationException();
		}
		BigDecimal newAmount = account.getBalance().add(amount);
		account.setBalance(newAmount);
		
		return account;
	}
	
	
	@Override
	@Transactional
	public Account register(Account account) {
		Objects.requireNonNull(account);
    	entityManager.persist(account);
        return account;
	}
	
}
