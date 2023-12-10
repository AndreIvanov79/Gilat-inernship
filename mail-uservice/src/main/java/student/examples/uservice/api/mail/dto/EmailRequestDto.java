package student.examples.uservice.api.mail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {

	private String email;
	private String token;
}
