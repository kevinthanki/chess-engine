package org.chess.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chess.api.entity.Account;
import org.chess.api.repository.AccountRepository;
import org.chess.api.util.Constants;
import org.chess.exception.GenericException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authKey = request.getHeader(Constants.HEADER_AUTHORIZATION);

		if (StringUtils.isEmpty(authKey)) {
			throw new GenericException("Invalid Authorization key");
		}
		
		List<Account> accounts = accountRepository.findByToken(authKey);

		if (CollectionUtils.isEmpty(accounts)) {
			throw new GenericException("Invalid Authorization key");
		}
		
		MDC.put(Constants.CONST_CHESS_USER, accounts.get(0).getUsername());

		return true;
	}

}
