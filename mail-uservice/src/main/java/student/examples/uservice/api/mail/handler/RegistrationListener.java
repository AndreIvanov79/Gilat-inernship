package student.examples.uservice.api.mail.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import student.examples.uservice.api.mail.service.EmailService;

@Service
//@KafkaListener(topics = "user-registration-topic", groupId = "mail-uservice")
public class RegistrationListener {

	@Autowired
	private EmailService emailService;

//	@KafkaHandler
	public void handleUserRegistration(String message) throws MessagingException {
		String emailAddress = "dyaka1979@gmail.com";
		String emailSubject = "Signup Confirmation";
		String emailBody = "Welcome to the GODZILA GAME!\nYour account was successfully created.";

		emailService.sendEmail(emailAddress, emailSubject, emailBody);
	}
}
