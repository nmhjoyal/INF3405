package resources.strings;

public interface ErrorHandling {
	static final String INVALID_IP = "Votre addresse IP est invalide. Veuillez réessayer.";
	static final String INVALID_PORT = "Votre port est invalide. Il doit se situer entre 5000 et 5050.";
	static final String ERROR_NUMBER_TYPE = "Veuillez entrer un numéro.";
	static final String ERROR_CLIENT = "Erreur de gestion client.";
	static final String ERROR_CREDENTIALS = "Erreur d'authentification.";
	static final String ERROR_SOCKET = "Incapable de fermer le socket.";
	static final String ERROR_ACCOUNT_CREATION = "Création d'utilisateur échouée. Veuillez réessayer.";
	static final String ERROR_CLIENT_CONNECTION = "Adresse IP ou port invalide. Veuillez réessayer.";
	static final String ERROR_PASSWORD = "Erreur dans la saisie du mot de passe.";	
	static final String ERROR_CREATING_SERVER = "Addresse IP ou port non disponible. Veuillez réessayer.";
}
