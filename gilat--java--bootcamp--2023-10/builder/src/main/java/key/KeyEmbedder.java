package key;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "embed", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class KeyEmbedder extends AbstractMojo {

	@Parameter(property = "src", defaultValue = "${project.basedir}", required = true)
	private File baseFolder;

	private UUID clientId;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		getLog().info("Embedder starting...");
		getLog().info("Source folder: " + baseFolder);

		String url = "jdbc:postgresql://localhost:5436/gg?user=postgres&password=postgres&ssl=false";
		

		clientId = UUID.randomUUID();

		File sourceFile = new File(baseFolder, "src/main/java/student/examples/client/HBClient.java");
		if (sourceFile.exists()) {
			try {

				BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
				String fileContent = reader.lines().collect(Collectors.joining("\n"));

				getLog().info("File content: " + fileContent);
				reader.close();

				KeyGenerator keygenerator = KeyGenerator.getInstance("DES");

				SecretKey secretKey = new SecretKeySpec(keygenerator.generateKey().getEncoded(), "DES");
				
				try {
					Connection conn = DriverManager.getConnection(url);
					insertToDb(clientId, byteToHex(secretKey.getEncoded()),conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

				String embedded = fileContent.replace("1, 1, 1, 1, 1, 1, 1, 1", bytesToString(secretKey.getEncoded()))
						.replace("1111", clientId.toString()).replace("HBClient", "HBClientDist");

				getLog().info("Embedded: " + embedded);

				File distFile = new File("src/main/java/student/examples/client/HBClientDist.java");
				BufferedWriter writer = new BufferedWriter(new FileWriter(distFile));
				writer.write(embedded);
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String bytesToString(byte[] byteArray) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			buffer.append(byteArray[i]);
			if (i == byteArray.length - 1) {
				buffer.append("");
			} else {
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}

	public String byteToHex(byte[] bytes) {
		StringBuilder strBuilder = new StringBuilder();
		for (byte b : bytes) {
			strBuilder.append(String.format("%02X", b));
		}
		return strBuilder.toString();
	}

	public static String getIdFromDb(Connection conn) throws SQLException {
		String id = "";
		String query = "SELECT * FROM clients;";
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				id = rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void insertToDb(Object id, String secretKey, Connection conn) {
		String query = "INSERT INTO clients(id, key) VALUES(?,?);";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setObject(1, id);
			pstmt.setString(2, secretKey);
			pstmt.executeUpdate();
			System.out.println("INSERT DATA TO DB");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
