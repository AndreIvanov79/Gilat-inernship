package students.examples.gg.runner;

import java.util.LinkedHashMap;
import java.util.Map;


import students.examples.gg.options.Options;
import students.examples.gg.task.Task;

public class TaskRunnerBuilder {
	
	private Map<Task, Options> runnableTask = new LinkedHashMap<>();
	
	public TaskRunnerBuilder addTask (Task task, Options options) {
		runnableTask.put(task, options);
		return this;
	}
	
	public TaskRunner build() {
		return new TaskRunner(runnableTask);	
	}

}
