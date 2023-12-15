package website.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.main.mail.MailSender;
import website.main.util.DBHandler;
import website.main.util.EncryptionManager;
import website.main.util.FieldValidator;

public class SignupActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FieldValidator fieldValidator;

	public SignupActionServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		fieldValidator = new FieldValidator();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> validatedData = new HashMap<>();

		String token = request.getParameter("-gg-token");

		String fullName = request.getParameter("fullName");
		validatedData.put("fullName", fieldValidator.validate("fullName", fullName));

		String email = request.getParameter("email");
		validatedData.put("email", fieldValidator.validate("email", email));

		String phone = request.getParameter("phone");
		validatedData.put("phone", fieldValidator.validate("phone", phone));

		String password = EncryptionManager.hashPassword(request.getParameter("password"));
		validatedData.put("password", fieldValidator.validate("password", request.getParameter("password")));
		
		String confirmPassword = EncryptionManager.hashPassword(request.getParameter("confirmPassword"));
//		validatedData.put("confirmPassword", fieldValidator.confirmPasswordMatching(request.getParameter("password"), confirmPassword));

		String nickname = request.getParameter("nickname");
		validatedData.put("nickname", fieldValidator.validate("nickname", nickname));

		boolean dataIsValid = validatedData.values().stream().anyMatch(data -> data != null);
		boolean updated = false;
		String clientExist = DBHandler.getTokenByEmail(email);
		
		if (DBHandler.checkLastInsertTime()) {
			if (clientExist == null) {
				updated = DBHandler.updateClient(token, fullName, email, phone, password, nickname);
				System.out.println("Updated client " + fullName +": "+updated);
			} else System.out.println("Client already exist in DB.");
		} else System.out.println("Frequency updates, try again.");

		String htmlContent = "<html><body><h1>Hello, this is GODZILA GAME email!</h1><br/>"
				+ "<a href=\"http://localhost:8080/MainWebsite/validate-download?email=" + email
				+ "\" target=\"_blank\">Downlod game</a></body></html>";

		MailSender mailSender = new MailSender();
		String result = "";
		try {
			Message message = mailSender.prepareMessage("dyaka1979@gmail.com", "lsmz czcb pgfp tviu", htmlContent);
			result = mailSender.sendMessage(message);
			System.out.println("MAIL RESULT: "+result);
			validatedData.put("mail-send", result);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(validatedData);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(json);
//		out.write("{\"mail-send\":\""+result+"\"}");

	}

}
