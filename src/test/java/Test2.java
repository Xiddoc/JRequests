import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Test2 {

	private static String run(String url) throws IOException {
		final URLConnection c = new URL(url).openConnection();
		c.connect();
		try (InputStream is = c.getInputStream()) {
			final byte[] buf = new byte[1024];
			final StringBuilder b = new StringBuilder();
			int read = 0;
			while ((read = is.read(buf)) != -1) {
				b.append(new String(buf, 0, read));
			}
			return b.toString();
		}
	}

	public static void main(String[] args) throws IOException {
		// Start the timer
		long startTime = System.nanoTime();
		int iterations = 1;

		// Loop 10 times
		for (int i = 0; i < iterations; i++) {
			// Execute GET request
			run("https://httpbin.org/get");
		}

		// Stop the timer
		long endTime = System.nanoTime();

		// Calculate the average
		float average = (((float) (endTime - startTime)) / 1000000000) / iterations;

		// Print results (0.953286 on my system)
		System.out.println(average);
	}
}