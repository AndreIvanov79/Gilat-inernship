package student.examples.uservice.api.business.grpc;

import java.io.IOException;
import java.util.UUID;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.stereotype.Service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import student.examples.grpc.UserServiceGrpc.UserServiceImplBase;
import student.examples.grpc.UserServiceOuterClass;
import student.examples.grpc.UserServiceOuterClass.UserRequest;
import student.examples.grpc.UserServiceOuterClass.UserResponse;

@GRpcService
public class UserServiceImpl extends UserServiceImplBase {

	@Override
	public void createUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
//		Server server = ServerBuilder.forPort(8442).addService(new UserServiceImpl()).build();
//		try {
//			server.start();
//			System.out.println("Server started...");
//			server.awaitTermination();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println(request);
		UserServiceOuterClass.UserResponse response = UserServiceOuterClass.UserResponse.newBuilder()
//				.se
//				.setUsername(request.getUsername())
//				.setEmail(request.getEmail())
//				.setPassword(request.getPassword())
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();

	}
}
