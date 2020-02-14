package shared;


/**
 * represents each card
 */
public class Model_Card {
	
	private String name;
	
	private Model_CardCategory category;
	
	private int ownerIndex = -1;
	
	
	/**
	 * Constructor: Create a card by category
	 * @param attributes : the category that represents in deck file
	 */
	public Model_Card(String name, Model_CardCategory category) {
		this.name = name;
		this.category = category;
	}
	
	
	//Getter and setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOwnerIndex() {
		return ownerIndex;
	}
	public void setOwnerIndex(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}
	public Model_CardCategory getCategory() {
		return category;
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
	
	public String log() {
		
		String log = name;
		
		for(int i = 0; i < category.numAttributes(); i++) {
			log += " " + category.getAttribute(i).getValue();
		}
		
		return log + "\n";
	}
	
}
