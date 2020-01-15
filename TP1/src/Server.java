import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Server {
	
	private static ServerSocket listener;
	
	public static void main(String[] args) throws Exception {
		System.out.println("bitch");
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		
		InetAddress serverIP = InetAddress.getByName("127.0.0.1");
		
		listener.bind(new InetSocketAddress(serverIP, 5000));
		
		
	}
}
