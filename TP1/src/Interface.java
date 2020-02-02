import java.io.Console;
import java.util.Scanner;

import resources.strings.ErrorHandling;
import resources.strings.Prompt;

public class Interface {

	private Scanner inputScanner;
		
	public Interface() {
		inputScanner = new Scanner(System.in);
	}
	
	public String getIPAddress() {
		System.out.println(Prompt.ENTER_IP);
		boolean isValid = false;
		String address = "";
		
		// Redemander l'addresse IP tant qu'elle n'est pas valide
		while (!isValid) {
			isValid = true;
			address = inputScanner.nextLine();
			String[] parts;
			parts = address.split("\\.");
			if (parts.length == 4) {
				for (int i = 0; i < parts.length; i++) {
					try {
						int number = Integer.parseInt(parts[i]);
						if (number < 0) {
							System.out.println(ErrorHandling.INVALID_IP);
							isValid = false;
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println(ErrorHandling.ERROR_NUMBER_TYPE);
						isValid = false;
						break;
					}
				}
			} else {
				System.out.println(ErrorHandling.INVALID_IP);
				isValid = false;
			}
		}
		return address;
	}
	
	public int getPort() {
		System.out.println(Prompt.ENTER_PORT);
		int port = 0;
		// Redemander le port tant qu'il ne se situe pas entre 5000 et 5050
		while (true) {
			String input = inputScanner.next();
			try {
				port = Integer.parseInt(input);
				if (port > 5050 || port < 5000) {
					System.out.println(ErrorHandling.INVALID_PORT);
					continue;
				}
				break;
			} catch(NumberFormatException e) {
				System.out.println(ErrorHandling.ERROR_NUMBER_TYPE);
			}
		}
		inputScanner.nextLine();
		return port;
	}
	
	public Credentials login() {
		System.out.println(Prompt.ENTER_USERNAME);
		String username = inputScanner.nextLine();
		System.out.println(Prompt.ENTER_PASSWORD);
		String password = inputScanner.nextLine();  
		return new Credentials(username, new String(password));
	}
	
	public String readFilename() {
		System.out.println(Prompt.ENTER_FILENAME);
		return inputScanner.nextLine();
	}
	
	public String readNewFilename() {
		System.out.println(Prompt.ENTER_NEW_FILENAME);
		return inputScanner.nextLine();
	}
	
	public void close() {
		inputScanner.close();
	}
}
