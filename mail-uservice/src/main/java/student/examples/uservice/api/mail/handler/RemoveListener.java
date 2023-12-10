package student.examples.uservice.api.mail.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import student.examples.uservice.api.mail.service.EmailService;

@Service
@KafkaListener(topics = "user-remove-topic", groupId = "mail-uservice")
public class RemoveListener {

	@Autowired
	private EmailService emailService;

	@KafkaHandler
	public void handleUserUnsubscribe(String message) {
		String emailAddress = "dyaka1979@gmail.com";
		String emailSubject = "Remove Confirmation";
		String emailBody = "Hi!\nYour GODZILA GAME account was successfully removed.";

		emailService.sendEmail(emailAddress, emailSubject, emailBody);
	}
}
