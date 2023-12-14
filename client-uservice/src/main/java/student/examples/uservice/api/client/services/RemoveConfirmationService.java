package student.examples.uservice.api.client.services;

import student.examples.uservice.api.client.dto.UserSignOutRequest;
import student.examples.uservice.api.client.grpc.UserServiceImpl;

public class RemoveConfirmationService {

	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	
	public String getRemovingResponse(UserSignOutRequest userSignOutRequest) {
		return userServiceImpl.confirmRemoving(userSignOutRequest);
		
	}
}
