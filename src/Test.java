import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		System.out.println(JRequests.get("http://api.ipify.org/"));
	}
}
