package student.examples.uservice.api.client.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
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
import student.examples.uservice.api.client.services.SignupResponseService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {

	@Mock
	private SignupResponseService signupResponseService;
	
	@Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

	@InjectMocks
	private AuthController authController;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	private UserSignupResponse validRequest;
	private UserSignupResponse invalidRequest;
	@BeforeEach
	public void setup() {
		validRequest = new UserSignupResponse();
		validRequest.setUsername("jerry111");
		validRequest.setEmail("jerry@mail.com");
		validRequest.setPassword("jerryJ#111");
		
		invalidRequest = new UserSignupResponse();
		invalidRequest.setUsername("jo");
		invalidRequest.setEmail("jojo.mail");
		invalidRequest.setPassword("  ");
	}

	@Test
	void whenInputIsInvalid_thenReturnsStatus400() throws Exception {

		when(signupResponseService.getResponse(any(UserSignupRequest.class))).thenReturn(invalidRequest);

		String body = objectMapper.writeValueAsString(invalidRequest);

		mvc.perform(post("/auth/signup").contentType("application/json;charset=UTF-8").content(body))
				.andExpect(status().isBadRequest());
	}

	@Test
	void whenInputIsInvalid_thenReturnsStatus400WithErrorObject() throws Exception {

		when(signupResponseService.getResponse(any(UserSignupRequest.class))).thenReturn(invalidRequest);
		
		String body = objectMapper.writeValueAsString(invalidRequest);

		MvcResult result = mvc.perform(
				post("https://localhost:8444/auth/signup").contentType("application/json;charset=UTF-8").content(body))
				.andExpect(status().isBadRequest()).andReturn();

		assertThat(result.getResponse().getContentAsString()).contains("must not be empty");
	}

	@Test
	void whenInputIsValid_thenReturnsStatus200() throws Exception {

		when(signupResponseService.getResponse(any(UserSignupRequest.class))).thenReturn(validRequest);

		String body = objectMapper.writeValueAsString(validRequest);

		mvc.perform(
				post("https://localhost:8444/auth/signup").contentType("application/json;charset=UTF-8").content(body))
				.andExpect(status().is(200));
	}

}
