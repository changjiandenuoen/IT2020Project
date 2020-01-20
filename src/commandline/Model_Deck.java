package commandline;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

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
		
		
		
		FileReader fr = null;
		Scanner s;
		int lineCounter = 0;
		
		//store info per line
		String line;
		
		//store the all attributeName
		String[] attributeNames = new String[5];
		
		//store attribute value for all data
		int[] attributeValue = new int[40];
		
		//store the list by spliting for each line
		String[] LineList;
		
		
		try {
			fr = new FileReader(file);
			s = new Scanner(fr);
			
			while(s.hasNextLine()) {
				
				lineCounter++;
				line = s.nextLine();	
				LineList = line.split(" ");
				
				//if read the first line, put all attribute into List
				for(int i = 1; i < LineList.length; i++) {
					if(lineCounter == 1) {
						attributeNames[i - 1] = LineList[i];
						
					}else {
						attributeValue[i - 1] = Integer.parseInt(LineList[i]);
					}
				}
				
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(fr != null) {
					fr.close();
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
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
		
		if(cards == null) {
			return null;
		}
		
		Model_Card[] cardList = (Model_Card[]) cards.toArray(new Model_Card[cards.size()]);

		return cardList;
	}
	
	/**
	 * @return remove the topcard in this deck and return that card
	 */
	public Model_Card removeCard() {
		
		Model_Card topCard = cards.get(cards.size() - 1);
		cards.remove(cards.size() - 1);
		
		return topCard;
	}
	
	/**
	 * remove all cards
	 */
	public void removeAllCards() {
		cards = new ArrayList<Model_Card>();
	}
	
	
	/**
	 * Add cards list into the bottom of this deck
	 * @return true if add succeed, else return false
	 */
	public boolean addToBottom(Model_Card[] Inputcards) {
		
		
		int oriCardSize = cards.size();
		
		for (int i = 0; i < Inputcards.length; i++) {
			cards.add(0, Inputcards[i]);
		}
		
		if(cards.size() == oriCardSize + Inputcards.length) {
			return true;
		}else {
			return false;
		}

	}
	
	
	/**
	 * 
	 * @return the card size of the deck
	 */
	public int size() {
		return cards.size();
	}
	
	/**
	 * add a card into Deck
	 * @param the card that need to add to the deck
	 */
	public void addCard(Model_Card card) {
		
		cards.add(card);
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
