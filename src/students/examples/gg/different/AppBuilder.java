package students.examples.gg.different;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AppBuilder {
	
	public static void main (String[] args) throws InterruptedException, ExecutionException {
		Map<String, Object> opt = new LinkedHashMap<>();
		opt.put("command","javac");
		opt.put("value1","-d");
		opt.put("value2",".");
		opt.put("value3","C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0.1\\src\\greeting\\*.java");
		
		try {
			ProcessBuilder builder = new ProcessBuilder();
			Process first = builder
//					.command("javac","-d",".",
//							"C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0.1\\src\\greeting\\*.java")
					.command(opt.values().stream().map(n->String.valueOf(n)).collect(Collectors.toList()))
					.start();
			System.out.println(first.onExit().get().toString());
			System.out.println(first.exitValue()+"\n"+first.info());
			
			Process third = builder
					.command("java", "greeting.HelloWorld")
					.start();
			System.out.println(third.onExit().get().toString());
			System.out.println(third.exitValue()+"\n"+first.info());
			
			//ProcessBuilder builder2 = new ProcessBuilder();
			Process second = builder
					.command("jar", "cfe", 
							"myapp.jar", 
							"HelloWorld",
							"C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0.1")
					.start();
			second.onExit().get();
			System.out.println(second.exitValue());
			
			Process forth = builder
					.command("java", "-jar", "myapp.jar")
					.start();
			System.out.println(forth.onExit().get().toString());
			System.out.println(forth.exitValue()+"\n"+first.info());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
