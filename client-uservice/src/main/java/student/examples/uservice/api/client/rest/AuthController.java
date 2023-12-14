package student.examples.uservice.api.client.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import student.examples.uservice.api.client.dto.RestResponse;
import student.examples.uservice.api.client.dto.RestSuccessResponse;
import student.examples.uservice.api.client.dto.UserSignOutRequest;
import student.examples.uservice.api.client.dto.UserSigninRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;
import student.examples.uservice.api.client.dto.UserSignupResponse;
import student.examples.uservice.api.client.services.RegisterConfirmationService;
import student.examples.uservice.api.client.services.RemoveConfirmationService;
import student.examples.uservice.api.client.services.SignupResponseService;
import student.examples.uservice.api.client.services.WithdrowResponseService;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	private SignupResponseService signupResponseService = new SignupResponseService();
	private WithdrowResponseService withdrowResponseService = new WithdrowResponseService();
	private RegisterConfirmationService registerConfirmationService = new RegisterConfirmationService();
	private RemoveConfirmationService removeConfirmationService = new RemoveConfirmationService();

	@PostMapping("/signup")
	public RestResponse signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", String.format("an email was been sent to %s, please verify and activate your account",
				userSignupRequest.getEmail()));

		UserSignupResponse user = signupResponseService.getCreateResponse(userSignupRequest);

		RestResponse response = new RestSuccessResponse(200, map);
		return response;
	}

	@PostMapping("/signin")
	public RestResponse signin(@Valid @RequestBody UserSigninRequest userSigninRequest) {

		return new RestSuccessResponse(200, "signin success");
	}

	@PostMapping("/signout")
	public RestResponse signOut(@Valid @RequestBody UserSignOutRequest signOutRequest) {

		return new RestSuccessResponse(200, "signout success");

	}

	@PostMapping("/withdrow")
	public RestResponse withdrow(@RequestBody UserSignOutRequest signOutRequest) {

		
		UserSignupResponse user = withdrowResponseService.getResponse(signOutRequest);
	//	UserSignupResponse user = responseService.getDeleteResponse(signOutRequest);
		return new RestSuccessResponse(200, "withdrow success");
	}
	
	@GetMapping("/register")
	public RestResponse registrationConfirmation(@RequestParam(name = "token") String token) {
		System.out.println("PARAM: "+token);
		UserSignOutRequest userSignOutRequest = new UserSignOutRequest();
		userSignOutRequest.setToken(token);
		System.out.println("TOKEN FOR REGISTER: "+userSignOutRequest.getToken());
		String message = registerConfirmationService.getConfirmationResponse(userSignOutRequest);
		System.out.println("MESSAGEfromCONTROLLER: "+message);
		return new RestSuccessResponse(200, message);
	}
	
	@GetMapping("/remove")
	public RestResponse removingConfirmation(@RequestParam(name = "token") String token) {
		UserSignOutRequest userSignOutRequest = new UserSignOutRequest();
		userSignOutRequest.setToken(token);
		System.out.println("TOKEN FOR REMOVE: "+userSignOutRequest.getToken());
		String message = removeConfirmationService.getRemovingResponse(userSignOutRequest);
		System.out.println("REMOVE_MESSAGEfromCONTROLLER: "+message);
		return new RestSuccessResponse(200, message);
	}

}
