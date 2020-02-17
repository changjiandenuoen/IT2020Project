package commandline;


import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import shared.Model;


/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {
	
	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 */
	public static void main(String[] args) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		File jsonFile = new File("./TopTrumps.json");
		
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readValue(jsonFile, JsonNode.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Model model = new Model(jsonNode.get("deckFile").asText(), jsonNode.get("numAIPlayers").asInt());
		
		Controller controller = new Controller(model);
		
		View view = new View(model, controller);
		
		controller.setView(view);
		
		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args.length > 0 && args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection
		model.setLogging(writeGameLogsToFile);
		
		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			view.printChoices();
			
			if(model.getGameStatus() == -1) {
				userWantsToQuit=true;
			}
		}
		
	}
}
