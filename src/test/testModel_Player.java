package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import shared.Model_Card;
import shared.Model_CardCategory;
import shared.Model_Deck;
import shared.Model_Player;

class testModel_Player {

	private Model_Card card;
	private Model_CardCategory category;
	
	@Test
	public void testGetName() {
		Model_Player player = new Model_Player("Player",1);
		assertEquals(player.getName(),"Player");
	}
	
	@Test
	public void testGetDeck() {
		Model_Player player = new Model_Player("Player",1);
		assertEquals(player.getDeck().getOwnerIndex(),1);
	}
	
	@Test
	public void testSetDeck() {
		Model_Player player = new Model_Player("Player",1);
		Model_Deck deck = new Model_Deck();
		player.setDeck(deck);
		assertEquals(player.getDeck().getOwnerIndex(),-1);
	}
	
	@Test
	public void testGetScore() {
		Model_Player player = new Model_Player("Player",1);
		assertEquals(player.getScore(),0);
	}
	
	@Test
	public void testGetIndex() {
		Model_Player player = new Model_Player("Player",1);
		assertEquals(player.getIndex(),1);
	}
	
	@Test
	public void testScorePlusOne() {
		Model_Player player = new Model_Player("Player",1);
		player.scorePlusOne();
		assertEquals(player.getScore(),1);
	}
	
	@Test
	public void testSetScoreToZero() {
		Model_Player player = new Model_Player("Player",1);
		for(int i = 0;i < 100;i++) {
			player.scorePlusOne();
		}
		assertEquals(player.getScore(),100);
		player.setScoreToZero();
		assertEquals(player.getScore(),0);
	}
	
	@Test
	public void testIsDead() {
		Model_Player player = new Model_Player("Player",1);
		
		ArrayList<String> attributesNames = new ArrayList<String>();
		List<Integer> valuesList	= new ArrayList<Integer>();
		attributesNames.add("Attack_Speed");
		valuesList.add(5);
		attributesNames.add("Attack_Demage");
		valuesList.add(14);
		attributesNames.add("Attack_Power");
		valuesList.add(11);
		attributesNames.add("Armor");
		valuesList.add(8);
		attributesNames.add("Magic_Resistance");
		valuesList.add(5);
				
		category = new Model_CardCategory(attributesNames,valuesList);		
		card = new Model_Card("Master_Yi",category);
		
		player.getDeck().addCard(card);
		assertEquals(player.getDeck().size(),1);
		
		player.getDeck().removeAllCards();
		
		assertTrue(player.isDead());
	}
	
	@Test
	public void testIsAI() {
		Model_Player player1 = new Model_Player("Player",0);
		Model_Player player2 = new Model_Player("Player",1);
		
		assertFalse(player1.isAI());
		assertTrue(player2.isAI());
	}

}
