package students.examples.gg.appbuilder.runner;

import java.util.LinkedHashMap;
import java.util.Map;

import students.examples.gg.appbuilder.options.Options;
import students.examples.gg.appbuilder.task.Task;

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
