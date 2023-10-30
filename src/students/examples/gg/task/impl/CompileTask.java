package students.examples.gg.task.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import students.examples.gg.options.Options;
import students.examples.gg.result.Result;
import students.examples.gg.task.Task;

public class CompileTask extends AbstractTask {
//	
//	private ProcessBuilder builder = new ProcessBuilder();
//	
//	private Result result = new Result();
//	
//	private Map<String, Object> compileOptions = new LinkedHashMap<>();

//	@Override
//	public Result execute(Options options) throws InterruptedException, ExecutionException, IOException {
////		compileOptions.put("command", "javac");
////		compileOptions.putAll(options.getMap());
////			
//		Process process = builder
////					.command(compileOptions
////							.values().stream()
////							.map(n->String.valueOf(n))
////							.collect(Collectors.toList()))
//				.command(putInitialOptions(options).values().stream()
//						.map(n->String.valueOf(n))
//						.collect(Collectors.toList()))
//					.start();
//			
//		
//		return result.getResult(process);
//	}
	
	Map<String, Object> putInitialOptions(Options options) {
		Map<String, Object> initialOptions = new LinkedHashMap<>();
		initialOptions.put("command", "javac");
		initialOptions.putAll(options.getMap());
		return initialOptions;
	}

}
