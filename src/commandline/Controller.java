package commandline;
import java.util.Scanner;
public class Controller {

	private Model model;
	private View view;
	
	private Scanner s;
	
	/**
	 * Constructor
	 * @param	model
	 */
	public Controller(Model model) {
		this.model = model;
		
		s = new Scanner(System.in);
	}
	
	
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * Get the choice of 1 or 2
	 * 1: Print Game Statistics
	 * 2: Play game
	 */
	public int getModeInput() {

		int choice = s.nextInt();
		
		if(choice == 1) {
			view.printGameStatistics();
		} else if(choice == 2) {
			view.startGame();
		}
		
		return choice;
	}
	
	/**
	 * Get the number of which attribute the player choose to compare.
	 * @return the number that the player chooses of the attribute.
	 */
	public int getAttributeInput() {

		int attributeNum = s.nextInt();
		
		return attributeNum;
	}
	
}
