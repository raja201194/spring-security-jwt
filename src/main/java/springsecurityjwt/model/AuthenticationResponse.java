package springsecurityjwt.model;

public class AuthenticationResponse {

	private String jwtToken;

	public AuthenticationResponse() {

	}

	public AuthenticationResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

}
