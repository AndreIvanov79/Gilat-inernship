package student.examples.uservice.api.client.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lognet.springboot.grpc.autoconfigure.OnGrpcServerEnabled;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.annotations.GrpcGenerated;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.util.MutableHandlerRegistry;
import student.examples.uservice.api.client.ClientUserviceApplication;
import student.examples.uservice.api.client.grpc.UserServiceImpl;
import student.examples.uservice.api.client.rest.AuthController;
import student.examples.uservice.api.client.services.SignupResponseService;

@SpringBootTest(properties = { "grpc.server.inProcessName=test", "grpc.server.port=9091",
		"grpc.client.petService.address=in-process:test" })
//@SpringJUnitConfig(classes = { UserServiceImpl.class })
@OnGrpcServerEnabled
//@AutoConfigureMockMvc
//@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {
	@MockBean
	UserServiceImpl userService; //= Mockito.mock(UserServiceImpl.class);

	@MockBean
	//private UserServiceGrpc.UserServiceBlockingStub blockingStub;

	@Autowired
	private AuthController authController;

	@Mock
	private SignupResponseService signupResponseService;

	@Rule
	public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

//	@Autowired
//	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	private String body;
	private UserSignupResponse validResponse;
	private UserSignupResponse invalidRequest;
	private UserSignupRequest request;

	private Server server;

	@BeforeEach
	public void setUp() throws Exception {

//    	 grpcCleanup.register(
//    		     InProcessServerBuilder.forName("my-test-case")
//    		         .directExecutor()
//    		         .addService(new UserServiceImpl())
//    		         .build()
//    		         .start());
//    		 ManagedChannel channel = grpcCleanup.register(
//    		     InProcessChannelBuilder.forName("my-test-case")
//    		         .directExecutor()
//    		         .build());
//    	
		String serverName = InProcessServerBuilder.generateName();
		MutableHandlerRegistry serviceRegistry = new MutableHandlerRegistry();
		Server server = grpcCleanup.register(
				InProcessServerBuilder.forName(serverName).fallbackHandlerRegistry(serviceRegistry).build().start());
		ManagedChannel channel = grpcCleanup.register(InProcessChannelBuilder.forName(serverName).build());

//        int port = 6565;
//
//        server = ServerBuilder.forPort(port).addService(new UserServiceImpl()).build().start();
//        grpcCleanup.register(server);
//        
//        blockingStub = UserServiceGrpc.newBlockingStub(
//                grpcCleanup.register(InProcessChannelBuilder.forName("localhost:" + 6565).directExecutor().build()));

		validResponse = new UserSignupResponse();
		validResponse.setUsername("jerry111");
	//	validResponse.setEmail("jerry@mail.com");
//		validResponse.setPassword("jerryJ#111");

		request = new UserSignupRequest("jerry111", "jerry@mail.com", "jerryJ#111", "jerryJ#111");

		body = "{\"username\":\"jerry111\",\"email\":\"jerry@mail.com\",\"password\":\"jerryJ#111\",\"passwordConfirmation\":\"jerryJ*111\"}";

		invalidRequest = new UserSignupResponse();
		invalidRequest.setUsername("jo");
//		invalidRequest.setEmail("jojo.mail");
//		invalidRequest.setPassword("  ");
	}

	@AfterEach
	public void tearDown() throws Exception {
		if (server != null) {
			server.shutdownNow();
		}
	}

	@Test
	void whenInputIsInvalid_thenReturnsStatus400() throws Exception {
//		UserServiceOuterClass.CreateRequest grpcRequest = UserServiceOuterClass.CreateRequest.newBuilder()
//				.setUsername(request.getUsername()).setEmail(request.getEmail()).setPassword(request.getPassword())
//				.build();

//		UserServiceOuterClass.CreateResponse response = blockingStub.createUser(grpcRequest);

//		when(blockingStub.createUser(grpcRequest)).thenReturn(any(CreateResponse.class));
		when(userService.createUser(request)).thenReturn(body);

		when(signupResponseService.getCreateResponse(any(UserSignupRequest.class))).thenReturn(invalidRequest);

		String body = objectMapper.writeValueAsString(invalidRequest);

		RestResponse res = authController.signup(request);
//		mvc.perform(post("/auth/signup").contentType("application/json;charset=UTF-8").content(body))
//				.andExpect(status().isBadRequest());
	}

	@Test
	void whenInputIsInvalid_thenReturnsStatus400WithErrorObject() throws Exception {
//		UserServiceOuterClass.CreateRequest grpcRequest = UserServiceOuterClass.CreateRequest.newBuilder()
//				.setUsername(request.getUsername()).setEmail(request.getEmail()).setPassword(request.getPassword())
//				.build();

//		UserServiceOuterClass.CreateResponse response = blockingStub.createUser(grpcRequest);
	//	when(blockingStub.createUser(grpcRequest)).thenReturn(any(CreateResponse.class));
		when(signupResponseService.getCreateResponse(any(UserSignupRequest.class))).thenReturn(invalidRequest);

		String body = objectMapper.writeValueAsString(invalidRequest);

		RestResponse res = authController.signup(request);
//		MvcResult result = mvc.perform(
//				post("https://localhost:8444/auth/signup").contentType("application/json;charset=UTF-8").content(body))
//				.andExpect(status().isBadRequest()).andReturn();

//		assertThat(result.getResponse().getContentAsString()).contains("must not be empty");
	}

	@Test
	void whenInputIsValid_thenReturnsStatus200() throws Exception {

//		UserServiceOuterClass.CreateRequest grpcRequest = UserServiceOuterClass.CreateRequest.newBuilder()
//				.setUsername(request.getUsername()).setEmail(request.getEmail()).setPassword(request.getPassword())
//				.build();

//		UserServiceOuterClass.CreateResponse response = blockingStub.createUser(grpcRequest);
	//	when(blockingStub.createUser(grpcRequest)).thenReturn(any(CreateResponse.class));
		when(signupResponseService.getCreateResponse(request)).thenReturn(validResponse);

		String body = objectMapper.writeValueAsString(request);
		RestResponse res = authController.signup(request);

//		mvc.perform(
//
//				post("https://localhost:8444/auth/signup").contentType("application/json;charset=UTF-8").content(body))
//				.andExpect(status().isOk());

		System.out.println("RES: " + res);
	}

}
