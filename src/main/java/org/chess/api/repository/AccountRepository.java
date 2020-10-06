package org.chess.api.repository;

import java.util.List;

import org.chess.api.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

	List<Account> findByToken(String token);

	List<Account> findByUsername(String username);

}