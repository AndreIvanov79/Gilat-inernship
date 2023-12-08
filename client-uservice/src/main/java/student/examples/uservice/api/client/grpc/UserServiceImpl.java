package student.examples.uservice.api.client.grpc;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.validation.Valid;
import student.examples.grpc.UserServiceGrpc;
import student.examples.grpc.UserServiceGrpc.UserServiceImplBase;
import student.examples.grpc.UserServiceOuterClass;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@Service
public class UserServiceImpl extends UserServiceImplBase {
	public void createUser(UserSignupRequest userSignupRequest) {

		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:6565").usePlaintext().build();

		UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
		

		UserServiceOuterClass.UserRequest request = UserServiceOuterClass.UserRequest
				.newBuilder()
	//			.setId(UUID.randomUUID().toString())
				.setUsername(userSignupRequest.getUsername())
				.setEmail(userSignupRequest.getEmail())
				.setPassword(userSignupRequest.getPassword())
				.build();
		UserServiceOuterClass.UserResponse response = stub.createUser(request);
		System.out.println("CLIENTRESP:"+response);
        channel.shutdownNow();
	}

}
