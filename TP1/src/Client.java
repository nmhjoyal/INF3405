import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import resources.strings.ErrorHandling;
import resources.strings.Result;

public class Client {

	private static Socket socket;
	
	public static void main(String[] args) throws Exception {
		
		Interface in = new Interface();
		
		// Itérer tant que l'addresse IP et le port sont invalides
		while (true) {
			try {
				String ipAddress = in.getIPAddress();
				int port = in.getPort();
				socket = new Socket(ipAddress, port);
				break;
			} catch (IOException e) {
				System.out.println(ErrorHandling.ERROR_CLIENT_CONNECTION);
				continue;
			}
		}
		
		try {
			// Authentification avec le serveur
			Credentials credentials = in.login();
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(credentials.username);
			output.writeUTF(credentials.password);
			DataInputStream input = new DataInputStream(socket.getInputStream());
			if (input.readBoolean()) {
				
				// Envoyer l'image au serveur

				System.out.println(Result.CONNECTION_SUCCESSFUL);
				String response = "";
				while (!response.equals("0")) {
					response = in.readFilename();					
					output.writeUTF(response);
				}
			} else {
				System.out.println(ErrorHandling.ERROR_PASSWORD);
			}
		} catch (IOException e) {
			System.out.println(ErrorHandling.ERROR_CLIENT);
		}
		socket.close();
	}
}
