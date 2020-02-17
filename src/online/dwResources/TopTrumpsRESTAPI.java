package online.dwResources;


import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import shared.Model;
import shared.Model_CardCategory;
import shared.Model_Database;
import shared.Model_Deck;
import shared.Model_Player;


@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controlled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
	
	// Data model
	Model model;

	/**
	 * Constructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) {
		
		model = new Model(conf.getDeckFile(), conf.getNumAIPlayers());
	}
	
	// Data getters for xhr request
	@GET
	@Path("/model")
	@Produces(MediaType.APPLICATION_JSON)
	public Model model() throws IOException {
		return model;
	}
	@GET
	@Path("/category")
	@Produces(MediaType.APPLICATION_JSON)
	public Model_CardCategory category() throws IOException {
		return model.category;
	}
	@GET
	@Path("/data")
	@Produces(MediaType.APPLICATION_JSON)
	public Model_Database data() throws IOException {
		 return model.getDatabase();
	}
	@GET
	@Path("/deck")
	@Produces(MediaType.APPLICATION_JSON)
	public Model_Deck deck() throws IOException {	
		return model.getDeck();
	}
	@GET
	@Path("/communalPile")
	@Produces(MediaType.APPLICATION_JSON)
	public Model_Deck communalPile() throws IOException {	
		return model.getCommunalPile();
	}
	@GET
	@Path("/player")
	@Produces(MediaType.APPLICATION_JSON)
	public Model_Player player(@QueryParam("Index") int playerIndex) throws IOException {
		return model.getPlayer(playerIndex);
	}
	
	// Data setters for xhr request
	@PUT
	@Path("/setPlayers")
	@Consumes(MediaType.TEXT_PLAIN)
	public void setPlayers(String numAIPlayers) throws IOException {
		model.setPlayers(Integer.valueOf(numAIPlayers)+1);
	}
	@PUT
	@Path("/setCurrAttrIndex")
	@Consumes(MediaType.TEXT_PLAIN)
	public void setCurrAttr(String currAttrIndex) throws IOException {
		model.setCurrAttributeIndex(Integer.valueOf(currAttrIndex));
	}
	
	// Functions calling by xhr request
	@GET
	@Path("/startGame")
	public void startGame() throws IOException {
		model.startGame();
	}
	@GET
	@Path("/resetGame")
	public void resetGame() throws IOException {
		model.resetModel();
	}
	@GET
	@Path("/aiHostCurrAttr")
	public void getAIHostCurrAttr() throws IOException {
		model.getAIHostCurrAttr();
	}
	@GET
	@Path("/battle")
	public void cardsBattle() throws IOException {
		model.battle(model.drawCard());
	}
	@GET
	@Path("/whoIsWinner")
	public void whoIsWinner() {
		model.whoIsWinner();
	}
	@GET
	@Path("/autoBattle")
	public void autoBattle() {
		while(model.getGameStatus() == 0) {
			model.getAIHostCurrAttr();
			model.battle(model.drawCard());
			model.whoIsWinner();
		}
	}
	
	
	public Model getModel() {
		return model;
	}
}
