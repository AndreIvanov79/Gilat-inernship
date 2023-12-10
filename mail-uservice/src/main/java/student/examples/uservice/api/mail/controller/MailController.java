package student.examples.uservice.api.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import student.examples.uservice.api.mail.dto.EmailRequestDto;
import student.examples.uservice.api.mail.service.EmailService;

@RestController
@RequestMapping("/")
public class MailController {
	
	@Autowired
	private EmailService emailService;

	@PostMapping("send-registration-email")
	public String sendRegistrationEmail(@RequestBody EmailRequestDto emailRequest) {
		
		String userEmail = emailRequest.getEmail();
		String emailAddress = "dyaka1979@gmail.com";
		String emailSubject = "Signup Confirmation";
		String emailBody = "Welcome to the GODZILA GAME!\nYour account was successfully created.";

		emailService.sendEmail(emailAddress, emailSubject, emailBody);
		
		return "Registration email sent successfully to: "+userEmail;
	}

	@PostMapping("send-remove-email")
	public String sendRemoveEmail(@RequestBody EmailRequestDto emailRequest) {
		
		String token = emailRequest.getToken();
		String emailAddress = "dyaka1979@gmail.com";
		String emailSubject = "Remove Confirmation";
		String emailBody = "Hi!\nYour GODZILA GAME account was successfully removed.";

		emailService.sendEmail(emailAddress, emailSubject, emailBody);
		return "Remove email sent to user: " + token;
	}
}
