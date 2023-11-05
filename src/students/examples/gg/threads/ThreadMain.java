package students.examples.gg.threads;

public class ThreadMain {

	static int number = 0;

	public static void main(String[] args) {

		Runnable incrementTask = () -> {
			while (number < 100) {
				number++;
				System.out.println("Number: " + number + " " + Thread.currentThread().getName());
			}
		};

		Thread thread1 = new Thread(incrementTask);
		Thread thread2 = new Thread(incrementTask);
		Thread thread3 = new Thread(incrementTask);

		thread1.start();
		thread2.start();
		thread3.start();

		try {
			thread1.join();
			thread2.join();
			thread3.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
