package commandline;


/**
 * represents each card
 */
public class Model_Card {
	
	String name;
	
	Model_CardCategory category;
	
	Model_Player owner;
	
	
	/**
	 * Constructor: Create a card by category
	 * @param attributes : the category that represents in deck file
	 */
	public Model_Card(String name, Model_CardCategory category) {
		this.name = name;
		this.category = category;
		this.owner = null;
	}
	
	
	//Getter and setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(Model_CardCategory category) {
		this.category = category;
	}
	public Model_Player getOwner() {
		return owner;
	}
	public void setOwner(Model_Player owner) {
		this.owner = owner;
	}
	
	/**
	 * 
	 * @param index
	 * @return a specific Attribute
	 */
	public Model_Attribute getAttribute(int index) {
		return category.getAttribute(index);
	}

	/**
	 * 
	 * @return the highest attribute index
	 */
	public int getHighestAttrIndex() {
		return category.getHighestAttrIndex();
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
		return category.toString();
	}
	
	
	/**
	 * 
	 * @param choice : the choice that we input
	 * @return all attributes/ the attributes that selected was added "<--"
	 */
	public String toString(int choice) {
		return category.toString(choice);
	}
	
}
