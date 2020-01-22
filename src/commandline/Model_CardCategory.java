package commandline;

import java.util.ArrayList;
import java.util.List;

/**
 * the categories(all attributes including name and value) for every card
 */
public class Model_CardCategory {
<<<<<<< HEAD
	//the 5 attributes in category
	private Model_Attribute[] attributes;
	
	//getter and setter
	public Model_Attribute[] getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Model_Attribute[] attributes) {
		this.attributes = attributes;
	}
	
	
	/*
=======
	
	private Model_Attribute[] attributes; //the attributes in category
	
	
	/**
>>>>>>> 6cb5791285a45279c7f95abe64052e2273ca8212
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
<<<<<<< HEAD
	}	
	
	
=======
	}
	
	
	//Getters and setters
	public Model_Attribute getAttributes(int index) {
		return attributes[index];
	}
	public void setAttributes(Model_Attribute[] attributes) {
		this.attributes = attributes;
	}
	
>>>>>>> 6cb5791285a45279c7f95abe64052e2273ca8212
	/**
	 * Get the highest attribute value in this category
	 * @return the highest attributes, if there are several attributes, 
	 * then return first one
	 */
	public Model_Attribute getHighestAttribute() {
		int HighestValue = attributes[0].getValue();
		int HighestIndex = 0;
		for(int i = 1;i < attributes.length;i++, HighestIndex++) {
			if(HighestValue < attributes[i].getValue()) {
				HighestValue = attributes[i].getValue();
				}
		}
		return attributes[HighestIndex];
	}
	
<<<<<<< HEAD
	
=======
>>>>>>> 6cb5791285a45279c7f95abe64052e2273ca8212
	/**
	 * 
	 * @return number of attributes in this category
	 */
	public int numAttributes() {
		return attributes.length;
	}
	
<<<<<<< HEAD
	
	@Override
=======
>>>>>>> 6cb5791285a45279c7f95abe64052e2273ca8212
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
			str = str + attributes[i] + "/n";
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
				str += " <--/n";
			} else {
				str +=  "/n";
			}
		}
		
		return str;
	}
}
