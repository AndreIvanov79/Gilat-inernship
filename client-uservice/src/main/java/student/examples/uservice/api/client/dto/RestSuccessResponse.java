package student.examples.uservice.api.client.dto;


public class RestSuccessResponse extends RestResponse {

	private Object body;

	public RestSuccessResponse(int statusCode, Object body) {
		super(statusCode, "success");
		this.body = body;
	}

	public Object getBody() {
		return body;
	}
	
}
