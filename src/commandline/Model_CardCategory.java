package commandline;

import java.util.ArrayList;
import java.util.List;

/**
 * the categories(all 5 attribute including name and value) for every card
 */
public class Model_CardCategory {
	private Model_Attribute[] attributes; //the 5 attributes in category
	//getter and setter
	public Model_Attribute[] getAttributes() {
		return attributes;
	}
	public void setAttributes(Model_Attribute[] attributes) {
		this.attributes = attributes;
	}
	/*
	 * Constructor
	 * @param infoArray : the name of attributes in every card
	 */
	public Model_CardCategory(ArrayList<String> attributesNames,List<Integer> list) {
		int count = attributesNames.size();
		attributes = new Model_Attribute[count];
		for (int i = 0; i < count; i++) {
			attributes[i] = new Model_Attribute(attributesNames.get(i), list.get(i));
		}
	}	
	/**
	 * Get the highest attribute value in this rd
	 * @return the highest attributes, if there are several attributes, 
	 * then return first one
	 */
	public Model_Attribute getHighestAttribute() {
		int HighestValue = attributes[0].getValue();
		int HighestIndex = 0;
		for(int i = 1;i < attributes.length;i++) {
			if(HighestValue < attributes[i].getValue()) {
				HighestValue = attributes[i].getValue();
				}
		}
		return attributes[HighestIndex];
	}
	/**
	 * 
	 * @param index
	 * @return a specific attribute
	 */
	public Model_Attribute getAttribute(int index) {
		return attributes[index];
	}
	@Override
	/**
	 * @return all attributes' String as form:
	 * 
	 *  	"> attribute1 name : attribute2 value"
	 *  	"> attribute2 name : attribute1 value"
	 *  	....
	 */
	public String toString() {
		
		String str = "";
		for (int i = 0; i < attributes.length; i++) {
			str = str + attributes[i].toString() + "/n";
		}
		return str;
	}
	
	
	/**
	 * 
	 * @param choice : the choice that we input
	 * @return all attributes' String as form :
	 * 		"> attribute2 name : attribute1 value"
	 * 
	 * the attributes that selected was added "<--"
	 */
	public String toString(int choice) {
		String str = "";
		for (int i = 0; i < attributes.length; i++) {
			str += attributes[i].toString() + "/n";
			if(i == choice) {
				str += " <--";
			}
		}
		return str;
	}
}
