package student.examples.uservice.api.client.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSignupRequestTest {
	

	@Test
	public void usernameShouldCorrespondsToRegexp() {
		String usernameRegexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$";
		String username = "jonny777";
		String wrongUsername ="jo";
		assertTrue(username.matches(usernameRegexp));
		assertTrue(!(wrongUsername.matches(usernameRegexp)));
	}
	
	@Test
	public void passwordShouldCorrespondsToRegexp() {
		String passwordRegexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
		String password = "jonnyJ*777";
		String wrongPassword = "john";
		assertTrue(password.matches(passwordRegexp));
		assertTrue(!(wrongPassword.matches(passwordRegexp)));
	}
}
