
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
	// the index of the winner, if -1 then no winner
	private int winnerIndex;
	// number of draws in a game
	private int numDraws;
	
	private Model_Deck deck;
	private Model_Deck communalPile;
	private Model_Player[] players;
	
	public Model_CardCategory category;
	
	private Model_Database database;
	
	private TestLog testLog;
	
	
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
		winnerIndex = -1;
		numDraws = 0;
		
		String path = "./LOLDeck.txt";
		deck = new Model_Deck(new File(path));
		communalPile = new Model_Deck();
		
		category = deck.getTopCard().getCategory();
		database = new Model_Database();
		testLog = new TestLog(this);
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

		if(players != null) {
			for(int i = 0; i < players.length; i++) {
				deck.addToBottom(players[i].getDeck().removeAllCards());
				deck.addToBottom(communalPile.removeAllCards());
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
		
		// log the contents of the complete deck after it has been shuffled
		testLog.wholeDeckLog("The cards in the deck after shuffle are: ");

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
		
		// log the contents of the current cards in play
		testLog.currentDeskLog(desk);
		// log the category selected and corresponding values when a user or computer selects a category
		testLog.selectedAttributeLog(currAttributeIndex);
		
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
			numDraws++;
			communalPile.addToBottom(desk);
			communalPile.shuffle();
			
			// log the contents of the communal pile
			testLog.commualPileLog();
		}
		
		// when it's not a draw: add all communal pile to winner's deck, winner become the host
		if(winningCard != null) {
			
			Model_Player winner = winningCard.getOwner();
			
			communalPile.addToBottom(desk);
			communalPile.shuffle();
			winner.getDeck().addToBottom(communalPile.getAllCards());
			communalPile.removeAllCards();
			
			// log the contents of the communal pile
			testLog.commualPileLog();
			
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
				winnerIndex = i;
			}
		}
		if(loserCount == players.length -1) {
			gameStatus = 1;
			
			// log the contents of each deck after a round
			testLog.playerCardLog();
			// log the winner of the game
			testLog.winnerLog();
			
			return winner;
		}
		
		return null;
	}
	
	public void updateDatabase() {
		
		int[] scoreList = new int[numPlayers()];
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
	public Model_Deck getCommunalPile() {
		return communalPile;
	}
	public Model_Deck getDeck() {
		return deck;
	}

	public int getNumGames() {
		return database.getNumOfGame();
	}
	public int getNumHumanWins() {
		return database.getNumHumWin();
	}
	public int getNumAIWins() {
		return database.getNumAIWin();
	}
	public int getLongestRoundNum() {
		return database.getLongestRound();
	}
	public double getAverageDraws() {
		return database.getAvgDraw();
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
	public Model_Player getWinner() {
		if(winnerIndex >= 0 && gameStatus == 1) {
			return players[winnerIndex];
		}
		
		return null;
	}
	
	public int numPlayers() {
		return players.length;
	}
	
	public void setLogging(boolean isLogging) {
		testLog.setLogging(isLogging);
	}
}
