import java.awt.image.BufferedImage;
import resources.strings.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class ClientHandler extends Thread {

	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
		System.out.println(Result.NEW_CONNECTION + socket);
	}
	
	public void run() {
		try {
			// Créer les tunnels de communication avec le client
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			DataInputStream input = new DataInputStream(socket.getInputStream());
			
			// Envoyer données utilisateur pour validation du serveur
			String username = input.readUTF();
			String password = input.readUTF();
			if (new Credentials(username, password).isValidCredentials()) {
				output.writeBoolean(true);
				while (true) {
					// Lire l'image du client et la traiter
					String readImage = input.readUTF();
					if (readImage != "0") {
						ByteArrayOutputStream fout = new ByteArrayOutputStream();
						int i = 0;
						int bytes = input.readInt();
						while (i < bytes) {
							fout.write(input.read());
							i++;
						}
						byte[] data = fout.toByteArray();
						ByteArrayInputStream byteArray = new ByteArrayInputStream(data);
						BufferedImage image = ImageIO.read(byteArray);
						BufferedImage filteredImage = Sobel.process(image);
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
						System.out.println("[" + username + " - " + socket.getInetAddress().getHostAddress() + ":" +
								socket.getPort() + " - " + df.format(new Date()) + "] : Image " + readImage + " reçue"
								+ " pour traitement.");
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write( filteredImage, "jpg", baos );
						byte[] newImageData = baos.toByteArray();
						i = 0;
						output.writeInt(newImageData.length);
						while (i < newImageData.length) {
							output.write(newImageData[i]);
							i++;
						}
						fout.flush();
						fout.close();
						baos.flush();
						baos.close();
					}
				}
			} else {
				output.writeBoolean(false);
			}
		} catch (IOException e) {
			System.out.println(ErrorHandling.ERROR_CLIENT);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(ErrorHandling.ERROR_CREDENTIALS);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(ErrorHandling.ERROR_SOCKET);
			}
			System.out.println(Result.CLIENT_DISCONNECTED);
		}
	}
}
