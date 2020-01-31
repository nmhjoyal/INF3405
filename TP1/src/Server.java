import java.net.ServerSocket;
import java.util.Iterator;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import resources.strings.ErrorHandling;

public class Server {
	
	private static ServerSocket listener;
	
	public static void main(String[] args) throws Exception {
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		
		
		Interface in = new Interface();
		
		// Itérer tant que l'adresse IP ou le port ne se créent pas
		while (true) {
			try {
				String ipAddress = in.getIPAddress();
				int port = in.getPort();
				InetAddress serverIP = InetAddress.getByName(ipAddress);
				listener.bind(new InetSocketAddress(serverIP, port));
				break;
			} catch (BindException e) {
				System.out.println(ErrorHandling.ERROR_CREATING_SERVER);
			}
		}
		in.close();
		
		// Itérer pour accepter des connexions clients
		try {
			while (true) {
				new ClientHandler(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}
}
