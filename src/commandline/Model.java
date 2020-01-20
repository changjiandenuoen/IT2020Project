package commandline;

import java.util.ArrayList;

public class Model {
	
	//-1 game start, 0 normal, 1 game end
	private int gameStatus;
	
	private int round = 0;
	
	//the deck contain all cards at the initilization of the game
	private Model_Deck deck;
	
	private ArrayList<Model_Card> communalPile;
	
	private Model_Player[] players;
	
	//the index of the player how select the attribute
	private int hostIndex;
	
	
	/**
	 * 
	 */
	public Model() {
		
		initialize();
		distribute();
		
	}
	
	
	/**
	 * 1. set game status to the origin status
	 * 2. set round = 1
	 * 3. set deck to the origin status
	 * 4. set communalPile to null
	 * 5. reset players
	 */
	public void initialize() {
		//TODO:
	}
	
	
	/**
	 * put the cards from the deck to into players' deck evenly
	 */
	public void distribute() {
		//TODO:
	}
	
	/**
	 * 
	 * @return the winner
	 */
	public Model_Player battle() {
		
		Model_Player winner = null;
		
		//game continues
		while(gameStatus != 1) {
			round++;
			
			//game start at first time
			if(gameStatus == -1) {
				hostIndex =  (int)(Math.random() * players.length);
				gameStatus = 0;
			}
			
			//player start to put cards on the desk
			Model_Card[] desk = new Model_Card[players.length];
			for (int i = 0; i < players.length; i++) {
				
				desk[i] = players[i].getPlayerDeck().getTopCard();
			}
			
			winner = getRoundResult(desk);
			if(winner == null) {
				
				//TODO: There is a draw, put cards from desk into communal pile
				
				
			}else{
				
				//TODO: shuffle desk and communal pile , add all cards at the bottom 
		
			}
			
			int loseCount = 0;
			for (int i = 0; i < players.length; i++) {

				if(!players[i].isDead()) {
					loseCount++;
				}

			}
			if(loseCount == players.length -1) {
				gameStatus = 1;
				break;
			}
			
			
		}
		return winner;
	}
	
	
	/**
	 * compare all the cards in the desk through the selected attribute
	 * 
	 * @return if there is a draw, return null, else return the winner
	 */
	private Model_Player getRoundResult(Model_Card[] desk) {
		//TODO:
		return null;
	}
	
	
	/**
	 * randomize the order of card list
	 * @param cards : the all cards from the desk
	 * @return shuffled desk
	 */
	public Model_Card[] shuffleCards(Model_Card[] cards) {
		//TODO:
		return null;
	}
	
}
