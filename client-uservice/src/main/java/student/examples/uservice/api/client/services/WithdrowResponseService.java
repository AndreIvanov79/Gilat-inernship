package student.examples.uservice.api.client.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import student.examples.uservice.api.client.dto.UserSignOutRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;
import student.examples.uservice.api.client.dto.UserSignupResponse;
import student.examples.uservice.api.client.grpc.UserServiceImpl;

public class WithdrowResponseService {

	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	
	public String getResponse(UserSignOutRequest userSignOutRequest) {
		return userServiceImpl.deleteUser(userSignOutRequest);
	}
	
	private UserSignupResponse parseUserString(String userString) {
        Pattern pattern = Pattern.compile("User\\(id=(.*?), username=(.*?), email=(.*?), password=(.*?), token=(.*?)\\)");
        Matcher matcher = pattern.matcher(userString);

        if (matcher.matches()) {
            String id = matcher.group(1);
            String username = matcher.group(2);
            String email = matcher.group(3);
            String password = matcher.group(4);
            String token = matcher.group(5);
            UserSignupResponse userSignupResponse = new UserSignupResponse();

            userSignupResponse.setUsername(username);
            userSignupResponse.setEmail(email);
            userSignupResponse.setPassword(password);
            userSignupResponse.setToken(token);
            
            return userSignupResponse;
        } else {
            throw new IllegalArgumentException("Invalid user string format");
        }
    }
}
