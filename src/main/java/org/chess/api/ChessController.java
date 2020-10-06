package org.chess.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.chess.api.entity.Account;
import org.chess.api.entity.Game;
import org.chess.api.repository.GameRepository;
import org.chess.api.request.CreateGameRequest;
import org.chess.api.request.JoinGameRequest;
import org.chess.api.response.GameResponse;
import org.chess.api.util.Constants;
import org.chess.api.util.Piece;
import org.chess.exception.GenericException;
import org.chess.service.AccountService;
import org.chess.service.GameService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chess")
public class ChessController {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private GameService gameService;

	@PostMapping("/game/create")
	public GameResponse createGame(@RequestBody CreateGameRequest request) {

		validateUser();

		GameResponse response = new GameResponse();
		response.setGameUrl(UUID.randomUUID().toString());
		response.setPositions(retrieveInitialPositions());
		response.setSide(getSide(request));

		gameRepository.save(generateGame(response));

		return response;
	}

	@PostMapping("/game/join")
	public GameResponse joinGame(@RequestBody JoinGameRequest request) {

		String currentUser = MDC.get(Constants.CONST_CHESS_USER);

		if (!StringUtils.isEmpty(currentUser)) {
			Game game = gameService.retrieveGameByURL(request.getGameUrl());
			if (Objects.isNull(game)) {
				throw new GenericException("Game not found");
			}

			Map<String, String> side = game.getSide();
			if (side.size() == 1) {

				if (side.containsKey(Constants.SIDE_BLACK)) {

					if (!side.get(Constants.SIDE_BLACK).equals(currentUser))

						side.put(Constants.SIDE_WHITE, currentUser);
				} else {
					if (!side.get(Constants.SIDE_WHITE).equals(currentUser))
						side.put(Constants.SIDE_BLACK, currentUser);
				}

				game.setSide(side);
				game.setRunning(Boolean.TRUE);
				gameRepository.save(game);
				return new GameResponse(game.getGameUrl(), game.getPositions(), side);
			} else {
				throw new GenericException("Unable to add you in the game");
			}

		} else {
			throw new GenericException("User not found");
		}

	}

	@GetMapping("/game/{gameUrl}")
	public GameResponse retrieveGame(@PathVariable String gameUrl) {
		Game game = gameService.retrieveGameByURL(gameUrl);
		if (Objects.isNull(game)) {
			throw new GenericException("Game not found");
		}
		return new GameResponse(gameUrl, game.getPositions(), game.getSide());
	}

	private void validateUser() {

		String username = MDC.get(Constants.CONST_CHESS_USER);

		if (StringUtils.isEmpty(username)) {
			throw new GenericException("Username is required!");
		}

		Account username1 = accountService.getAccount(username);
		if (Objects.isNull(username1)) {
			throw new GenericException(String.format("User[%s] not registered", username));
		}
	}

	private Game generateGame(GameResponse response) {
		Game game = new Game();
		game.setGameUrl(response.getGameUrl());
		game.setPositions(response.getPositions());
		game.setSide(response.getSide());
		game.setRunning(false);
		game.setWinner(null);
		return game;
	}

	private Map<String, String> getSide(CreateGameRequest request) {
		Map<String, String> side = new HashMap<>();

		String username = MDC.get(Constants.CONST_CHESS_USER);

		if (System.currentTimeMillis() % 2 == 0) {
			side.put(Constants.SIDE_WHITE, username);

			if (!StringUtils.isEmpty(request.getOpponent()))
				side.put(Constants.SIDE_BLACK, request.getOpponent());

		} else {
			side.put(Constants.SIDE_BLACK, username);

			if (!StringUtils.isEmpty(request.getOpponent()))
				side.put(Constants.SIDE_WHITE, request.getOpponent());
		}

		return side;
	}

	private Map<String, String> retrieveInitialPositions() {
		Map<String, String> positions = new HashMap<>();
		positions.put("A1", Piece.BLACK_ROOK);
		positions.put("A2", Piece.BLACK_KNIGHT);
		positions.put("A3", Piece.BLACK_BISHOP);
		positions.put("A4", Piece.BLACK_QUEEN);
		positions.put("A5", Piece.BLACK_KING);
		positions.put("A6", Piece.BLACK_BISHOP);
		positions.put("A7", Piece.BLACK_KNIGHT);
		positions.put("A8", Piece.BLACK_ROOK);

		positions.put("B1", Piece.BLACK_PAWN);
		positions.put("B2", Piece.BLACK_PAWN);
		positions.put("B3", Piece.BLACK_PAWN);
		positions.put("B4", Piece.BLACK_PAWN);
		positions.put("B5", Piece.BLACK_PAWN);
		positions.put("B6", Piece.BLACK_PAWN);
		positions.put("B7", Piece.BLACK_PAWN);
		positions.put("B8", Piece.BLACK_PAWN);

		positions.put("G1", Piece.WHITE_PAWN);
		positions.put("G2", Piece.WHITE_PAWN);
		positions.put("G3", Piece.WHITE_PAWN);
		positions.put("G4", Piece.WHITE_PAWN);
		positions.put("G5", Piece.WHITE_PAWN);
		positions.put("G6", Piece.WHITE_PAWN);
		positions.put("G7", Piece.WHITE_PAWN);
		positions.put("G8", Piece.WHITE_PAWN);

		positions.put("H1", Piece.WHITE_ROOK);
		positions.put("H2", Piece.WHITE_KNIGHT);
		positions.put("H3", Piece.WHITE_BISHOP);
		positions.put("H4", Piece.WHITE_QUEEN);
		positions.put("H5", Piece.WHITE_KING);
		positions.put("H6", Piece.WHITE_BISHOP);
		positions.put("H7", Piece.WHITE_KNIGHT);
		positions.put("H8", Piece.WHITE_ROOK);

		return positions;
	}

}
