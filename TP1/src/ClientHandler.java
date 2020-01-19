import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
		System.out.println("New connection at " + socket);
	}
	
	public void run() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			String username = input.readUTF();
			String password = input.readUTF();
			if (new Credentials(username, password).isValidCredentials()) {
				//Do stuff
			} else {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Error handling client");
		} catch (Exception e) {
			System.out.println("Error validating credentials");
		} finally {
			try {
				this.socket.close();
			} catch (IOException e) {
				System.out.println("Couldn't close socket");
			}
			System.out.println("Connection closed");
		}
	}
}
