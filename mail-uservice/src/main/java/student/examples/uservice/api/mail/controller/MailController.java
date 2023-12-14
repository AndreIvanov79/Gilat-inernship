package student.examples.uservice.api.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import student.examples.uservice.api.mail.dto.EmailRequestDto;
import student.examples.uservice.api.mail.service.EmailService;

@RestController
@RequestMapping("/")
public class MailController {
	
	@Autowired
	private EmailService emailService;

	@PostMapping("send-registration-email")
	public String sendRegistrationEmail(@RequestBody EmailRequestDto emailRequest) throws MessagingException {
		
		String email = emailRequest.getEmail();
		String token = emailRequest.getToken();
	
		emailService.sendConfirmRegistration(email, token);
		return "Registration email sent successfully to: "+email;
	}

	@PostMapping("/send-remove-email")
	public String sendRemoveEmail(@RequestBody EmailRequestDto emailRequest) throws MessagingException {
		
		String email = emailRequest.getEmail();
		String token = emailRequest.getToken();

		emailService.sendConfirmRemoving(email, token);
		return "Remove confirmation email sent to user: " + emailRequest.getEmail();
	}
}
