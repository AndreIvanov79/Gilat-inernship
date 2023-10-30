package students.examples.gg.different;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class HeartBeatCliet {
	
	public static void main (String[] args) throws IOException, NumberFormatException, ParserConfigurationException, SAXException {
		
		String serverConfig = "C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0..1\\resources\\server_config.xml";
		
		ConfigurationProvider confiProvider = new ConfigurationProvider();
		
		System.out.println("CLIENT  starting\n");
		
		int port = Integer.parseInt(confiProvider.provide(serverConfig).get("port"));
		String ip = confiProvider.provide(serverConfig).get("ipaddress");
		
		Socket clientSocket = new Socket(ip, port);
		PrintWriter cosoleOut = new PrintWriter(System.out);
		OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        	
        	String inputLine = in.readLine();
        	System.out.println("Received: "+inputLine);
        	out.write("Bye!");
        	out.flush();
        	System.out.println("CLIENT finished");
        
	}
}
