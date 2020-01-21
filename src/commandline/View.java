package commandline;

public class View {
	
	private Model model;
	private Controller controller;
	
	
	/**
	 * Constructor
	 * @param	model
	 * @param	controller
	 */
	public View(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
	}
	
	
	/**
	 * Called when starting a game
	 * TODO: 
	 * 1. reset model
	 * 2. choose to print past game result (get user input)
	 * 3. game start, round one
	 * 4. players draw card, if human alive print what you draw and how many left in your deck
	 * 5. print category info
	 * 6. get user input (Attribute)
	 * 7. model.battle
	 * 8. show round result: winner, if draw then show cards number in pile, winning card info
	 * 9. 
	 */
	public void startGame() {
		
	}
	
	public void update() {
		
	}
	
	
}
