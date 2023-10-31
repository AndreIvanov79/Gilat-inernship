package students.examples.gg.appbuilder;

import students.examples.gg.appbuilder.options.Options;
import students.examples.gg.appbuilder.runner.TaskRunner;
import students.examples.gg.appbuilder.runner.TaskRunnerBuilder;
import students.examples.gg.appbuilder.task.impl.CompileTask;
import students.examples.gg.appbuilder.task.impl.PackageTask;
import students.examples.gg.appbuilder.task.impl.RunTask;


public class Main {
	public static void main(String[] args) {
		TaskRunner runner = new TaskRunnerBuilder()
				.addTask(new CompileTask(), new Options().set("value1","-d")
						.set("value2",".")
						.set("value3","C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0.1\\src\\greeting\\*.java"))
				.addTask(new RunTask(),new Options().set("value","greeting.HelloWorld"))
				.addTask(new PackageTask(), new Options().set("value1","cfe")
						.set("value2", "myapp.jar").set("value3", "HelloWorld")
						.set("value4", "C:\\Users\\USER\\eclipse-workspace\\heartbeat-v0.0.1")).build();
		runner.start();
	}

}
