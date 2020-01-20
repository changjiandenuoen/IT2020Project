package commandline;

import java.io.File;


public class Model {
	
	//-1 game start, 0 normal, 1 game end
	private int gameStatus;
	
	private int round;
	
	private int currAttributeIndex;
	
	//the deck contain all cards at the initialisation of the game
	private Model_Deck deck;
	
	private Model_Deck communalPile;
	
	private Model_Player[] players;
	
	//the index of the player how select the attribute
	private int hostIndex;
	
	
	/**
	 * 
	 */
	public Model() {
		
		initialise();
		distribute();
		
	}
	
	
	/**
	 * 1. set game status to the origin status
	 * 2. set round = 1
	 * 3. set deck to the origin status
	 * 4. set communalPile to null
	 * 5. reset players' decks
	 */
	public void initialise() {
		gameStatus = 0;
		round = 1;
		currAttributeIndex = 0;
		String path = ".../StarCitizenDeck.txt";
		deck = new Model_Deck(new File(path));
		communalPile = new Model_Deck();
		if(players != null) {
			for(int i = 0; i < players.length; i++) {
				players[i].getDeck().removeAllCards();
			}
		}
	}
	
	
	/**
	 * put the cards from the deck to into players' deck evenly
	 */
	public void distribute() {
		deck.shuffle();
		
		int numPlayers = players.length;
		for(int i = 0; i < numPlayers; i++) {
			int fromIndex = (int)(deck.size() / numPlayers * i);
			int toIndex = (int)(deck.size() / numPlayers * (i + 1));
			Model_Card[] playerDeck = new Model_Card[toIndex - fromIndex];
			players[i].getDeck().addToBottom(deck.getCards().subList(fromIndex , toIndex).toArray(playerDeck));
		}
	}
	
	public void setPlayers(int numPlayers) {
		players = new Model_Player[numPlayers];
		players[0] = new Model_Player("You", false);
		for(int i = 1; i < players.length; i++) {
			players[i] = new Model_Player("AI Player " + i, true);
		}
	}
	
	
	/**
	 * 
	 * @return the winner
	 */
	public Model_Player battle() {

		Model_Player winner = null;
		
		round++;
		
		//game start at first time
		if(gameStatus == -1) {
			hostIndex =  (int)(Math.random() * players.length);
			gameStatus = 0;
		}
		
		//get a peek at the top cards and select a winner
		winner = players[0];
		for (int i = 1; i < players.length; i++) {
			if(players[i].getDeck().getTopCardAttribute(currAttributeIndex).getValue() 
					> winner.getDeck().getTopCardAttribute(currAttributeIndex).getValue())  {
				winner = players[i];
			}
		}
		
		//find out if it is a draw
		for (int i = 0; i < players.length; i++) {
			if(players[i].getName() != winner.getName()) {
				if(players[i].getDeck().getTopCardAttribute(currAttributeIndex).getValue()  
						== winner.getDeck().getTopCardAttribute(currAttributeIndex).getValue())  {
					winner = null;
					break;
				}
			}
		}
		
		//player start to put cards on the desk
		for (int i = 0; i < players.length; i++) {
			communalPile.addCard(players[i].getDeck().removeTopCard());
		}
		communalPile.shuffle();
		
		if(winner != null) {
			winner.getDeck().addToBottom(communalPile.getAllCards());
			communalPile.removeAllCards();
		}
		
		int loseCount = 0;
		for (int i = 0; i < players.length; i++) {
			if(!players[i].isDead()) {
				loseCount++;
			}
		}
		if(loseCount == players.length -1) {
			gameStatus = 1;
		}
		
		return winner;
	}
	
	public void setAttributeIndex(int index) {
		currAttributeIndex = index;
	}
	
	
}
