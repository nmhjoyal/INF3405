import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class ClientHandler extends Thread {

	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
		System.out.println("New connection at " + socket);
	}
	
	public void run() {
		try {
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			DataInputStream input = new DataInputStream(socket.getInputStream());
			String username = input.readUTF();
			String password = input.readUTF();
			if (new Credentials(username, password).isValidCredentials()) {
				//Do stuff
				output.writeBoolean(true);
				while (true) {
					String nomFichier = input.readUTF();
					System.out.println(nomFichier);
					if (nomFichier != "0") {
							File imageFile = new File(nomFichier);
							BufferedImage originalImage = ImageIO.read(imageFile);
							Sobel.process(originalImage);
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
							System.out.println("[" + username + " - " + socket.getInetAddress().getHostAddress() + ":" +
									socket.getPort() + " - " + df.format(new Date()) + "] : Image " + nomFichier + " reçue"
									+ " pour traitement.");							
						
					} else {
						break;
					}
				}
			} else {
				output.writeBoolean(false);
			}
		} catch (IOException e) {
			System.out.println("Error handling client");
		} catch (Exception e) {
			System.out.println("Error validating credentials");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Couldn't close socket");
			}
			System.out.println("Connection closed");
		}
	}
}
