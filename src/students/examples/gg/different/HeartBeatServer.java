package students.examples.gg.different;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class HeartBeatServer {
	public static void main (String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		String serverConfig = "C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0..1\\resources\\server_config.xml";
		
		ConfigurationProvider confiProvider = new ConfigurationProvider();
		System.out.println("SERVER  starting");
		
		int port = Integer.parseInt(confiProvider.provide(serverConfig).get("port"));
		String ip = confiProvider.provide(serverConfig).get("ipaddress");
		
		ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
		Socket clientSocket = serverSocket.accept();
		PrintWriter out = new PrintWriter(System.out);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			
            if (inputLine.equals("Bye!")) {
                out.write("Goodbye!\n");
                out.flush();
                break;
             } else {
            	 out.write("Hi!");
            	 System.out.println(inputLine);
             }
		}
		
		System.out.println("SERVER  finished");
	}

}
