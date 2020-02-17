package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import shared.Model_Card;
import shared.Model_CardCategory;

class testModel_Card {
	
	private Model_CardCategory category;
	private Model_Card card;
	
	@Test
	public void testGetName() {
		initial();
		assertEquals(card.getName(),"Master_Yi");
	}
	
	@Test
	public void testSetName() {
		initial();
		card.setName("Soraka");
		assertEquals(card.getName(),"Soraka");
	}
	
	@Test
	public void testGetOwnerIndex() {
		initial();
		assertEquals(card.getOwnerIndex(),-1);
	}
	
	@Test
	public void testSetOwnerIndex() {
		initial();
		card.setOwnerIndex(1);
		assertEquals(card.getOwnerIndex(),1);
	}
	
	@Test
	public void testGetCategory() {
		initial();
		String str = "\t> " + "Attack_Speed" + ": " + "5" + "\n"
				  		  + "\t> " + "Attack_Demage" + ": " + "14" + "\n"
				  		  + "\t> " + "Attack_Power" + ": " + "11" + "\n"
				  		  + "\t> " + "Armor" + ": " + "8" + "\n"
				  		  + "\t> " + "Magic_Resistance" + ": " + "5" + "\n";
		assertEquals(card.getCategory().toString(),str);
	}
	
	@Test
	public void testGetAttribute() {
		initial();
		String str = "\t> " + "Attack_Power" + ": " + "11";
		assertEquals(card.getAttribute(2).toString(),str);
	}
	
	@Test
	public void testGetHighestAttrIndex() {
		initial();
		assertEquals(card.getHighestAttrIndex(),1);
	}
	
	@Test
	public void testToStringNoArguments() {
		initial();
		String str = "\t> " + "Attack_Speed" + ": " + "5" + "\n"
						  + "\t> " + "Attack_Demage" + ": " + "14" + "\n"
						  + "\t> " + "Attack_Power" + ": " + "11" + "\n"
						  + "\t> " + "Armor" + ": " + "8" + "\n"
						  + "\t> " + "Magic_Resistance" + ": " + "5" + "\n";
		assertEquals(card.toString(),str);
	}
	
	@Test
	public void testToStringWithArguments() {
		initial();
		String str = "\t> " + "Attack_Speed" + ": " + "5" + "\n"
						  + "\t> " + "Attack_Demage" + ": " + "14" + "\n"
						  + "\t> " + "Attack_Power" + ": " + "11" + " <--\n"
						  + "\t> " + "Armor" + ": " + "8" + "\n"
						  + "\t> " + "Magic_Resistance" + ": " + "5" + "\n";
		assertEquals(card.toString(2),str);
	}
	
	@Test
	public void testLog() {
		initial();
		String str = "Master_Yi" + " " + 5 + " " + 14 + " " + 11 + " " + 8 + " " + 5 + "\n";
		assertEquals(card.log(),str);
	}
	
	public void initial() {
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
	}
}
