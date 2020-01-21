package commandline;

import java.util.ArrayList;
import java.util.List;

/**
 * the categories(all attributes including name and value) for every card
 */
public class Model_CardCategory {
	
	private Model_Attribute[] attributes; //the attributes in category
	
	
	/**
	 * Constructor
	 * @param	attributesNames : the name of each attribute
	 * @param	valuesList : the value of each attribute
	 */
	public Model_CardCategory(ArrayList<String> attributesNames, List<Integer> valuesList) {
		int count = attributesNames.size();
		attributes = new Model_Attribute[count];
		for (int i = 0; i < count; i++) {
			attributes[i] = new Model_Attribute(attributesNames.get(i), valuesList.get(i));
		}
	}
	
	
	//Getters and setters
	public Model_Attribute[] getAttributes() {
		return attributes;
	}
	public void setAttributes(Model_Attribute[] attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * Get the highest attribute value in this category
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
	
	/**
	 * @return all attributes' String as form:
	 * 
	 *  	"> attribute1 name : attribute2 value"
	 *  	"> attribute2 name : attribute1 value"
	 *  	....
	 */
	@Override
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
