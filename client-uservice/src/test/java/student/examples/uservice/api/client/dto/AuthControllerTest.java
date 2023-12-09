package student.examples.uservice.api.client.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.grpc.ServerServiceDefinition;
import io.grpc.testing.GrpcServerRule;
import student.examples.uservice.api.client.grpc.UserServiceImpl;
import student.examples.uservice.api.client.rest.AuthController;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

	@Mock
	private UserServiceImpl userServiceImpl;
	
	@Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

	@InjectMocks
	private AuthController authController;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

//	@Test
	void whenInputIsInvalid_thenReturnsStatus400() throws Exception {

		UserSignupRequest request = new UserSignupRequest("jerry111", "jerry@mail.com", "jerryJ#111", "jerryJ#111");

//		when(userServiceImpl.createUser(any(UserSignupRequest.class))).thenReturn(request);
		grpcServerRule.getServiceRegistry().addService(userServiceImpl);

		String body = objectMapper.writeValueAsString(request);

		mvc.perform(post("/auth/signup").contentType("application/json;charset=UTF-8").content(body))
				.andExpect(status().isBadRequest());
	}

//	@Test
	void whenInputIsInvalid_thenReturnsStatus400WithErrorObject() throws Exception {
		UserSignupRequest request = new UserSignupRequest("jerry111", "jerry@mail.com", "jerryJ#111", "jerryJ#111");
//		when(userServiceImpl.createUser(any(UserSignupRequest.class))).thenReturn(request);
		
		ServerServiceDefinition serviceDefinition = userServiceImpl.bindService();
		grpcServerRule.getServiceRegistry().addService(serviceDefinition);
		String body = objectMapper.writeValueAsString(request);

		MvcResult result = mvc.perform(
				post("https://localhost:8444/auth/signup").contentType("application/json;charset=UTF-8").content(body))
				.andExpect(status().isBadRequest()).andReturn();

		assertThat(result.getResponse().getContentAsString()).contains("violations");
	}

//	@Test
	void whenInputIsValid_thenReturnsStatus200() throws Exception {
		UserSignupRequest request = new UserSignupRequest("jerry111", "jerry@mail.com", "jerryJ#111", "jerryJ#111");
//		when(userServiceImpl.createUser(any(UserSignupRequest.class))).thenReturn(request);
		
		grpcServerRule.getServiceRegistry().addService(userServiceImpl);
		String body = objectMapper.writeValueAsString(request);

		mvc.perform(
				post("https://localhost:8444/auth/signup").contentType("application/json;charset=UTF-8").content(body))
				.andExpect(status().isOk());
	}

}
