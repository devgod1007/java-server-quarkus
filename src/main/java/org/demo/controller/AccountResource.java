package org.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.demo.model.Account;
import org.demo.model.AccountStatus;

@Path("/account")
public class AccountResource {

	@Inject
	EntityManager entityManager;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> list(
    		@QueryParam("page") @DefaultValue("0") int pageIndex,
    		@QueryParam("size") @DefaultValue("20") int pageSize) {
    	//LOG.info(String.format("Page=%s Size=%s", pageIndex, pageSize));
    	return entityManager
    			 .createNamedQuery("Accounts.findAll", Account.class) 
    			 .getResultList(); 
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account get(@PathParam("id") Long id) {
    	//return companyService.getCompany(id);
    	return entityManager.find(Account.class, id);
    }
    
    
    @GET
    @Path("/populate")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String populate() {
    	Account account = new Account();
    	account.setAccountNumber(Long.valueOf(223432));
    	account.setAccountStatus(AccountStatus.OPEN);
    	account.setBalance(BigDecimal.valueOf(1882));
    	account.setCustomerName("My Customer");
    	account.setCustomerNumber(Long.valueOf(2));
    	entityManager.persist(account);
        return account.toString();
    }
   
}