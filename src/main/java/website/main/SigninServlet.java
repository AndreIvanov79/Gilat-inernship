package website.main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.main.util.DBHandler;
import website.main.util.EncryptionManager;
import website.main.util.FieldValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SigninServlet
 */
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FieldValidator fieldValidator;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SigninServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init() throws ServletException {
		super.init();
		fieldValidator = new FieldValidator();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> validatedData = new HashMap<>();
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		validatedData.put("email", fieldValidator.validate("email", email));
		String password = request.getParameter("password");

		String passwordFromDB = DBHandler.getPasswordByEmail(email);
		
		if (EncryptionManager.verifyPassword(password,passwordFromDB)) {
			validatedData.put("password", null);
		} else {
			validatedData.put("password", "Password is not valid");
//			response.getWriter().append("<h1>Password is not valid</h1>");
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(validatedData);
        
        response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(json);
	}

}
