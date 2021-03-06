
package commandline;

import java.util.ArrayList;

import shared.Model;
import shared.Model_Card;
import shared.Model_Player;

public class View {
	
	private Model model;
	private Controller controller;
	
	private boolean youLost = false;
	
	
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
	 */
	public void startGame() {
		
		model.resetModel();
		model.startGame();
		
		System.out.println("\n\nGame Start");
		
		while(model.getGameStatus() == 0) {
			
			System.out.println("Round " + model.getRound());
			
			Model_Card yourCard = model.getPlayer(0).getDeck().getTopCard();
			
			int attributeIndex = model.getAIHostCurrAttr();
			
			ArrayList<Model_Card> desk = model.drawCard();
			System.out.println("Round " + model.getRound() + ": Players have drawn their cards");
			
			if(!model.getPlayer(0).isDead()) {
				
				System.out.println("You drew '" + yourCard.getName() + "':");
				System.out.print(yourCard);
				
				int numCards = model.getPlayer(0).getDeck().size();
				if(numCards == 1) {
					System.out.println("There is one card in your deck");
				} else {
					System.out.println("There are " + model.getPlayer(0).getDeck().size() + " cards in your deck");
				}
				
			} else {
				if(!youLost) {
					youLost = true;
					System.out.println("You have Lost!");
				}
			}
			
			if(model.getHostIndex() == 0) {
				System.out.println("It is your turn to select a category, the category are:");
				this.displayCategory();
				System.out.print("Enter the number for your attribute: ");
				
				attributeIndex = controller.getAttributeInput() - 1;
				model.setCurrAttributeIndex(attributeIndex);
			}
			
			Model_Card winningCard = model.battle(desk);
			
			System.out.print("Round " + model.getRound() + ": ");
			// check if it's a draw or not
			if(winningCard == null) {
				System.out.println("This round was a Draw, communal pile now has " + model.getCommunalPile().size() + " cards");
			} else {
				Model_Player winner = model.getPlayer(winningCard.getOwnerIndex());
				System.out.println("Player " + winner.getName() + " won this round");
				System.out.println("The winning card was '" + winningCard.getName() + "':");
				System.out.print(winningCard.toString(model.getCurrAttributeIndex()));
			}
			System.out.println("\n");
			
			this.printWhoIsWinner();
		}
	}
	
	public void printChoices() {
		
		System.out.println("Do you want to see past results or play a game?");
		System.out.println("\t1: Print Game Statistics");
		System.out.println("\t2: Play game");
		System.out.println("\t3: Quit");
		System.out.print("Enter the number for your selection: ");
		
		controller.getModeInput();
	}
	
	public void printGameStatistics() {
		
		System.out.println("\n\nGame Statistics:");
		System.out.println("Number of Games: " + model.getNumGames());
		System.out.println("Number of Human Wins: " + model.getNumHumanWins());
		System.out.println("Number of AI Wins: " + model.getNumAIWins());
		System.out.println(String.format("Average number of Draws: %.1f", model.getAverageDraws()));
		System.out.println("Longest Game: " + model.getLongestRoundNum());
		
		System.out.println("\n");
	}
	
	public void displayCategory() {
		
		for(int i = 0; i < model.category.numAttributes(); i++) {
			System.out.println(String.format("\t%d: %s", i+1, model.category.getAttribute(i).getName()));
		}
	}
	
	public void printWhoIsWinner() {
		
		Model_Player winner = model.whoIsWinner();
		
		if(winner != null) {
			System.out.println("Game End\n");
			System.out.println("The overall winner was " + winner.getName());
			System.out.println("Scores:");
			
			for(int i = 0; i < model.getNumPlayers(); i++) {
				Model_Player p = model.getPlayer(i);
				System.out.println("\t" + p.getName() + ": " + p.getScore());
			}
			System.out.println("\n");
		}
	}
	
}

