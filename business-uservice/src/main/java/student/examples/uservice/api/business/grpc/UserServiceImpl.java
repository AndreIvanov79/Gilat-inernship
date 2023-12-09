package student.examples.uservice.api.business.grpc;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.stub.StreamObserver;
import student.examples.grpc.UserServiceGrpc.UserServiceImplBase;
import student.examples.grpc.UserServiceOuterClass;
import student.examples.grpc.UserServiceOuterClass.CreateRequest;
import student.examples.grpc.UserServiceOuterClass.CreateResponse;
import student.examples.grpc.UserServiceOuterClass.DeleteRequest;
import student.examples.grpc.UserServiceOuterClass.DeleteResponse;
import student.examples.uservice.api.business.db.entities.User;
import student.examples.uservice.api.business.db.repositories.UserRepository;
import student.examples.uservice.api.business.mail.EmailService;
import student.examples.uservice.api.business.util.UserToken;

@GRpcService
public class UserServiceImpl extends UserServiceImplBase {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;

	@Override
	public void createUser(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {

		UserToken userToken = new UserToken();
		UUID userId = UUID.randomUUID();
		
		String emailAddress = "dyaka1979@gmail.com";
		String emailSubject = "Signup Confirmation";
		String emailBody = "Welcome to the GODZILA GAME!\nYour account was successfully created.";

		User user = new User();
		user.setId(userId);
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		try {
			user.setToken(userToken.createToken(userId, request.getEmail()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		userRepository.save(user);

		emailService.sendEmail(emailAddress, emailSubject, emailBody);

		System.out.println("SERVREQ: " + request);
		UserServiceOuterClass.CreateResponse response = UserServiceOuterClass.CreateResponse.newBuilder()
				.setUser(user.toString()).build();

		System.out.println("SERVRES: " + response.getUser());
		responseObserver.onNext(response);
		responseObserver.onCompleted();

	}
	
	@Override
	public void deleteUser(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
		
		String emailAddress = "dyaka1979@gmail.com";
		String emailSubject = "Remove Confirmation";
		String emailBody = "Hi!\nYour GODZILA GAME account was successfully removed.";
		
		userRepository.deleteByToken(request.getToken());
		
		emailService.sendEmail(emailAddress, emailSubject, emailBody);
		
		UserServiceOuterClass.DeleteResponse response = UserServiceOuterClass.DeleteResponse.newBuilder()
				.setUser(request.getToken()).build();

		System.out.println("SERVRES: " + response.getUser());
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
