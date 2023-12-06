package student.examples.uservice.api.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserSigninRequest {
	private String usernameOrEmail;
	private String password;
}
