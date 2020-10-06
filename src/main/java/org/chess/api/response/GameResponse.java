package org.chess.api.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
	private String gameUrl;
	private Map<String, String> positions;
	private Map<String, String> side;
}
