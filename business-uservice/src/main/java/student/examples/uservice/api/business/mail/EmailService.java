package student.examples.uservice.api.business.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import student.examples.uservice.api.business.db.entities.User;

@Service
public class EmailService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${mail-uservice.base-url}")
	private String mailUserviceBaseUrl;

	
	public void sendEmail(User user, String url) {
		System.out.println("USERfromEMailServiceBusiness: "+user.getEmail());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestBody = "{\"email\": \"" + user.getEmail() + "\", \"token\": \"" + user.getToken() + "\"}";

		System.out.println("REQUESTBODDDY: "+requestBody);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
		restTemplate.postForEntity(mailUserviceBaseUrl + url, requestEntity, String.class);
		System.out.println("URRRLLL: "+mailUserviceBaseUrl + url);
	}
}
