package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import shared.Model_Card;
import shared.Model_CardCategory;
import shared.Model_Deck;

class testModel_Deck {
	
	private Model_Deck deck;
	private Model_CardCategory category1,category2,category3;
	private Model_Card card1,card2,card3;
	
	@Test
	public void testGetOwnerIndex() {
		initial();
		assertEquals(deck.getOwnerIndex(),1);
	}
	
	@Test
	public void testSetOwner() {
		initial();
		deck.setOwner(2);
		assertEquals(deck.getOwnerIndex(),2);
	}
	
	@Test
	public void testAddCard() {
		initial();
		deck.addCard(card1);
		assertEquals(card1.getOwnerIndex(),1);

		Model_Deck anotherDeck = new Model_Deck();
		anotherDeck.setOwner(2);
		anotherDeck.addCard(card2);
		assertEquals(card2.getOwnerIndex(),2);
	}
	
	@Test
	public void testSize() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.size(),3);
	}
	
	@Test
	public void testGetNumCards() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.getNumCards(),3);
	}
	
	@Test
	public void testRemoveAllCards() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		deck.removeAllCards();
		assertEquals(deck.getNumCards(),0);
	}
	
	@Test
	public void testGetCard() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.getCard(1),card2);
	}
	
	@Test
	public void testGetCards() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.getCards(0, 3)[0],card1);
		assertEquals(deck.getCards(0, 3)[1],card2);
		assertEquals(deck.getCards(0, 3)[2],card3);
	}
	
	@Test
	public void testGetTopCard() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.getTopCard(),card3);
	}
	
	@Test
	public void testGetAllCards() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.getAllCards()[0],card1);
		assertEquals(deck.getAllCards()[1],card2);
		assertEquals(deck.getAllCards()[2],card3);
	}
	
	@Test
	public void testRemoveTopCard() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.removeTopCard(),card3);
		assertEquals(deck.getNumCards(),2);
	}
	
	@Test
	public void testAddToBottom() {
		initial();
		deck.addCard(card1);
		Model_Card[] cardList1 = new Model_Card[2];
		cardList1[0] = card2;
		cardList1[1] = card3;
		deck.addToBottom(cardList1);
		assertEquals(deck.getTopCard(),card1);
		assertEquals(deck.getNumCards(),3);
		
		ArrayList<Model_Card> cardList2 = new ArrayList<Model_Card>();
		cardList2.add(card2);
		cardList2.add(card3);
		deck.addToBottom(cardList2);
		assertEquals(deck.getTopCard(),card1);
		assertEquals(deck.getNumCards(),5);
	}
	
	@Test
	public void testGetTopCardAttribute() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(card3);
		assertEquals(deck.getTopCardAttribute(2).getName(),"Attack_Power");
		assertEquals(deck.getTopCardAttribute(2).getValue(),5);
	}
	
	@Test
	public void testAddCardWithIndex() {
		initial();
		deck.addCard(card1);
		deck.addCard(card2);
		deck.addCard(1,card3);
		assertEquals(deck.getTopCard(),card2);
		}
		
	public void initial() {
		ArrayList<String> attributesNames1 = new ArrayList<String>();
		List<Integer> valuesList1	= new ArrayList<Integer>();
		attributesNames1.add("Attack_Speed");
		valuesList1.add(5);
		attributesNames1.add("Attack_Demage");
		valuesList1.add(14);
		attributesNames1.add("Attack_Power");
		valuesList1.add(11);
		attributesNames1.add("Armor");
		valuesList1.add(8);
		attributesNames1.add("Magic_Resistance");
		valuesList1.add(5);
				
		category1 = new Model_CardCategory(attributesNames1,valuesList1);		
		card1 = new Model_Card("Master_Yi",category1);

		
		ArrayList<String> attributesNames2 = new ArrayList<String>();
		List<Integer> valuesList2 = new ArrayList<Integer>();
		attributesNames2.add("Attack_Speed");
		valuesList2.add(8);
		attributesNames2.add("Attack_Demage");
		valuesList2.add(11);
		attributesNames2.add("Attack_Power");
		valuesList2.add(14);
		attributesNames2.add("Armor");
		valuesList2.add(14);
		attributesNames2.add("Magic_Resistance");
		valuesList2.add(5);
		
		category2 = new Model_CardCategory(attributesNames2,valuesList2);		
		card2 = new Model_Card("Soraka",category2);
		 
		ArrayList<String> attributesNames3 = new ArrayList<String>();
		List<Integer> valuesList3 = new ArrayList<Integer>();
		attributesNames3.add("Attack_Speed");
		valuesList3.add(5);
		attributesNames3.add("Attack_Demage");
		valuesList3.add(20);
		attributesNames3.add("Attack_Power");
		valuesList3.add(5);
		attributesNames3.add("Armor");
		valuesList3.add(14);
		attributesNames3.add("Magic_Resistance");
		valuesList3.add(0);
		
		category3 = new Model_CardCategory(attributesNames3,valuesList3);		
		card3 = new Model_Card("Ashe",category3);
		
		deck = new Model_Deck(1);
	}
}
