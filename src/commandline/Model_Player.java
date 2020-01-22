package commandline;

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
		this.deck = new Model_Deck(this);
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
	public void setScore(int score) {
		this.score = score;
	}
	public int getIndex() {
		return index;
	}


	/**
	 * check whether the player died
	 * @return true if no card in playerDeck, otherwise return false;
	 */
	public boolean isDead() {
		return deck.size() == 0;
	}
	
}
