package shared;


import java.io.File;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

import commandline.TestLog;


public class Model {
	
	//-1 game quit, 0 normal, 1 game end
	private int gameStatus;
	// number of rounds
	private int round;
	// attribute index chosen by the host of current round
	private int currAttributeIndex;
	// the index of the player how select the attribute
	private int hostIndex;
	// the index of the winner, if -1 then no winner
	private int winnerIndex;
	// number of draws in a game
	private int numDraws;
	
	private Model_Deck deck;
	private Model_Deck communalPile;
	private Model_Player[] players;
	private Model_Card winningCard = null;
	
	@JsonIgnore
	public Model_CardCategory category;
	
	private Model_Database database;
	private TestLog testLog;
	
	private File deckFile;
	
	
	/**
	 * The constructor of Model
	 */
	public Model(String deckFile, int numAIPlayers) {
		initialise(deckFile, numAIPlayers);
	}
	
	
	/**
	 * Initialise game values
	 */
	public void initialise(String deckFilePath, int numAIPlayers) {
		gameStatus = 0;
		round = 0;
		currAttributeIndex = 0;
		hostIndex = 0;
		winnerIndex = -1;
		numDraws = 0;

		deckFile = new File(deckFilePath);
		deck = new Model_Deck(deckFile);
		deck.shuffle();
		
		communalPile = new Model_Deck();
		
		category = deck.getTopCard().getCategory();
		database = new Model_Database();
		testLog = new TestLog(this);
		
		setPlayers(numAIPlayers+1);
	}
	
	/**
	 * Initialise players, human player is always the first one - players[0]
	 * also, add the player information into Database
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
		winnerIndex = -1;
		numDraws = 0;

		communalPile.removeAllCards();
		if(players != null) {
			for(int i = 0; i < players.length; i++) {
				players[i].getDeck().removeAllCards();
				players[i].setScoreToZero();
			}
		}
		
		deck = new Model_Deck(deckFile);
		deck.shuffle();
	}
	
	/**
	 * put the cards from the deck to into players' deck evenly <br>
	 * if cannot evenly distribute, then put the extra card into communalPile
	 */
	public void distribute() {

		// log the contents of the complete deck after it has been shuffled
		testLog.wholeDeckLog("The cards in the deck after shuffle are: ");

		int deckSize = deck.size();
		
		int fromIndex = 0;
		int toIndex = 0;

		for(int i = 0; i < getNumPlayers(); i++) {
			
			fromIndex = toIndex;
			toIndex = (int)(deckSize / getNumPlayers() * (i + 1));

			players[i].getDeck().addToBottom(deck.getCards(fromIndex , toIndex));
		}
		
		// if can not split the cards evenly, put rest of the deck to communal pile
		if(toIndex < deckSize) {
			communalPile.addToBottom(deck.getCards(toIndex, deckSize));
		}
		
		deck.removeAllCards();
		
		// log the contents of the human’s deck and the computer’s deck(s)
		testLog.playerCardLog();
	}
	
	/**
	 * start a round
	 */
	public void startGame() {
		
		// log the contents of the complete deck at the beginning of the game
		testLog.wholeDeckLog("The cards in the deck are: ");

		this.distribute();
		
		// set a random player as the host
		hostIndex =  (int)(Math.random() * players.length);
		
		round++;
	}
	
	/**
	 * each player draw their top card and put it on the desk
	 * @return the deck
	 */
	public ArrayList<Model_Card> drawCard() {
		
		ArrayList<Model_Card> desk = new ArrayList<Model_Card>();
		
		for(int i = 0; i < getNumPlayers(); i++) {
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

		if(desk.size() == 0) {
			return null;
		}
		
		// log the contents of the current cards in play
		testLog.currentDeskLog(desk);
		// log the category selected and corresponding values when a user or computer selects a category
		testLog.selectedAttributeLog(currAttributeIndex);
		
		Model_Card winningCard = desk.get(0);
		Model_Card secondPlace = desk.get(1);

		for(int i = 1; i < desk.size(); i++) {
			if(desk.get(i).getAttribute(currAttributeIndex).getValue() > 
			   winningCard.getAttribute(currAttributeIndex).getValue()) {
				secondPlace = winningCard;
				winningCard = desk.get(i);
			} else if(desk.get(i).getAttribute(currAttributeIndex).getValue() > 
					  secondPlace.getAttribute(currAttributeIndex).getValue()) {
				secondPlace = desk.get(i);
			}
		}
		
		// if it is a draw, add desk to communal pile
		if(winningCard.getAttribute(currAttributeIndex).getValue() <=
				secondPlace.getAttribute(currAttributeIndex).getValue()) {
			winningCard = null;
			numDraws++;
			communalPile.addToBottom(desk);
			communalPile.shuffle();
			
			// log the contents of the communal pile
			testLog.commualPileLog();
		}
		
		// when it's not a draw: add all communal pile to winner's deck, winner become the host
		if(winningCard != null) {
			
			Model_Player winner = players[winningCard.getOwnerIndex()];
			
			communalPile.addToBottom(desk);
			communalPile.shuffle();
			winner.getDeck().addToBottom(communalPile.getAllCards());
			communalPile.removeAllCards();
			
			// log the contents of the communal pile
			testLog.commualPileLog();
			
			hostIndex = winner.getIndex();
			winner.scorePlusOne();
		}
		
		this.winningCard = winningCard;
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
			
			// log the contents of each deck after a round
			testLog.playerCardLog();
			// log the winner of the game
			testLog.winnerLog();
			
			winnerIndex = winner.getIndex();
			
			updateDatabase();
			
			return winner;
		}
		
		return null;
	}
	
	public void updateDatabase() {
		
		int[] scoreList = new int[getNumPlayers()];
		for (int i = 0; i < scoreList.length; i++) {
			scoreList[i] = players[i].getScore();
		}
		
		database.writeDataInDB(numDraws, players[winnerIndex].getIndex(), round, scoreList);
	}
	
	public void quit() {
		
		gameStatus = -1;
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
	public int getWinnerIndex() {
		return winnerIndex;
	}
	@JsonIgnore
	public Model_Deck getCommunalPile() {
		return communalPile;
	}
	@JsonIgnore
	public Model_Deck getDeck() {
		return deck;
	}
	public int getNumPlayers() {
		return players.length;
	}
	public Model_Card getWinningCard() {
		return winningCard;
	}
	
	@JsonIgnore
	public Model_Database getDatabase() {
		return database;
	}
	@JsonIgnore
	public int getNumGames() {
		return database.getNumOfGame();
	}
	@JsonIgnore
	public int getNumHumanWins() {
		return database.getNumHumWin();
	}
	@JsonIgnore
	public int getNumAIWins() {
		return database.getNumAIWin();
	}
	@JsonIgnore
	public int getLongestRoundNum() {
		return database.getLongestRound();
	}
	@JsonIgnore
	public double getAverageDraws() {
		return database.getAvgDraw();
	}
	
	@JsonIgnore
	public Model_Player getHost() {
		return players[hostIndex];
	}
	@JsonIgnore
	public Model_Player getWinner() {
		if(winnerIndex >= 0 && gameStatus == 1) {
			return players[winnerIndex];
		}
		
		return null;
	}

	public Model_Player getPlayer(int playerIndex) {
		
		return players[playerIndex];
	}

	public void setLogging(boolean isLogging) {
		testLog.setLogging(isLogging);
	}
	@JsonIgnore
	public int getAIHostCurrAttr() {
		currAttributeIndex = players[hostIndex].getDeck().getTopCard().getHighestAttrIndex();
		return currAttributeIndex;
	}
}
