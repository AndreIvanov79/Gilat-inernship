package students.examples.gg.appbuilder.result;

import java.util.concurrent.ExecutionException;

public class Result {
	
	private int exitCode;
	
	private String output;
	

	public int getExitCode() {
		return exitCode;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setExitCode (int exitCode) {
		this.exitCode = exitCode;
	}
	
	public void setOutput (String output) {
		this.output = output;
	}
	
	public Result getResult(Process process) throws InterruptedException, ExecutionException {
		this.setExitCode(process.onExit().get().exitValue());
		this.setOutput(process.onExit().get().toString());
		return this;
	}
}
