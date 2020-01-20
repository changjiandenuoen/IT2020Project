package commandline;

/**
 * This class represents the one of the category on the card
 */
public class Model_Attribute {
	
	private String name;	//attribute name
	private int value;	//attribute value
	
	
	//getter and setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Constructor: 
	 * @param name : the name of attributes / default value = 0
	 */
	public Model_Attribute(String name) {
		//TODO:
	}
	
	/**
	 * compare the value of the two attributes
	 * @return if bigger return 1, if equal return 0, if less return -1
	 */
	public int compareTo(Model_Attribute attribute) {
		//TODO:
		return -1;
	}
	
	@Override
	/**
	 * @return "> attribute name : attribute value"
	 */
	public String toString() {
		//TODO:
		return null;
		
	}
}
