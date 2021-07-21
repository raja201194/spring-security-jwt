package springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import springsecurityjwt.model.AuthenticationRequest;
import springsecurityjwt.model.AuthenticationResponse;
import springsecurityjwt.service.MyUserDetailsService;
import springsecurityjwt.util.JwtUtil;

@RestController
public class HelloController {
	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authencationRequest ) throws Exception{
		try {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authencationRequest.getUserName(), authencationRequest.getPassword()));
		}catch (BadCredentialsException e) {
			throw new Exception("Incorect username and password",e);
		}
		
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(authencationRequest.getUserName());
		
		String jwt = jwtUtil.generateToken(userDetails);
		return new ResponseEntity<>(new AuthenticationResponse(jwt) ,HttpStatus.OK);
	}
}
