package org.chess.service;

import java.util.List;

import org.chess.api.entity.Game;
import org.chess.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public Game retrieveGameByURL(String gameUrl) {
		List<Game> games = gameRepository.findBygameUrl(gameUrl);

		if (!CollectionUtils.isEmpty(games)) {
			return games.get(0);
		} else {
			return null;
		}
	}

}
