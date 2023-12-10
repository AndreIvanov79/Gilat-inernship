package student.examples.uservice.api.client.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.servlet.ServletContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import student.examples.custom.validator.TokenValidator;
import student.examples.uservice.api.client.grpc.UserServiceImpl;
import student.examples.uservice.api.client.rest.AuthController;

@SpringBootTest
public class UserSignupRequestTest {

	@Autowired
	private Validator validator;

	private MockMvc mockMvc;

	private File file = new File("./report.csv");
	
	
	@BeforeAll
	public static void setUp() throws IOException {
		cloneRepository();
	}

	@AfterAll
	public static void tearDown() {
		pushToRepository();
	}

	// @Test
	public void test() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("https://localhost:8444/auth/signup"))
				.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Hello World!!!")).andReturn();

		assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/requests.csv")
	public void testUserSignnupRequest_whenValuesAreValid(String username, String email, String password,
			String passwordConnfirmation) throws Exception {

		UserSignupRequest user = new UserSignupRequest();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setPasswordConfirmation(passwordConnfirmation);

		Set<ConstraintViolation<UserSignupRequest>> violations = validator.validate(user);

		Assertions.assertThat(violations.isEmpty());
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/bad_requests.csv")
	public void testUserSignnupRequest_whenValuesAreNotValid(String username, String email, String password,
			String passwordConnfirmation) throws Exception {

		Map<String, Object> results = new HashMap<>();

		UserSignupRequest user = new UserSignupRequest();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setPasswordConfirmation(passwordConnfirmation);

		Set<ConstraintViolation<UserSignupRequest>> violations = validator.validate(user);
		if (!violations.isEmpty()) {
			System.out.println("VIOL: " + violations.stream().findFirst().get().getMessage());
			violations.stream().forEach(val -> createCsvFile(username, val.toString(), file));
		}


		Assertions.assertThat(!violations.isEmpty());
	}

	public static void createCsvFile(String csvHeader, String csvMessage, File csvFilePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
			// Writing header

			writer.write(csvHeader);
			writer.newLine();

			// Writing data
			writer.write(csvMessage);
			writer.newLine();

			System.out.println("CSV file created successfully at: " + csvFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void cloneRepository() throws IOException {
		try {
			File destinationDirectory = new File("C:\\Users\\USER\\test-validation");
			System.out.println("Destination Directory: " + destinationDirectory.getAbsolutePath());

			if (destinationDirectory.exists() && destinationDirectory.list().length == 0) {
				Git.cloneRepository().setURI("https://github.com/AndreIvanov79/Test-validation.git")
						.setDirectory(destinationDirectory).setBranch("main")
						.call();
			} else {
				System.out.println("Destination directory is not empty. Skipping cloning.");
			}
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	private static void pushToRepository() {
		try (Git git = Git.open(new File("C:\\Users\\USER\\test-validation\\.git"))) {
			git.add().addFilepattern(".").call();
			git.add().addFilepattern(".").call();
			git.commit().setMessage("test results").call();
			git.push().setCredentialsProvider(
					new UsernamePasswordCredentialsProvider("AndreIvanov79", "26ja1979"))
					.call();
		} catch (IOException | GitAPIException e) {
			e.printStackTrace();
		}
	}
}