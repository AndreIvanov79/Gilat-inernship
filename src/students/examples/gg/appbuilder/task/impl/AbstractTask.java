package students.examples.gg.appbuilder.task.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import students.examples.gg.appbuilder.options.Options;
import students.examples.gg.appbuilder.result.Result;
import students.examples.gg.appbuilder.task.Task;



public abstract class AbstractTask implements Task {
	
	private ProcessBuilder builder = new ProcessBuilder();
	
	private Result result = new Result();

	@Override
	public Result execute(Options options) throws InterruptedException, ExecutionException, IOException {
			
		Process process = builder
				.command(putInitialOptions(options).values().stream()
						.map(n->String.valueOf(n))
						.collect(Collectors.toList()))
					.start();
			
		
		return result.getResult(process);
	}
	
	abstract Map<String, Object> putInitialOptions(Options options);

}