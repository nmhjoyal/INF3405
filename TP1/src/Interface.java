import java.io.Console;
import java.util.Scanner;

public class Interface {

	private Scanner inputScanner;
	
	public Interface() {
		inputScanner = new Scanner(System.in);
	}
	
	public String getIPAddress() {
		System.out.println("Veuillez entrer l'adresse IP.");
		boolean isValid = false;
		String address = "";
		while (!isValid) {
			isValid = true;
			address = inputScanner.nextLine();
			String[] parts;
			parts = address.split("\\.");
			if (parts.length > 3) {
				for (int i = 0; i < parts.length; i++) {
					try {
						int number = Integer.parseInt(parts[i]);
						if (number < 0) {
							System.out.println("Votre addresse IP est invalide. Veuillez réessayer.");
							isValid = false;
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println("Votre addresse IP est invalide. Veuillez réessayer.");
						isValid = false;
						break;
					}
				}
			} else {
				System.out.println("Votre addresse IP est invalide. Veuillez réessayer");
				isValid = false;
			}
		}
		return address;
	}
	
	public int getPort() {
		System.out.println("Entrer le port.");
		int port = 0;
		while (true) {
			String input = inputScanner.next();
			try {
				port = Integer.parseInt(input);
				if (port > 5050 || port < 5000) {
					System.out.println("Votre port est invalide. Il doit se situer entre 5000 et 5050.");
					continue;
				}
				break;
			} catch(NumberFormatException e) {
				System.out.println("Valeur de port invalide. Veuillez entrer un numéro valide.");
			}
		}
		return port;
	}
	
	public Credentials login() {
		Console console = null;
		String username = "";
		char[] password = null;
		try {
			console = System.console();
			console.flush();
			if (console != null) {
				username = console.readLine("Nom d'utilisateur : ");
	        	password = console.readPassword("Mot de passe : ");
	        }
	    } catch(Exception ex) {
	    	ex.printStackTrace();      
	    }
		return new Credentials(username, new String(password));
	}
	
	public void close() {
		inputScanner.close();
	}
}
