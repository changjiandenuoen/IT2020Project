package commandline;

import java.io.File;
import java.util.ArrayList;

public class Model_Deck {
	
	Model_Player owner;
	ArrayList<Model_Card> cards;

	/**
	 * Constructor : for player's Deck
	 * @param player
	 */
	public Model_Deck(Model_Player player) {
		this.owner = player;
		cards = new ArrayList<Model_Card>();
	}
	
	/**
	 * Constructor: for the whole Deck
	 * import all card information from the txt file, and create a new ArrayLists
	 *  which contain all cards
	 *  
	 * @param file the file that contains all information about cards 
	 */
	public Model_Deck(File file) {
		cards = new ArrayList<Model_Card>();
		//TODO:
	}
	
	/**
	 * Constructor : for the desk (only contain Topcards in each round)
	 */
	public Model_Deck() {
		cards = new ArrayList<Model_Card>();
	}

	
	//getter and setter
	public Model_Player getOwner() {
		return owner;
	}

	public void setOwner(Model_Player owner) {
		this.owner = owner;
	}

	public ArrayList<Model_Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Model_Card> cards) {
		this.cards = cards;
	}


	/**
	 * get the top card from deck
	 * @return the card list that the deck currently have
	 */
	public Model_Card getTopCard() {
		
		//Top cards means the last element of arraylist
		if(cards.size() >= 1) {
			return cards.get(cards.size() - 1);
			
		}else {
			return null;
			
		}
	}
	
	
	/**
	 * 
	 * @return the card list of this deck
	 */
	public Model_Card[] getAllCards() {
		Model_Card[] cardList = new Model_Card[cards.size()];
//		cardList = cards.toArray()
		return null;
	}
	
	/**
	 * @return remove the topcard in this deck and return that card
	 */
	public Model_Card removeCard() {
		//TODO:
		return null;
	}
	
	/**
	 * remove all cards
	 */
	public void removeAllCards() {
		
	}
	
	
	/**
	 * Add cards list into the bottom of the bottom of this deck
	 * @return true if add succeed, else return false
	 */
	public boolean addToBottom(Model_Card[] cards) {
		//TODO:
		return false;
	}
	
	
	/**
	 * random the order of the card arraylist
	 */
	public void shuffle() {
		ArrayList<Model_Card> newDeck = new ArrayList<Model_Card>();
		int switchPosition;
		
		while(cards.size() > 0) {
			
			switchPosition = (int)(Math.random() * cards.size());
			newDeck.add(cards.get(switchPosition));
			cards.remove(switchPosition);

		}	
		
		cards = newDeck;
	}
}
