package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import shared.Model_Attribute;

public class testModel_Attribute {

	private Model_Attribute attribute = new Model_Attribute("Attack_Speed",5);
			
	@Test
	public void testGetName() {
		assertEquals("Attack_Speed", attribute.getName());
	}
	
	@Test
	public void testSetName() {
		attribute.setName("Speed");
		assertEquals("Speed", attribute.getName());
	}
	
	@Test
	public void testGetValue() {
		assertEquals(5, attribute.getValue());
	}
	
	@Test
	public void testSetValue() {
		attribute.setValue(60);
		assertEquals(60, attribute.getValue());
	}
	
	@Test
	public void testCompareTo() {
		Model_Attribute attribute1 = new Model_Attribute("Attack_Demage", 14);
		Model_Attribute attribute2 = new Model_Attribute("Magic_Resistance", 5);
		Model_Attribute attribute3 = new Model_Attribute("Attack_Demage", 3);
		assertEquals(attribute.compareTo(attribute1), -1);
		assertEquals(attribute.compareTo(attribute2), 0);
		assertEquals(attribute.compareTo(attribute3), 1);
	}
	
	@Test
	public void testToString() {
		String str = "\t> " + "Attack_Speed" + ": " + "5";
		assertEquals(attribute.toString(), str);
	}

}
