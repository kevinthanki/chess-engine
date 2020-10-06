package org.chess.api.entity;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Game {

	@Id
	private ObjectId _id;

	@Indexed(unique = true)
	private String gameUrl;

	private Map<String, String> positions;

	private Map<String, String> side;

	private boolean isRunning;

	private String winner;

}
