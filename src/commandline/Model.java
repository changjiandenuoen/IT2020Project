package commandline;

import java.io.File;


public class Model {
	
	//-1 game start, 0 normal, 1 game end
	private int gameStatus;
	// number of rounds
	private int round;
	// attribute index chosen by the host of current round
	private int currAttributeIndex;
	// the index of the player how select the attribute
	private int hostIndex;
	// number of games played
	private int numGames;
	// number of games human won
	private int numHumanWins;
	// number of games AI won
	private int numAIWins;
	// number of rounds in total
	private int numTotalRounds;
	// number of draws in total
	private int numTotalDraws;
	// the longest rounds
	private int longestRoundNum;
	
	private Model_Deck deck;
	private Model_Deck communalPile;
	private Model_Player[] players;
	
	
	/**
<<<<<<< HEAD
	 * The constructor of Model
=======
	 * Constructor
>>>>>>> 6cb5791285a45279c7f95abe64052e2273ca8212
	 */
	public Model() {
		initialise();
	}
	
	
	/**
	 * Initialise game values
	 */
	public void initialise() {
		gameStatus = 0;
		round = 1;
		currAttributeIndex = 0;
		
		
		String path = "./StarCitizenDeck.txt";
		deck = new Model_Deck(new File(path));
		communalPile = new Model_Deck();
		if(players != null) {
			for(int i = 0; i < players.length; i++) {
				players[i].getDeck().removeAllCards();
			}
		}
	}
	
	/**
	 * reset game values for a new game
	 */
	public void resetModel() {
		gameStatus = 0;
		round = 1;
		currAttributeIndex = 0;
		
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
	
<<<<<<< HEAD
	
	/**
	 * Initialize players
	 * @param numPlayers the number of players
=======
	/**
	 * Initialise players, human player is always the first one - players[0]
	 * @param numPlayers : number of players
>>>>>>> 6cb5791285a45279c7f95abe64052e2273ca8212
	 */
	public void setPlayers(int numPlayers) {
		players = new Model_Player[numPlayers];
		players[0] = new Model_Player("You", 0);
		for(int i = 1; i < players.length; i++) {
			players[i] = new Model_Player("AI Player " + i, i);
		}
	}

	/**
	 * 
	 * @param playerIndex
	 * @return the selected player's top card
	 */
	public Model_Card getPlayerTopCard(int playerIndex) {
		
		return players[playerIndex].getDeck().getTopCard();
	}
	
	/**
	 * start a round
	 */
	public void startRound() {
		
		round++;
		
		// game start at first time
		if(gameStatus == -1) {
			hostIndex =  (int)(Math.random() * players.length);
			gameStatus = 0;
		}
	}
	
	/**
	 * players compare their cards by the chosen attribute
	 * @return the winner
	 */
	public Model_Player battle() {

		Model_Player winner = players[0];

		// get a peek at each player's top card and select a winner
		for (int i = 1; i < players.length; i++) {
			if(players[i].getDeck().getTopCardAttribute(currAttributeIndex).getValue() 
					> winner.getDeck().getTopCardAttribute(currAttributeIndex).getValue())  {
				winner = players[i];
			}
		}
		
		// find out if it is a draw
		for (int i = 0; i < players.length; i++) {
			if(players[i].getName() != winner.getName()) {
				if(players[i].getDeck().getTopCardAttribute(currAttributeIndex).getValue()  
						>= winner.getDeck().getTopCardAttribute(currAttributeIndex).getValue())  {
					winner = null;
					break;
				}
			}
		}
		
		// player start to put cards on the desk
		for (int i = 0; i < players.length; i++) {
			communalPile.addCard(players[i].getDeck().removeTopCard());
		}
		communalPile.shuffle();
		
		// when it's not a draw: add all communal pile to winner's deck, winner become the host
		if(winner != null) {
			winner.getDeck().addToBottom(communalPile.getAllCards());
			communalPile.removeAllCards();
			hostIndex = winner.getIndex();
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
	
	/**
	 * choose an Attribute to be compared by the players
	 * @param playerIndex
	 * @return the chose Attribute's index
	 */
	public int chooseAttributeIndex(int playerIndex) {
		
		if(playerIndex == 0) {
			
		}
		return 0;
	}

	// Getters and setters
	public int getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public int getCurrAttributeIndex() {
		return currAttributeIndex;
	}
	public void setCurrAttributeIndex(int currAttributeIndex) {
		this.currAttributeIndex = currAttributeIndex;
	}
	public int getHostIndex() {
		return hostIndex;
	}
	public void setHostIndex(int hostIndex) {
		this.hostIndex = hostIndex;
	}
	
	
}
