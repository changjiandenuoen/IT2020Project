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
	public Model_Attribute getAttribute(int index) {
		return attributes[index];
	}
	public void setAttributes(Model_Attribute[] attributes) {
		this.attributes = attributes;
	}

	/**
	 * Get the highest attribute index in this category
	 * @return the highest attribute index, if there are several,
	 * then return first one
	 */
	public int getHighestAttrIndex() {

		int index = 0;
		for(int i = 1; i < attributes.length; i++) {
			if(attributes[i].getValue() > attributes[index].getValue()) {
				index = i;
			}
		}
		
		return index;
	}
	
	/**
	 * 
	 * @return number of attributes in this category
	 */
	public int numAttributes() {
		return attributes.length;
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
			str = str + attributes[i] + "\n";
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
			str += attributes[i];
			if(i == choice) {
				str += " <--\n";
			} else {
				str +=  "\n";
			}
		}
		
		return str;
	}
	
	public String log() {
		
		String log = "Description";
		
		for (int i = 0; i < attributes.length; i++) {
			log += " " + attributes[i].getName();
		}
		
		return log + "\n";
	}
}
