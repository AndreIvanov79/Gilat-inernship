package students.examples.gg.appbuilder.runner;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import students.examples.gg.appbuilder.exception.TaskFailureException;
import students.examples.gg.appbuilder.options.Options;
import students.examples.gg.appbuilder.result.Result;
import students.examples.gg.appbuilder.task.Task;

public class TaskRunner {

	private Task task;

	private Options options;

	private Result taskResult;

	private Map<Task, Options> runnableTasks = new LinkedHashMap<>();

	private Map<Map<Task, Options>, Result> taskResults = new HashMap<>();

	public TaskRunner(Map<Task, Options> runnableTasks) {
		this.runnableTasks = runnableTasks;
	}

	public void start() {
		for (Entry<Task, Options> entry : runnableTasks.entrySet()) {
			try {
				taskResult = entry.getKey().execute(entry.getValue());
				if (taskResult.getExitCode() != 0) {
					throw new TaskFailureException("BUILDING Failure at step " + entry.getKey().toString());
				}
			} catch (InterruptedException | ExecutionException | IOException | TaskFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getLog();
		}
	}

	private Map<Map<Task, Options>, Result> getLog() {
		taskResults.put(runnableTasks, taskResult);
		runnableTasks.keySet().forEach(task -> System.out
				.println(task.getClass().getSimpleName() + " finished with Result: " + taskResult.getExitCode()));
		return taskResults;
	}

}
