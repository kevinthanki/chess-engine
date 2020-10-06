package org.chess.api.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Account {

	@Id
	private ObjectId _id;

	@Indexed(unique = true)
	private String username;

	@Indexed(unique = true)
	private String email;

	private String firstName;

	private String lastName;

	private String password;

	private String token;
}
