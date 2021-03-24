import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

public class FullResponseBuilder {
	public static String getFullResponse(HttpURLConnection con) throws IOException {
		StringBuilder fullResponseBuilder = new StringBuilder();

		// read status and message
		fullResponseBuilder.append(con.getResponseCode())
			.append(" ")
			.append(con.getResponseMessage())
			.append("\n");

		// read headers
		con.getHeaderFields().entrySet().stream()
			.filter(entry -> entry.getKey() != null)
			.forEach(entry -> {
				fullResponseBuilder.append(entry.getKey()).append(": ");
				List headerValues = entry.getValue();
				Iterator it = headerValues.iterator();
				if (it.hasNext()) {
					fullResponseBuilder.append(it.next());
					while (it.hasNext()) {
						fullResponseBuilder.append(", ").append(it.next());
					}
				}
				fullResponseBuilder.append("\n");
			});

		// read response content
		int status = con.getResponseCode();

		// Choose which reader to use by status code
		Reader streamReader;

		if (status > 299) {
			streamReader = new InputStreamReader(con.getErrorStream());
		} else {
			streamReader = new InputStreamReader(con.getInputStream());
		}

		// Get stream
		BufferedReader in = new BufferedReader(
			streamReader
		);
		String inputLine;
		StringBuilder content = new StringBuilder("\n");
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();

		con.disconnect();

		fullResponseBuilder.append(content.toString());

		return fullResponseBuilder.toString();
	}
}
