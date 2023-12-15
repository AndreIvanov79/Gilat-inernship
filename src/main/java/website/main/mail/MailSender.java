package website.main.mail;

import java.io.IOException;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class MailSender {

	private Session getSession(String username, String password) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.tls.trust", "smtp.gmail.com");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;
	}

	public Message prepareMessage(String username, String password, String text) throws MessagingException, IOException {

		Session session = getSession(username, password);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("dyaka1979@gmail.com"));

		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dyaka1979@gmail.com"));
		message.setSubject("Mail Subject");

		String msg = text; 

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

		MimeMultipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);
		
//		MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//		attachmentBodyPart.attachFile(new File("path/to/file"));
//		multipart.addBodyPart(attachmentBodyPart);


		return message;

	}

	public String sendMessage(Message message) {
		String result = "";
		try {
			Transport.send(message);
			result = "Message sent successfully.";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Message not sent.";
		}
		System.out.println(result);
		return result;
	}

}
