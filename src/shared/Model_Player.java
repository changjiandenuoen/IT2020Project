package shared;

public class Model_Player {

		
	private String name;
	// 0 human player, > 0 AI players
	private int index;
	private int score;
	
	private Model_Deck deck;
	
	
	/**
	 * Constructor
	 * @param name
	 * @param isAi
	 */
	public Model_Player(String name, int index) {
		this.name = name;
		this.index = index;
		score = 0;
		
		this.deck = new Model_Deck(index);
	}
	
	
	//Getters and setters
	public String getName() {
		return name;
	}
	public Model_Deck getDeck() {
		return deck;
	}
	public void setDeck(Model_Deck playerDeck) {
		this.deck = playerDeck;
	}
	public int getScore() {
		return score;
	}
	public int getIndex() {
		return index;
	}

	public void scorePlusOne() {
		score++;
	}
	public void setScoreToZero() {
		score = 0;
	}

	/**
	 * check whether the player died
	 * @return true if no card in playerDeck, otherwise return false;
	 */
	public boolean isDead() {
		return deck.size() == 0;
	}
	
	/**
	 * 
	 * @return true if this player is an AI player
	 */
	public boolean isAI() {
		return index != 0;
	}
	
}

