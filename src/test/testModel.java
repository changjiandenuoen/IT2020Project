package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import shared.Model;


public class testModel {
	
	private Model model;
		
	@Test
	public void testResetModel() {
		initial();
		model.resetModel();
		assertEquals(model.getGameStatus(),0);
		assertEquals(model.getRound(),0);
		assertEquals(model.getCurrAttributeIndex(),0);
		assertEquals(model.getWinnerIndex(),-1);
	}
		
	@Test
	public void testDrawCard() {
		initial();
		model.startGame();
		assertEquals(model.drawCard().size(),5);
		model.getPlayer(1).getDeck().removeAllCards();
		assertEquals(model.drawCard().size(),4);
	}
		
	@Test
	public void testGetGameStatus() {
		initial();
		assertEquals(model.getGameStatus(),0);
	}
	
	@Test
	public void testQuit() {
		initial();
		model.quit();
		assertEquals(model.getGameStatus(),-1);
	}
	
	@Test
	public void testSetGameStatus() {
		initial();
		model.setGameStatus(1);
		assertEquals(model.getGameStatus(),1);
	}
	
	@Test
	public void testGetRound() {
		initial();
		assertEquals(model.getRound(),0);
	}
	
	@Test
	public void testGetCurrAttributeIndex() {
		initial();
		assertEquals(model.getCurrAttributeIndex(),0);
	}
	
	@Test
	public void testSetCurrAttributeIndex() {
		initial();
		model.setCurrAttributeIndex(2);
		assertEquals(model.getCurrAttributeIndex(),2);
	}
	
	@Test
	public void testGetHostIndex() {
		initial();
		assertEquals(model.getHostIndex(),0);
	}
	
	@Test
	public void testSetHostIndex() {
		initial();
		model.setHostIndex(2);
		assertEquals(model.getHostIndex(),2);
	}
	
	@Test
	public void testGetWinnerIndex() {
		initial();
		assertEquals(model.getWinnerIndex(),-1);
	}
	
	@Test
	public void testGetNumPlayers() {
		initial();
		assertEquals(model.getNumPlayers(),5);
	}
	
	@Test
	public void testSetPlayers() {
		initial();
		model.setPlayers(5);
		assertEquals(model.getNumPlayers(),5);
	}
	
	@Test
	public void testGetPlayer() {
		initial();
		model.setPlayers(5);
		assertEquals(model.getPlayer(0).getName(),"You");
		assertTrue(model.getPlayer(1).isAI());
		assertTrue(model.getPlayer(2).isAI());
		assertTrue(model.getPlayer(3).isAI());
		assertTrue(model.getPlayer(4).isAI());
	}
	
	@Test
	public void testGetHost() {
		initial();
		assertEquals(model.getHost(),model.getPlayer(0));
	}
	
	public void initial() {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		File jsonFile = new File("./TopTrumps.json");
		try {
			jsonNode = objectMapper.readValue(jsonFile, JsonNode.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model = new Model(jsonNode.get("deckFile").asText(),4);
	}
}
