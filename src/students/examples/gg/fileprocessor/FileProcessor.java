package students.examples.gg.fileprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class FileProcessor {
	public static void main(String[] args) {
		try {
			copyFile();
			writeJson(xmlToObject());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void watching() throws IOException, InterruptedException {
		WatchService watchService = FileSystems.getDefault().newWatchService();

		Path path = Paths.get("C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0.1\\src\\students\\examples\\gg\\fileprocessor");

		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);

		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
			}
			key.reset();
		}
	}

	public static void copyFile() {
		Path sourceDirectory = Paths.get("./resources/");
		Path destinationDirectory = Paths.get("./src/students/examples/gg/fileprocessor/");
		String fileName = "Player.xml";

		Path sourceFile = sourceDirectory.resolve(fileName);
		Path destinationFile = destinationDirectory.resolve(fileName);

		try {
			Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("XML file copied successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Player xmlToObject() throws IOException {
		System.out.println("Converting xml to object ...");
		String content = new String(
				Files.readAllBytes(Paths.get("./src/students/examples/gg/fileprocessor/Player.xml")));
		
		XmlMapper xmlMapper = new XmlMapper();
		Player player = xmlMapper.readValue(content, Player.class);

		return player;
	}

	public static void writeJson(Player player) throws StreamWriteException, DatabindException, IOException {
		System.out.println(player);
		File output = new File("./src/students/examples/gg/fileprocessor/Player.json");
		JsonMapper jMapper = new JsonMapper();
		jMapper.enable(SerializationFeature.INDENT_OUTPUT);
		jMapper.writeValue(output, player);

		System.out.println("OUTPUT: " + jMapper.readValue(output, Player.class));
	}

}
