import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Socket socket;
	
	public static void main(String[] args) throws Exception {
		System.out.println("bitch");		
		System.out.println("Rentrez l'adresse IP du serveur, puis son port, ensuite votre nom d'utilisateur et votre mot de passe: ");
		
		Interface in = new Interface();
		String ipAddress = in.getIPAddress();
		int port = in.getPort();
		socket = new Socket(ipAddress, port);
		Credentials credentials = in.login();
		
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		output.writeUTF(credentials.username);
		output.writeUTF(credentials.password);

		socket.close();
	}
}
