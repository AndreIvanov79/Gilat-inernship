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

    public void sendRegistrationEmail(User user) {
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String userEmail = user.getEmail();
        String requestBody = "{\"email\": \"" + userEmail + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(mailUserviceBaseUrl + "/send-registration-email", requestEntity, String.class);

    }

    public void sendRemoveEmail(String token) {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"token\": \"" + token + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(mailUserviceBaseUrl + "/send-remove-email", requestEntity, String.class);
    }
}
