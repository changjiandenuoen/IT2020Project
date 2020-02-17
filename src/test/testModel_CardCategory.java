package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import shared.Model_Attribute;
import shared.Model_CardCategory;

public class testModel_CardCategory {
	
	private ArrayList<String> attributesNames = new ArrayList<String>();
	private List<Integer> valuesList = new ArrayList<Integer>();
	private Model_CardCategory cardCategory;
		
	@Test
	public void testGetAttribute() {
		
		initial();
				
		assertEquals(cardCategory.getAttribute(0).getName(), "Attack_Speed");
		assertEquals(cardCategory.getAttribute(0).getValue(), 5);
		assertEquals(cardCategory.getAttribute(1).getName(), "Attack_Demage");
		assertEquals(cardCategory.getAttribute(1).getValue(), 14);
		assertEquals(cardCategory.getAttribute(2).getName(), "Attack_Power");
		assertEquals(cardCategory.getAttribute(2).getValue(), 11);
		assertEquals(cardCategory.getAttribute(3).getName(), "Armor");
		assertEquals(cardCategory.getAttribute(3).getValue(), 8);
		assertEquals(cardCategory.getAttribute(4).getName(), "Magic_Resistance");
		assertEquals(cardCategory.getAttribute(4).getValue(), 5);
	}
	
	@Test
	public void testSetAttributes() {
		
		Model_Attribute[] attributes = new Model_Attribute[5];
		attributes[0] = new Model_Attribute("Attack_Speed",5);
		attributes[1] = new Model_Attribute("Attack_Demage",14);
		attributes[2] = new Model_Attribute("Attack_Power",11);
		attributes[3] = new Model_Attribute("Armor",8);
		attributes[4] = new Model_Attribute("Magic_Resistance",5);
		
		cardCategory = new Model_CardCategory(this.attributesNames,this.valuesList);
		cardCategory.setAttributes(attributes);
		
		assertEquals(cardCategory.getAttribute(0).getName(), "Attack_Speed");
		assertEquals(cardCategory.getAttribute(0).getValue(), 5);
		assertEquals(cardCategory.getAttribute(1).getName(), "Attack_Demage");
		assertEquals(cardCategory.getAttribute(1).getValue(), 14);
		assertEquals(cardCategory.getAttribute(2).getName(), "Attack_Power");
		assertEquals(cardCategory.getAttribute(2).getValue(), 11);
		assertEquals(cardCategory.getAttribute(3).getName(), "Armor");
		assertEquals(cardCategory.getAttribute(3).getValue(), 8);
		assertEquals(cardCategory.getAttribute(4).getName(), "Magic_Resistance");
		assertEquals(cardCategory.getAttribute(4).getValue(), 5);
	}

	@Test
	public void testGetAttributes() {
		initial();
		assertEquals(cardCategory.getAttributes()[0].getName(), "Attack_Speed");
		assertEquals(cardCategory.getAttributes()[0].getValue(), 5);
		assertEquals(cardCategory.getAttributes()[1].getName(), "Attack_Demage");
		assertEquals(cardCategory.getAttributes()[1].getValue(), 14);
		assertEquals(cardCategory.getAttributes()[2].getName(), "Attack_Power");
		assertEquals(cardCategory.getAttributes()[2].getValue(), 11);
		assertEquals(cardCategory.getAttributes()[3].getName(), "Armor");
		assertEquals(cardCategory.getAttributes()[3].getValue(), 8);
		assertEquals(cardCategory.getAttributes()[4].getName(), "Magic_Resistance");
		assertEquals(cardCategory.getAttributes()[4].getValue(), 5);
	}
	
	@Test
	public void testGetHighestAttrIndex() {
		initial();
		assertEquals(cardCategory.getHighestAttrIndex(), 1);
	}
	
	@Test
	public void testNumAttributes() {
		initial();
		assertEquals(cardCategory.numAttributes(), 5);
	}
	
	@Test
	public void testToStringNoArguments() {
		initial();
		String str = "\t> " + "Attack_Speed" + ": " + "5" + "\n"
						  + "\t> " + "Attack_Demage" + ": " + "14" + "\n"
						  + "\t> " + "Attack_Power" + ": " + "11" + "\n"
						  + "\t> " + "Armor" + ": " + "8" + "\n"
						  + "\t> " + "Magic_Resistance" + ": " + "5" + "\n";
		assertEquals(cardCategory.toString(), str);
	}
	
	@Test
	public void testToStringWithArguments() {
		initial();
		String str = "\t> " + "Attack_Speed" + ": " + "5" + "\n"
				  		  + "\t> " + "Attack_Demage" + ": " + "14" + "\n"
				  		  + "\t> " + "Attack_Power" + ": " + "11" + " <--\n"
				  		  + "\t> " + "Armor" + ": " + "8" + "\n"
				  		  + "\t> " + "Magic_Resistance" + ": " + "5" + "\n";
		assertEquals(cardCategory.toString(2), str);
	}
	
	@Test
	public void TestLog() {
		initial();
		String str = "Description" + " " + "Attack_Speed" + " " + "Attack_Demage" + " " + "Attack_Power" + " " + "Armor" + " " +  "Magic_Resistance" + "\n";
		assertEquals(cardCategory.log(), str);
	}
	
	public void initial() {
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
		cardCategory = new Model_CardCategory(attributesNames,valuesList);
	}
}
