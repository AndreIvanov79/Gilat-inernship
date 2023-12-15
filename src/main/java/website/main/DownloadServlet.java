package website.main;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {

		//TODO: check for authentication 
		response.sendRedirect(request.getContextPath() + "/signup-action");  
	  
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.sendRedirect(request.getContextPath() + "/download.jsp");
//		request.getRequestDispatcher("/download.jsp").forward(request, response);
	}
}