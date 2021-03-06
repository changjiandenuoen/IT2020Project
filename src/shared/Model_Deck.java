
package shared;


import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Scanner;


public class Model_Deck {
	
	private int ownerIndex = -1;
	
	private ArrayList<Model_Card> cards;


	/**
	 * Constructor : for player's Deck
	 * @param player
	 */
	public Model_Deck(int playerIndex) {
		this.ownerIndex = playerIndex;
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
		
		//create the deck
		cards = new ArrayList<Model_Card>();
		
		//count the line, lineCounter - 1 represents the number of cards in total
		int lineCounter = 0;
		
		//store info per line
		String line;
		
		//store the all attributeName
		ArrayList<String> attributeNames = new ArrayList<String>();
		
		//store attribute value for all (which can be divided by 5)
		ArrayList<Integer> attributeValue = new ArrayList<Integer>();
		
		//store all names
		ArrayList<String> nameList = new ArrayList<String>();
		
		//store the list by splitting for each line
		String[] LineList;

		try {
			FileReader fr = new FileReader(file);
			Scanner s = new Scanner(fr);
			
			while(s.hasNextLine()) {
				
				lineCounter++;
				line = s.nextLine();	
				LineList = line.split(" ");
				
				/*
				 * read each line:
				 * 		1. if first line
				 * 			- read all attributeNames
				 * 		2. if other line
				 * 			- read all cardsName into nameList
				 * 			- read all attribute value into attributeValue
				 */
				for(int i = 1; i < LineList.length; i++) {
					if(lineCounter == 1) {
						attributeNames.add(LineList[i]);
					} else {
						if(i == 1) {
							nameList.add(LineList[0]);
						}
						attributeValue.add(Integer.parseInt(LineList[i]));
					}
				}
				//now we read everything form the file
			}
			
			fr.close();
			s.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		int count = attributeNames.size();
		for (int i = 0; i < lineCounter - 1; i++) {
			addCard(new Model_Card(nameList.get(i), new Model_CardCategory(attributeNames, attributeValue.subList(i * count, (i+1) * count))));
		}
	}
	
	/**
	 * Constructor : for an empty deck
	 */
	public Model_Deck() {
		cards = new ArrayList<Model_Card>();
	}

	
	//Getter and setter
	public int getOwnerIndex() {
		return ownerIndex;
	}
	
	public void setOwner(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}
	
	
	

	/**
	 * get a part of the deck as an array cards
	 * @param fromIndex cards array start at this index (include)
	 * @param toIndex cards array end at this index (exclude)
	 * @return the cards array
	 */
	public Model_Card[] getCards(int fromIndex, int toIndex) {

		try {
			int length = toIndex - fromIndex;
			
			Model_Card[] cardList = new Model_Card[length];
			
			for(int i = fromIndex; i < toIndex; i++) {
				cardList[i-fromIndex] = cards.get(i);
			}
			
			return cardList;
			
		} catch(Exception e) {
			e.printStackTrace();
			// TODO: error
			return null;
		}
	}
	
	/**
	 * get a specific card from deck
	 * @param the card's index
	 * @return the card
	 */
	public Model_Card getCard(int index) {

		if(cards.size() > 0 && index < cards.size()) {
			return cards.get(index);
		}else {
			// TODO: error
			return null;
		}
	}

	/**
	 * get the top card from deck
	 * @return the card list that the deck currently have
	 */
	public Model_Card getTopCard() {
		
		//Top cards means the last element of ArrayList
		if(cards.size() > 0) {
			return cards.get(cards.size() - 1);
		}else {
			// TODO: error
			return null;
		}
	}
	
	/**
	 * 
	 * @return the card list of this deck
	 */
	public Model_Card[] getAllCards() {
		
		if(cards.size() == 0) {
			// TODO: error
			return null;
		}
		
		Model_Card[] cardList = new Model_Card[cards.size()];
		
		for(int i = 0; i < cards.size(); i++) {
			cardList[i] = cards.get(i);
		}

		return cardList;
	}
	
	/**
	 * @return remove the top card in this deck and return that card
	 */
	public Model_Card removeTopCard() {
		
		Model_Card topCard = null;
		if(cards.size() > 0) {
			topCard = cards.get(cards.size() - 1);
			cards.remove(cards.size() - 1);
		}

		return topCard;
	}
	
	
	/**
	 * remove all cards 
	 * @return the cardList before remove
	 */
	public Model_Card[] removeAllCards() {
		
		Model_Card[] cardList = getAllCards();
		
		cards = new ArrayList<Model_Card>();
		
		return cardList;
	}
	
	
	/**
	 * Add cards list into the bottom of this deck
	 * @return true if add succeed, else return false
	 */
	public void addToBottom(Model_Card[] Inputcards) {

		if(Inputcards == null) return;
		
		for (int i = 0; i < Inputcards.length; i++) {
			addCard(0, Inputcards[i]);
		}
	}
	
	
	/**
	 * Add cards list into the bottom of this deck
	 * @return true if add succeed, else return false
	 */
	public void addToBottom(ArrayList<Model_Card> Inputcards) {

		for (int i = 0; i < Inputcards.size(); i++) {
			addCard(0, Inputcards.get(i));
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
		card.setOwnerIndex(ownerIndex);
		cards.add(card);
	}
	
	
	/**
	 * add a card into index position of the Deck
	 * @param the card that need to put in the deck
	 */
	public void addCard(int index, Model_Card card) {
		card.setOwnerIndex(ownerIndex);
		cards.add(0, card);
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
	
	
	/**
	 * return a certain attribute of the top cards based on Index
	 * @param attributeIndex
	 * @return the attribute that choose
	 */
	public Model_Attribute getTopCardAttribute(int attributeIndex) {
		return getTopCard().getCategory().getAttribute(attributeIndex);
	}
	
	public int getNumCards() {
		return cards.size();
	}
	
}
