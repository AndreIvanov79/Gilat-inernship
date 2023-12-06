package student.examples.uservice.api.client.dto;

import lombok.Getter;
import student.examples.custom.validator.ValidToken;

@Getter
public class UserSignOutRequest {
	
	@ValidToken(message = "Invalid token format")
    private String token;

}
