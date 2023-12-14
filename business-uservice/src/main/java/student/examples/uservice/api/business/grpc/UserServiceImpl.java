package student.examples.uservice.api.business.grpc;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import io.grpc.stub.StreamObserver;
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
import student.examples.uservice.api.business.db.entities.User;
import student.examples.uservice.api.business.db.repositories.UserRepository;
import student.examples.uservice.api.business.mail.EmailService;
import student.examples.uservice.api.business.mail.MailConfirmationService;
import student.examples.uservice.api.business.util.UserToken;

@GRpcService
public class UserServiceImpl extends UserServiceImplBase {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

//	@Autowired
//	private MailConfirmationService mailConfirmationService;

	@Override
	public void createUser(CreateRequest request, StreamObserver<CreateResponse> responseObserver) {

		UserToken userToken = new UserToken();
		UUID userId = UUID.randomUUID();

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

		UserServiceOuterClass.CreateResponse response = UserServiceOuterClass.CreateResponse.newBuilder()
				.setUser(user.toString()).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();

		emailService.sendEmail(user, "/send-registration-email");

		// mailConfirmationService.registerUser(user);

	}

	@Override
	public void deleteUser(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {

		User userToDelete = userRepository.findUserByToken(request.getToken());
		System.out.println("USER TO DELETE: "+userToDelete);
		// userRepository.delete(userToDelete);

		UserServiceOuterClass.DeleteResponse response = UserServiceOuterClass.DeleteResponse.newBuilder()
				.setUser(userToDelete.toString()).build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();

		emailService.sendEmail(userToDelete, "/send-remove-email");

//		mailConfirmationService.removeUser(request.getToken());
	}

	@Override
	public void confirmRegistration(ConfirmRegistrationRequest request,
			StreamObserver<ConfirmRegistrationResponse> responseObserver) {
		System.out.println("BUSINESS confirm: " + request.getToken());

		User userToActivate = userRepository.findUserByToken(request.getToken());
		userToActivate.setActive(true);
		User activatedUser = userRepository.save(userToActivate); // activateUser(request.getToken());
		System.out.println("USER ACTIVATED: " + activatedUser);

		UserServiceOuterClass.ConfirmRegistrationResponse response = UserServiceOuterClass.ConfirmRegistrationResponse
				.newBuilder().setMessage("User " + activatedUser.getEmail() + " was activated.").build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void confirmRemoving(ConfirmRemovingRequest request,
			StreamObserver<ConfirmRemovingResponse> responseObserver) {
		User userToDelete = userRepository.findUserByToken(request.getToken());
		userRepository.delete(userToDelete);

		UserServiceOuterClass.ConfirmRemovingResponse response = UserServiceOuterClass.ConfirmRemovingResponse
				.newBuilder().setMessage("User " + userToDelete.getEmail() + " was deleted.").build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
