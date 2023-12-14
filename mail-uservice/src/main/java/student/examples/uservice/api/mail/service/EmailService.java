package student.examples.uservice.api.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import student.examples.uservice.api.mail.dto.EmailRequestDto;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendConfirmRegistration(String email, String token) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Signup Confirmation");
        mimeMessageHelper.setText("<html><body><h1>Welcome to the GODZILA GAME!<br>Your account was successfully created.</h1><br/>"
				+ "<a href=\"https://localhost:8444/auth/register?token=" + token
				+ "\" target=\"_blank\">Confirm your registration</a></body></html>", true); 

        javaMailSender.send(mimeMessage);
	}
	
	public void sendConfirmRemoving(String email, String token) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		String to = email;
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("Remove Confirmation");
        mimeMessageHelper.setText("<html><body><h1>Hi!<br>Your GODZILA GAME account is going to be removed.</h1><br/>"
				+ "<a href=\"https://localhost:8444/auth/remove?token=" + token
				+ "\" target=\"_blank\">Confirm</a></body></html>", true); 

        javaMailSender.send(mimeMessage);
	}

	public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true); 

        javaMailSender.send(mimeMessage);
   	
	}
}
