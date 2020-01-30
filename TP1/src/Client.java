import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Socket socket;
	
	public static void main(String[] args) throws Exception {
		System.out.println("bitch");		
		System.out.println("Rentrez l'adresse IP du serveur, puis son port.");
		
		Interface in = new Interface();
		while (true) {
			try {
				String ipAddress = in.getIPAddress();
				int port = in.getPort();
				socket = new Socket(ipAddress, port);
				break;
			} catch (IOException e) {
				System.out.println("Adresse IP ou port invalide. Veuillez réessayer.");
				continue;
			}
		}
		
		try {
			Credentials credentials = in.login();
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(credentials.username);
			output.writeUTF(credentials.password);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			if (input.readBoolean()) {
				//Do stuff
				System.out.println("Connexion réussie.");
				String response = "";
				while (!response.equals("0")) {
					response = in.readFilename();					
					output.writeUTF(response);
				}
			} else {
				System.out.println("Erreur dans la saisie du mot de passe");
			}
		} catch (IOException e) {
			System.out.println("Error handling client");
		}
		socket.close();
	}
}
