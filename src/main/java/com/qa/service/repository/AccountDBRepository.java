package com.qa.service.repository;

import java.util.Collection;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



import com.qa.domain.Account;
import com.qa.util.JSONUtil;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;
import javax.transaction.Transactional;


@Transactional(SUPPORTS)
@Default
public class AccountDBRepository implements AccountRepository {
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;
	
	@Inject
	private JSONUtil util;
	
	@Override
	public String getAllAccounts() {
		Query query = em.createQuery("Select a FROM Account a");
		Collection<Account> accounts = (Collection<Account>) query.getResultList();
		return util.getJSONForObject(accounts);
	}
	
	@Override
	@Transactional(REQUIRED)
	public String createAnAccount(String account) {
		Account anAccount = util.getObjectForJSON(account, Account.class);
		em.persist(anAccount);
		return "{\"message\": \"account has been sucessfully added\"}";
	}
	public Account findAnAccount(Long id) {
		return em.find(Account.class, id);
	}
	
	@Override
	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		Account accountDB = findAnAccount(id);
		if(accountDB != null) {
			em.remove(accountDB);
		}
		return "{\"message\": \"account successfully deleted\"}";
	}
	
	@Override
	@Transactional(REQUIRED)
	public String updateAnAccount(Long id, String accountToUpdate) {
		Account updatedAccount = util.getObjectForJSON(accountToUpdate, Account.class);
		Account accountInDB = findAnAccount(id);
		if(accountToUpdate != null) {
			accountInDB = updatedAccount;
			em.merge(accountInDB);
		}
		return "{\"message\"; \"account sucessfully updated\"}";
	}
	
	public void setUtil(JSONUtil util) {
		this.util = util;
	}
	
	public void setManager(EntityManager em) {
		this.em = em;
	}
	
}
