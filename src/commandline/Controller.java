package commandline;
import java.util.Scanner;
public class Controller {

	private Model model;
	private View view;
	
	/**
	 * Constructor
	 * @param	model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * Get the choice of 1 or 2
	 * 1: Print Game Statistics
	 * 2: Play game
	 */
	public void getModeInput() {
		Scanner s = new Scanner(System.in);
		int choice = s.nextInt();
		if(choice == 1) {
			view.printGameStatistics();
		}
		if(choice == 2) {
			view.startGame();
		}
	}
	
	/**
	 * Get the number of which attribute the player choose to compare.
	 * @return the number that the player chooses of the attribute.
	 */
	public int getAttributeInput() {
		Scanner s = new Scanner(System.in);
		int attributeNum = s.nextInt();
		return attributeNum;
	}
}
