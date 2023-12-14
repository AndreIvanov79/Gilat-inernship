package student.examples.uservice.api.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserSignupResponse {

	private String username;

	private String email;

//	private String password;

	private String token;

}
