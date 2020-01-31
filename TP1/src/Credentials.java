import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import resources.strings.*;

public class Credentials {
	public final String username;
    public final String password;
    private final String database = "./users.json";

    public Credentials(String user, String pass) {         
        this.username = user;
        this.password = pass;
     }
    
    public boolean isValidCredentials() throws Exception {
    	// Ouvrir la base de données
		FileReader reader = new FileReader(this.database);
		JSONParser jsonParser = new JSONParser();
		JSONObject object = (JSONObject) jsonParser.parse(reader);
		
		JSONArray userArray = (JSONArray) object.get("users");
		
		// Parcourir la liste d'utilisateurs pour trouver le nom d'utilisateur entré, avec le bon mot de passe
		for (int i = 0; i < userArray.size(); i++) {
			JSONObject user = (JSONObject) userArray.get(i);
			String usernameFound = (String) user.get("username");
			String passwordFound = (String) user.get("password");
			
			if (this.username.contentEquals(usernameFound)) {
				if (this.password.contentEquals(passwordFound)) {
					reader.close();
					return true;
				}
				reader.close();
				return false;
			}
		}
		
		// Cas de nouvel utilisateur: créer une entrée JSON et écrire dans la base de données
		userArray.add(this.createNewUser());
		object.put("users", userArray);
		try {
			this.addUserToDatabase(object);
		} catch (IOException e) {
			System.out.println(ErrorHandling.ERROR_ACCOUNT_CREATION);
			System.exit(-1);
		}
		System.out.println("Utilisateur ".concat(this.username).concat(" créé."));
		reader.close();
		return true;
	}
    
    private JSONObject createNewUser() {
    	JSONObject newUser = new JSONObject();
		newUser.put("username", this.username);
		newUser.put("password", this.password);
		return newUser;
    }
    
    private void addUserToDatabase(JSONObject object) throws IOException{
    	FileWriter writer = new FileWriter(this.database);
		writer.write(object.toJSONString());
		writer.flush();
		writer.close();
    }
}
