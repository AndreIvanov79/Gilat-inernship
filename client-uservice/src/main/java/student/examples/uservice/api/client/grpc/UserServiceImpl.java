package student.examples.uservice.api.client.grpc;

import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import student.examples.grpc.UserServiceGrpc;
import student.examples.grpc.UserServiceGrpc.UserServiceImplBase;
import student.examples.grpc.UserServiceOuterClass;
import student.examples.grpc.UserServiceOuterClass.ConfirmRegistrationRequest;
import student.examples.grpc.UserServiceOuterClass.ConfirmRegistrationResponse;
import student.examples.grpc.UserServiceOuterClass.ConfirmRemovingRequest;
import student.examples.grpc.UserServiceOuterClass.ConfirmRemovingResponse;
import student.examples.grpc.UserServiceOuterClass.CreateRequest;
import student.examples.grpc.UserServiceOuterClass.CreateResponse;
import student.examples.grpc.UserServiceOuterClass.DeleteRequest;
import student.examples.grpc.UserServiceOuterClass.DeleteResponse;
import student.examples.uservice.api.client.dto.UserSignOutRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@Service
public class UserServiceImpl extends UserServiceImplBase {

	public String createUser(UserSignupRequest userSignupRequest) {
		
		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:6565").usePlaintext().build();

		UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
		

		UserServiceOuterClass.CreateRequest request = UserServiceOuterClass.CreateRequest
				.newBuilder()
				.setUsername(userSignupRequest.getUsername())
				.setEmail(userSignupRequest.getEmail())
				.setPassword(userSignupRequest.getPassword())
				.build();
		UserServiceOuterClass.CreateResponse response = stub.createUser(request);
        channel.shutdownNow();
		return response.getUser();
	}
	
	
	public String deleteUser(UserSignOutRequest signOutRequest) {
		
		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:6565").usePlaintext().build();

		UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
		

		UserServiceOuterClass.DeleteRequest request = UserServiceOuterClass.DeleteRequest
				.newBuilder()
				.setToken(signOutRequest.getToken())
				.build();
		
		UserServiceOuterClass.DeleteResponse response = stub.deleteUser(request);
        channel.shutdownNow();
		return response.getUser();
	}
	
	
	public String confirmRegistration(UserSignOutRequest signOutRequest) {
		System.out.println("ConfirmTOKEN: "+signOutRequest.getToken());
		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:6565").usePlaintext().build();

		UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
		
		UserServiceOuterClass.ConfirmRegistrationRequest request = UserServiceOuterClass.ConfirmRegistrationRequest
				.newBuilder()
				.setToken(signOutRequest.getToken())
				.build();
		
		UserServiceOuterClass.ConfirmRegistrationResponse response = stub.confirmRegistration(request);
		System.out.println("CLIENTconfirmResp:"+response.getMessage());
        channel.shutdownNow();
		return response.getMessage();
	}


	public String confirmRemoving(UserSignOutRequest signOutRequest) {
		
		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:6565").usePlaintext().build();

		UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
		
		UserServiceOuterClass.ConfirmRemovingRequest request = UserServiceOuterClass.ConfirmRemovingRequest
				.newBuilder()
				.setToken(signOutRequest.getToken())
				.build();
		
		UserServiceOuterClass.ConfirmRemovingResponse response = stub.confirmRemoving(request);
		System.out.println("CLIENTremoveResp:"+response.getMessage());
        channel.shutdownNow();
		return response.getMessage();
	}

}
