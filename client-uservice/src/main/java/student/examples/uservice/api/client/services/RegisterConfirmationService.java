package student.examples.uservice.api.client.services;

import student.examples.uservice.api.client.dto.UserSignOutRequest;
import student.examples.uservice.api.client.grpc.UserServiceImpl;

public class RegisterConfirmationService {

	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	
	public String getConfirmationResponse(UserSignOutRequest userSignOutRequest) {
		return userServiceImpl.confirmRegistration(userSignOutRequest);
		
	}
}
