package com.qa.service.repository;

import java.util.List;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import com.qa.domain.Account;
import com.qa.util.JSONUtil;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;
import javax.transaction.Transactional;


@Transactional(SUPPORTS)
public class AccountCRUD {
	
	@PersistenceContext(unitName = "primary")
	private EntityManager em;
	
	@Inject
	private JSONUtil util;
	
	public List<Account>getAllAccounts(){ 
		TypedQuery<Account>query = em.createQuery("Select a from Account a", Account.class);
		return query.getResultList();
	}
	@Transactional(REQUIRED)
	public String createAnAccount(String account) {
		Account anAccount = util.getObjectForJSON(account, Account.class);
		em.persist(anAccount);
		return "{\"message\": \"account has been sucessfully added\"}";
	}
	public Account findAnAccount(Long id) {
		return em.find(Account.class, id);
	}
	
	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		Account accountDB = findAnAccount(id);
		if(accountDB != null) {
			em.remove(accountDB);
		}
		return "{\"message\": \"account successfully deleted\"}";
	}
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
	
}
