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
	
	public Model_CardCategory category;
	
	
	/**
	 * The constructor of Model
	 */
	public Model() {
		initialise();
	}
	
	
	/**
	 * Initialise game values
	 */
	public void initialise() {
		gameStatus = 0;
		round = 0;
		currAttributeIndex = 0;
		hostIndex = 0;
		
		numGames = 0;
		numHumanWins = 0;
		numAIWins = 0;
		numTotalRounds = 0;
		numTotalDraws = 0;
		longestRoundNum = 0;
		
		String path = "./StarCitizenDeck.txt";
		deck = new Model_Deck(new File(path));
		communalPile = new Model_Deck();
		
		category = deck.getTopCard().getCategory();
	}
	
	/**
	 * reset game values for a new game
	 */
	public void resetModel() {
		gameStatus = 0;
		round = 0;
		currAttributeIndex = 0;
		
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
	
	/**
	 * Initialise players, human player is always the first one - players[0]
	 * @param numPlayers : number of players
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
	 * 
	 * @return the host player
	 */
	public Model_Player getHost() {
		
		return players[hostIndex];
	}
	
	public int numPlayers() {
		return players.length;
	}
	
	/**
	 * start a round
	 */
	public void startRound() {
		
		round++;
		numTotalRounds++;
		
		// game start at first time
		if(gameStatus == -1) {
			hostIndex =  (int)(Math.random() * players.length);
			gameStatus = 0;
		}
	}
	
	/**
	 * players compare their top cards by the chosen attribute
	 * @return the winner
	 */
	public Model_Player battle() {

		Model_Player winner = players[0];
		Model_Player secondPlace = null;

		// get a peek at each player's top card and select a winner
		for (int i = 1; i < players.length; i++) {
			if(players[i].getDeck().getTopCardAttribute(currAttributeIndex).getValue() 
					> winner.getDeck().getTopCardAttribute(currAttributeIndex).getValue())  {
				secondPlace = winner;
				winner = players[i];
			}
		}
		
		// find out if it is a draw
		if(winner.getDeck().getTopCardAttribute(currAttributeIndex).getValue() <=
				secondPlace.getDeck().getTopCardAttribute(currAttributeIndex).getValue()) {
			winner = null;
			numTotalDraws++;
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
		
		return winner;
	}
	
	/**
	 * check if the game is over and find out who is winner
	 * @return the winner if all cards are in winner's deck, else return null
	 */
	public Model_Player whoIsWinner() {
		
		int winnerIndex = 0;
		int loserCount = 0;
		for (int i = 0; i < players.length; i++) {
			if(!players[i].isDead()) {
				loserCount++;
			} else {
				winnerIndex = i;
			}
		}
		
		if(loserCount == players.length -1) {
			gameStatus = 1;
			numGames++;
			if(winnerIndex == 0) numHumanWins++;
			else numAIWins++;
			if(round > longestRoundNum)
				longestRoundNum = round;
			
			return players[winnerIndex];
		}
		
		return null;
	}
	
	public boolean isHumanDead() {
		
		return players[0].isDead();
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
	public int getNumGames() {
		return numGames;
	}
	public int getNumHumanWins() {
		return numHumanWins;
	}
	public int getNumAIWins() {
		return numAIWins;
	}
	public int getLongestRoundNum() {
		return longestRoundNum;
	}
	
	public double getAverageDraws() {
		return numTotalDraws / numGames;
	}
	
}
