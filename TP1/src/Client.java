import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;

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
				String filename = "";
				String newFilename = "";
				while (true) {
					filename = in.readFilename();
					if (filename.equals("0")) {
						break;
					}
					newFilename = in.readNewFilename();
					FileInputStream fis;
					try {
						fis = new FileInputStream (new File("./" + filename));
					} catch (IOException e) {
						System.out.println(ErrorHandling.ERROR_OPENING_FILE);
						continue;
					}
					output.writeUTF(filename);
					int i = 0;
					int size = 0;
					while ((i = fis.read()) != -1) {
						size++;
					}
					output.writeInt(size);
					fis.close();
					fis = new FileInputStream (new File("./" + filename));
					i = 0;
					while (i < size) {
						output.write(fis.read());
						i++;
					}
					System.out.println(Result.IMAGE_SENT);
					fis.close();
					ByteArrayOutputStream fout = new ByteArrayOutputStream();
					i = 0;
					int newImageBytes = input.readInt();
					while (i < newImageBytes) {
						fout.write(input.read());
						i++;
					}
					try {
						ByteArrayInputStream filteredImageBytes = new ByteArrayInputStream(fout.toByteArray());
						BufferedImage image = ImageIO.read(filteredImageBytes);
						File outputImage = new File("./" + newFilename + ".jpg");
						ImageIO.write(image, "jpg", outputImage);
						System.out.println(Result.IMAGE_RECEIVED + outputImage.getAbsolutePath());	
						image.flush();
						filteredImageBytes.close();
					} catch (IOException e) {
						System.out.println(ErrorHandling.ERROR_CREATING_FILE);
					}	
				}
			} else {
				System.out.println(ErrorHandling.ERROR_PASSWORD);
			}
		} catch (IOException e) {
			System.out.println(ErrorHandling.ERROR_CLIENT);
		}
		in.close();
		socket.close();
	}
}
