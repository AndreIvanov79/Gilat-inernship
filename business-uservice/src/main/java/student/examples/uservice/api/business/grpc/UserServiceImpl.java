package student.examples.uservice.api.business.grpc;

import java.io.IOException;
import java.util.UUID;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import student.examples.grpc.UserServiceGrpc.UserServiceImplBase;
import student.examples.grpc.UserServiceOuterClass;
import student.examples.grpc.UserServiceOuterClass.UserRequest;
import student.examples.grpc.UserServiceOuterClass.UserResponse;
import student.examples.uservice.api.business.db.entities.User;
import student.examples.uservice.api.business.db.repositories.UserRepository;

@GRpcService
public class UserServiceImpl extends UserServiceImplBase {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void createUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		System.out.println("Userto DB: "+user);
		userRepository.save(user);

		System.out.println("SERVREQ: "+request);
		UserServiceOuterClass.UserResponse response = UserServiceOuterClass.UserResponse.newBuilder().build();

		System.out.println("SERVRES: "+response);
		responseObserver.onNext(response);
		responseObserver.onCompleted();

	}
}
