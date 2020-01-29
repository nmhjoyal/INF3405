import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Credentials {
	public final String username;
    public final String password;

    public Credentials(String user, String pass) {         
        this.username = user;
        this.password = pass;
     }
    
    public boolean isValidCredentials() throws Exception {
		boolean usernameExists = false;

		FileReader reader = new FileReader("./users.json");
		JSONParser jsonParser = new JSONParser();
		JSONObject object = (JSONObject) jsonParser.parse(reader);
		
		JSONArray userArray = (JSONArray) object.get("users");
		
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
		
		if (!usernameExists) {
			userArray.add(this.createNewUser());
			object.put("users", userArray);
			try {
				this.addUserToDatabase(object);
			} catch (IOException e) {
				System.out.println("Création d'utilisateur échouée. Veuillez réessayer.");
				System.exit(-1);
			}
			System.out.println("Utilisateur ".concat(this.username).concat(" créé."));
		}
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
    	FileWriter writer = new FileWriter("./users.json");
		writer.write(object.toJSONString());
		writer.flush();
		writer.close();
    }
}
