import java.io.IOException;

public class Test {

	public static double timeURL(String url) throws IOException {
		// Init timer variables
		long start; long stop;

		// Start timer
		start = System.nanoTime();

		// Execute request
		JResponse resp = JRequests.get(url);
		System.out.println(resp.content());

		// Stop timer
		stop = System.nanoTime();

		// Calculate time
		return (stop - start) / 1000000000.0;
	}

	public static void main(String[] args) throws IOException {
		// Print response
		System.out.println("HTTP REQUEST EXECUTED IN [" + timeURL("http://httpbin.org/get") + "] SECONDS");
		System.out.println("HTTPS REQUEST EXECUTED IN [" + timeURL("https://httpbin.org/get") + "] SECONDS");
	}
}
