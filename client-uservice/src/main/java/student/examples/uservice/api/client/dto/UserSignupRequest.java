package student.examples.uservice.api.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
//@Setter
public class UserSignupRequest {
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$", message = " should be minimum 8 characters latin alphabet and digits")
	private String username;
	
	@Email(message = " is not valid.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = " must be minimum 8 characters, spechial characters and digits")
	private String password;
	
	@NotEmpty
	private String passwordConfirmation;
	
}
