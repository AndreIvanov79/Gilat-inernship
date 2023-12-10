package student.examples.uservice.api.business.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import student.examples.uservice.api.business.db.entities.User;

@Service
public class MailConfirmationService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void registerUser(User user) {
		kafkaTemplate.send("user-registration-topic", "User registered: " + user.getEmail());
	}

	public void removeUser(String token) {
		kafkaTemplate.send("user-remove-topic", "User removed: " + token);
	}
}
