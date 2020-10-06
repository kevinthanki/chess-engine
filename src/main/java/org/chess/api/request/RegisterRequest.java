package org.chess.api.request;

import lombok.Data;

@Data
public class RegisterRequest {

	private String email;
	private String username;
	private String firstName;
	private String lastName;
}
