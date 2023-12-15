package website.main;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Servlet implementation class AvatarUloadServlet
 */
@WebServlet(name="AvatarUloadServlet", urlPatterns="/upload-avatar")
@MultipartConfig(fileSizeThreshold = 1024*1024*1,
					maxFileSize = 1024*1024*1,
					maxRequestSize = 1024*1024*1)
public class AvatarUloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AvatarUloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private static String uploadPath=null;
    
    @Override
    public void init() throws ServletException{
        uploadPath= getServletContext().getInitParameter("upload.path");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		if(!ServletFileUpload.isMultipartContent(request)){
//			throw new ServletException("Content type is not multipart/form-data");
//		}
		String fileName="";
		
		try {
            // Retrieve the file part from the request
            Part filePart = request.getPart("file");
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            // Save the file to the server
            InputStream inputStream = filePart.getInputStream();
            Files.copy(inputStream, Paths.get(uploadPath + File.separator + fileName),StandardCopyOption.REPLACE_EXISTING);
            response.getWriter().println("File uploaded successfully!");
        } catch (IOException | ServletException e) {
            response.getWriter().println("File upload failed due to an error: " + e.getMessage());
        }
    
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("status", "Ok");
		node.put("fileName", fileName);
		
		String jsonString = node.toString();
		
		PrintWriter out = response.getWriter();
		out.write(jsonString);
	}

}
