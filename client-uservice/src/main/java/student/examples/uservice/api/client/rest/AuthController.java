package student.examples.uservice.api.client.rest;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import student.examples.uservice.api.client.dto.RestResponse;
import student.examples.uservice.api.client.dto.RestSuccessResponse;
import student.examples.uservice.api.client.dto.UserSignOutRequest;
import student.examples.uservice.api.client.dto.UserSigninRequest;
import student.examples.uservice.api.client.dto.UserSignupRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@PostMapping("/signup")
	public RestResponse signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("message", String.format("an email was been sent to %s, please verify and activate your account",
				userSignupRequest.getEmail()));
		
		RestResponse response = new RestSuccessResponse(200, map);
		return response;
	}

	@PostMapping("/signin")
	public RestResponse signin(@Valid @RequestBody UserSigninRequest userSigninRequest) {

		return new RestSuccessResponse(200, "signin success");
	}
	
	@PostMapping("/signout")
    public RestResponse signOut(@Valid @RequestBody UserSignOutRequest signOutRequest) {
   
        return new RestSuccessResponse(200, "signin success");
   
    }

}
