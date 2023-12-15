package website.main;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import website.main.services.DatabaseManager;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;

/**
 * Servlet implementation class SignupServlet
 */
//@WebServlet(name="SignupServlet", urlPatterns="/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Signup Thread: " + Thread.currentThread().getName());
		UUID clientUUID = UUID.randomUUID();
		byte[] clientToken;
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			clientToken = messageDigest.digest(clientUUID.toString().getBytes());
			String encodedToken = Base64.getEncoder().encodeToString(clientToken);

//					response.addHeader("Token", Base64.getEncoder().encodeToString(clientToken));
			request.setAttribute("token", encodedToken);

			Connection conn = DatabaseManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement("insert into clients(id,token) values(?,?)");
			stmt.setObject(1, clientUUID);
			stmt.setObject(2, encodedToken);

			stmt.executeUpdate();

		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
