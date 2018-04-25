package com.qa.service.repository;

public interface AccountRepository {
	
	String getAllAccounts();
	
	String createAnAccount(String account);
	
	String updateAnAccount(Long id, String accountToUpdate);
	
	String deleteAccount(Long id);
}
