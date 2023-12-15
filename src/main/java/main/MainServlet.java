package main;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private byte[] lastUserAgent = new byte[] {};

	private Map<String, Long> userAgentMap;

	private final long threshold = 2000;

	private final long delay = 1000;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		userAgentMap = new ConcurrentHashMap<String, Long>();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		userAgentMap = null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		long currentTime = System.currentTimeMillis() / 1000;
		String userAgent = request.getHeader("User-Agent");
//		System.out.println("ActualAGENT: "+userAgent+"__"+Thread.currentThread().getId());
		byte[] hashedUserAngent = null;
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			hashedUserAngent = mDigest.digest();
			System.out.println(
					"ActualAGENT: " + Arrays.toString(hashedUserAngent) + "__" + Thread.currentThread().getId());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userAgent.equals(lastUserAgent)) {
			String hexUser = Base64.encodeBase64String(hashedUserAngent);

			long valueObject = userAgentMap.get(hexUser);
			if (valueObject != 0) {
				if ((currentTime - userAgentMap.get(hexUser).longValue()) < threshold) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else {
				userAgentMap.put(hexUser, currentTime);
			}
		} else {

			lastUserAgent = hashedUserAngent;
		}

		response.getWriter().append("Served at: ").append(String.valueOf(Thread.currentThread().getId()));
	}

}
