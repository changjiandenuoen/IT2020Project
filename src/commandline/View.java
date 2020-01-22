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
	 * 2. choose to print past game result (get user input)(controller)
	 * 3. game start, round one
	 * 4. players draw card, if human alive print what you draw and how many left in your deck
	 * 5. print category info
	 * 6. get user input (Attribute)
	 * 7. model.battle
	 * 8. show round result: winner, if draw then show cards number in pile, winning card info
	 * 9. check model gameStatus: if end to 10, else round++ to 4
	 * 10. print end game result: winner, scores
	 * 11. to 1
	 */
	public void startGame() {
		
		
		
		
		// 2.
		this.printGameStatistics();
		
		System.out.println("Game Start");
		
		// 3.
		while(model.getGameStatus() != 1) {
			
			model.startRound();
			
			System.out.println("Round " + model.getRound());
			System.out.println("Round " + model.getRound() + ": Players have drawn their cards");
			
			if(!model.isHumanDead()) {
				System.out.println(String.format("You drew \"%s\":", model.getPlayerTopCard(0).getName()));
				System.out.println(model.getPlayerTopCard(0));
				System.out.println("There are " + model.getHost().getDeck().size() + " cards in your deck");
				System.out.println("It is your turn to select a category, the category are:");
				this.displayCategory();
				System.out.println("Enter the number for your attribute: ");
				
				model.setCurrAttributeIndex(controller.getUserInput());
				
				Model_Card winningCard = model.battle();
				
				// when it's a draw
				if(winningCard == null) {
					System.out.println("Round " + model.getRound() + 
							": This round was a Draw, common pile now has " + model.getCommunalPile().size() + " cards");
					
				} else {
					
				}
				
				
				
				
				
				model.resetModel();
			}
		}
		
		// 4.
		
		
	}
	
	/**
	 * print Number of Games: Number of Human Wins: Number of AI Wins: 
   	 * 		 Average number of Draws: Longest Game: 
	 */
	public void printGameStatistics() {
		
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("\t1: Print Game Statistics");
		System.out.println("\t2: Play game");
		System.out.println("Enter the number for your selection: ");
		
		controller.getUserInput();
		
		System.out.println("\n\n");
	}
	
	public void displayCategory() {
		
		for(int i = 0; i < model.category.numAttributes(); i++) {
			System.out.println(String.format("\t%d: %s\n", i+1, model.category.getAttribute(i).getName()));
		}
	}
	
}
