package org.chess.api.repository;

import java.util.List;

import org.chess.api.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

	List<Game> findBygameUrl(String gameUrl);

}
