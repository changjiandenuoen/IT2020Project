package commandline;

import java.io.File;

import java.util.ArrayList;

public class Model {
	
	//-1 game quit, 0 normal, 1 game end
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
	// number of draws in total
	private int numTotalDraws;
	// the longest rounds
	private int longestRoundNum;
	// the id of current game
	private int gameId;
	
	private Model_Database statistic;
	
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
		numTotalDraws = 0;
		longestRoundNum = 0;
		
		String path = "./StarCitizenDeck.txt";
		deck = new Model_Deck(new File(path));
		communalPile = new Model_Deck();
		
		category = deck.getTopCard().getCategory();
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
	 * reset game values for a new game:
	 * remove all cards from players and add them to the whole deck
	 */
	public void resetModel() {
		gameStatus = 0;
		round = 0;
		currAttributeIndex = 0;

		if(players != null) {
			for(int i = 0; i < players.length; i++) {
				deck.addToBottom(players[i].getDeck().removeAllCards());
				players[i].setScoreToZero();
			}
		}
	}
	
	/**
	 * put the cards from the deck to into players' deck evenly <br>
	 * if cannot evenly distribute, then put the extra card into communalPile
	 */
	public void distribute() {
		
		deck.shuffle();

		int deckSize = deck.size();
		
		int fromIndex = 0;
		int toIndex = 0;

		for(int i = 0; i < numPlayers(); i++) {
			
			fromIndex = toIndex;
			toIndex = (int)(deckSize / numPlayers() * (i + 1));

			players[i].getDeck().addToBottom(deck.getCards(fromIndex , toIndex));
		}
		
		// if can not split the cards evenly, put rest of the deck to communal pile
		if(toIndex < deckSize) {
			communalPile.addToBottom(deck.getCards(toIndex, deckSize));
		}
		
		deck.removeAllCards();
	}
	
	/**
	 * start a round
	 */
	public void startGame() {

		this.distribute();
		
		// set a random player as the host
		hostIndex =  (int)(Math.random() * players.length);
	}
	
	/**
	 * each player draw their top card and put it on the desk
	 * @return the deck
	 */
	public ArrayList<Model_Card> drawCard() {
		
		ArrayList<Model_Card> desk = new ArrayList<Model_Card>();
		
		for(int i = 0; i < numPlayers(); i++) {
			if(!players[i].isDead())
				desk.add(players[i].getDeck().removeTopCard());
		}
		
		 return desk;
	}
	
	/**
	 * players compare their top cards by the chosen attribute
	 * @return the winning card
	 */
	public Model_Card battle(ArrayList<Model_Card> desk) {
		
		Model_Card winningCard = desk.get(0);
		Model_Card secondPlace = desk.get(1);

		for(int i = 0; i < desk.size(); i++) {
			if(desk.get(i).getAttribute(currAttributeIndex).getValue() > 
				winningCard.getAttribute(currAttributeIndex).getValue()) {
				secondPlace = winningCard;
				winningCard = desk.get(i);
			}
		}
		
		// if it is a draw, add desk to communal pile
		if(winningCard.getAttribute(currAttributeIndex).getValue() <=
				secondPlace.getAttribute(currAttributeIndex).getValue()) {
			winningCard = null;
			numTotalDraws++;
			communalPile.addToBottom(desk);
			communalPile.shuffle();
		}
		
		// when it's not a draw: add all communal pile to winner's deck, winner become the host
		if(winningCard != null) {
			
			Model_Player winner = winningCard.getOwner();
			
			communalPile.addToBottom(desk);
			communalPile.shuffle();
			winner.getDeck().addToBottom(communalPile.getAllCards());
			communalPile.removeAllCards();
			
			hostIndex = winner.getIndex();
			winner.scorePlusOne();
		}
		
		return winningCard;
	}

	/**
	 * check if the game is over and find out who is winner
	 * @return the winner if all cards are in winner's deck, else return null
	 */
	public Model_Player whoIsWinner() {
		
		Model_Player winner = null;
		int loserCount = 0;
		for (int i = 0; i < players.length; i++) {
			if(players[i].isDead()) {
				loserCount++;
			} else {
				winner = players[i];
			}
		}
		
		if(loserCount == players.length -1) {
			gameStatus = 1;
			numGames++;
			if(winner.getIndex() == 0) numHumanWins++;
			else numAIWins++;
			if(round > longestRoundNum)
				longestRoundNum = round;
			
			return winner;
		}
		
		return null;
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
	public Model_Deck getCommunalPile() {
		return communalPile;
	}
	public Model_Deck getDeck() {
		return deck;
	}


	public double getAverageDraws() {
		if(numGames == 0)
			return 0;
		else
			return numTotalDraws / numGames;
	}
	
	/**
	 * 
	 * @param playerIndex
	 * @return the selected player
	 */
	public Model_Player getPlayer(int playerIndex) {
		
		return players[playerIndex];
	}
	
	/**
	 * round ++
	 */
	public void roundPlusOne() {
		round++;
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
}
