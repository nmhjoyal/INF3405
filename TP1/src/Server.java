import java.net.ServerSocket;
import java.util.Iterator;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Server {
	
	private static ServerSocket listener;
	
	public static void main(String[] args) throws Exception {
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		
		
		Interface in = new Interface();
		String ipAddress = in.getIPAddress();
		int port = in.getPort();

		in.close();
		InetAddress serverIP = InetAddress.getByName(ipAddress);
		listener.bind(new InetSocketAddress(serverIP, port));
		
		try {
			while (true) {
				new ClientHandler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}
}
