import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	
	public static void main(String[] args) throws Exception {
		System.out.println("bitch");		
		System.out.println("Rentrez l'adresse IP du serveur, puis son port, ensuite votre nom d'utilisateur et votre mot de passe: ");
		
		//Socket socket = new Socket(serverAddress, port);
		Scanner in = new Scanner(System.in);
		String address = in.nextLine();
		int port = in.nextInt();
		in.nextLine(); //use the newline
		String user = in.nextLine();
		String pass = in.nextLine();
		in.close();
		
		// verif des infos entrees
		for (int i = 0; i != address.length(); i++) {
			char c = address.charAt(i);
			if(i == 3 || i == 7 || i == 11 || i == 15) {
				if (c != '.') {
					System.out.println("Votre addresse IP est invalide.");
					System.exit(-1);
				}									
			}
			else {
				if( !Character.isDigit(c)) {
					System.out.println("Votre addresse IP est invalide.");
					System.exit(-1);
				}
			}
		}
		if (port > 5050 || port < 5000) {
			System.out.println("Votre port est invalide. Il doit se situer entre 5000 et 5050.");
			System.exit(-1);
		}
		
		
		//DataInputStream in = new DataInputStream(socket.getInputStream());
		
		//String helloMsg = in.readUTF();
		//System.out.println(helloMsg);
		
		//socket.close();
		
	}
	
	
}
