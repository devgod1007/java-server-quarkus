package org.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.demo.model.Account;
import org.demo.model.AccountStatus;
import org.demo.service.IAccountService;

@Path("/account")
public class AccountResource {
	
	@Inject
	private IAccountService accountService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> list(
    		@QueryParam("page") @DefaultValue("0") int pageIndex,
    		@QueryParam("size") @DefaultValue("20") int pageSize) {
    	//LOG.info(String.format("Page=%s Size=%s", pageIndex, pageSize));
    	return accountService.list();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account get(@PathParam("id") Long id) {
    	return accountService.find(id);
    }
    
    
    @GET
    @Path("/populate")
    @Produces(MediaType.TEXT_PLAIN)
    public String populate() {
    	Account account = new Account();
    	account.setAccountNumber(Long.valueOf(223432));
    	account.setAccountStatus(AccountStatus.OPEN);
    	account.setBalance(BigDecimal.valueOf(1882));
    	account.setCustomerName("My Customer");
    	account.setCustomerNumber(Long.valueOf(2));
    	accountService.register(account);
        return account.toString();
    }
   
}