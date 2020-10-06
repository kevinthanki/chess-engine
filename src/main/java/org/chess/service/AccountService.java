package org.chess.service;

import java.util.List;

import org.chess.api.entity.Account;
import org.chess.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Account getAccount(String username) {
		List<Account> accounts = accountRepository.findByUsername(username);
		if (!CollectionUtils.isEmpty(accounts)) {
			return accounts.get(0);
		} else {
			return null;
		}
	}

}
