package org.chess.api;

import java.util.Objects;
import java.util.UUID;

import org.chess.api.entity.Account;
import org.chess.api.repository.AccountRepository;
import org.chess.api.request.LoginRequest;
import org.chess.api.request.RegisterRequest;
import org.chess.api.response.LoginResponse;
import org.chess.exception.GenericException;
import org.chess.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@PostMapping("/register")
	public Account register(@RequestBody RegisterRequest request) {

		if (Objects.isNull(request) || Objects.isNull(request.getUsername()) || Objects.isNull(request.getEmail())) {
			throw new GenericException("Register request failed");
		}

		Account existingAccount = accountService.getAccount(request.getUsername());

		if (Objects.nonNull(existingAccount)) {
			throw new GenericException("Account already exists");
		}

		Account account = new Account();
		account.setEmail(request.getEmail());
		account.setUsername(request.getUsername());
		account.setFirstName(request.getFirstName());
		account.setLastName(request.getLastName());
		account.setPassword(UUID.randomUUID().toString());
		accountRepository.save(account);
		return account;
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {

		if (Objects.isNull(request) || Objects.isNull(request.getUsername()) || Objects.isNull(request.getPassword())) {
			throw new GenericException("Invalid login request");
		}

		Account account = accountService.getAccount(request.getUsername());
		if (Objects.nonNull(account) && account.getPassword().equals(request.getPassword())) {
			String token = persistToken(account);
			return new LoginResponse(account.getUsername(), account.getEmail(), token);
		} else {
			throw new GenericException("Invalid credentials");
		}
	}

	private String persistToken(Account account) {
		account.setToken(UUID.randomUUID().toString());
		accountRepository.save(account);
		return account.getToken();
	}

}
