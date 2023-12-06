package student.examples.uservice.api.client.dto;

public class RestResponse {

	private int statusCode;
	private String statusMessage;
	
	public RestResponse(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public RestResponse(int statusCode) {
		super();
		this.statusCode = statusCode;
	}
	
	public RestResponse(String statusMessage) {
		super();
		this.statusMessage = statusMessage;
	}
}
