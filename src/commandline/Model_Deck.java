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
		
		//count the line, lineCounter - 1 represents the num of cards in total
		int lineCounter = 0;
		
		//store info per line
		String line;
		
		//store the all attributeName
		ArrayList<String> attributeNames = new ArrayList<String>();
		
		//store attribute value for all (which can be divided by 5)
		ArrayList<Integer> attributeValue = new ArrayList<Integer>();
		
		//store all names
		ArrayList<String> nameList = new ArrayList<String>();
		
		//store the list by spliting for each line
		String[] LineList;
		
		
		try {
			fr = new FileReader(file);
			s = new Scanner(fr);
			
			while(s.hasNextLine()) {
				
				lineCounter++;
				line = s.nextLine();	
				LineList = line.split(" ");
				
				//if read the first line, put all attribute name into list
				//if read other lines, put all value into list
				for(int i = 1; i < LineList.length; i++) {
					if(lineCounter == 1) {
						attributeNames.add(LineList[i]);
						
					}else {
						if(i == 1) {
							nameList.add(LineList[0]);
						}
						
						attributeValue.add(Integer.parseInt(LineList[i]));
					}
				}
				
				//now we read everything form the file
				
			}
		
		//close the reader and scanner
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
		
		//The initial deck contain 40 cards, each cards contain 5 attributes
		for (int i = 0; i < lineCounter - 1; i++) {
			cards.add(new Model_Card(nameList.get(i), new Model_CardCategory(attributeNames, attributeValue.subList(i, i+5))));
			
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
	 * return a certain attribute of the top cards based on Index
	 * @param attributeIndex
	 * @return the attribute that choose
	 */
	public Model_Attribute getTopCardAttribute(int attributeIndex) {
		return getTopCard().getCategory().getAttribute(attributeIndex);
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
	public Model_Card removeTopCard() {
		
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
