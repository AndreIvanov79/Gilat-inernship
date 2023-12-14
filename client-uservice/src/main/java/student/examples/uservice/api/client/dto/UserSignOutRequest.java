package student.examples.uservice.api.client.dto;

import lombok.Getter;
import lombok.Setter;
import student.examples.custom.validator.ValidToken;

@Getter
@Setter
public class UserSignOutRequest {
	
	@ValidToken(message = "Invalid token format")
    private String token;

}
