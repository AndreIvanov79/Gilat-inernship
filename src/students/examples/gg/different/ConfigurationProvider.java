package students.examples.gg.different;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigurationProvider {
	
	public Map<String, String> provide(String fileName) 
			throws ParserConfigurationException, SAXException, IOException {
		
		File file = new File(fileName); 
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    	
    	DocumentBuilder db = dbf.newDocumentBuilder();  
    	Document doc = db.parse(file);  
    	doc.getDocumentElement().normalize();  

    	NodeList nodeList = doc.getElementsByTagName("config");
    	
    	Map<String, String> map = new HashMap<>();
    	
    	for (int itr = 0; itr < nodeList.getLength(); itr++) {
    		
    		Node node = nodeList.item(itr); 
    		
    		Element eElement = (Element) node;
    		
    		map.put("ipaddress", eElement.getElementsByTagName("ipaddress").item(0).getTextContent().toString());
    		map.put("port", eElement.getElementsByTagName("port").item(0).getTextContent().toString());	
    	}
    	
    	return map;
	}

}
	
