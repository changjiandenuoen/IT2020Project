package commandline;

public class Model_Player {

		
	private String name;
	private boolean isAi;
	private int score;
	
	private Model_Deck playerDeck;
	
	
	/**
	 * Constructor
	 * @param name
	 * @param isAi
	 */
	public Model_Player(String name, boolean isAi) {
		this.name = name;
		this.isAi = isAi;
		this.playerDeck = new Model_Deck(this);
	}
	
	
	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAi() {
		return isAi;
	}
	public void setAi(boolean isAi) {
		this.isAi = isAi;
	}
	public Model_Deck getDeck() {
		return playerDeck;
	}
	public void setDeck(Model_Deck playerDeck) {
		this.playerDeck = playerDeck;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}


	/**
	 * check whether the player died
	 * @return true if no card in playerDeck, otherwise return false;
	 */
	public boolean isDead() {
		return playerDeck.size() == 0;
	}
	
}
