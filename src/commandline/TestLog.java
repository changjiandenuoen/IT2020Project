package commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class TestLog {
	
	private Model model;
	private File logFile;
	
	private boolean logging;
	
	
	public TestLog(Model model){
		this.model = model;
		
		logFile = new File("./toptrumps.log");
		logging = false;
	}
	
	
	/**
	 * The method can be used to log the argument deck
	 * @param deck
	 * @return the deck logs
	 */
	private String deckLog(Model_Deck deck) {

		String log = model.category.log();
		
		for(int i = 0;i < deck.size(); i++) {
			log += deck.getCard(i).log();
		}
		
		return log;
	}
	
	/**
	 * The method is used log the main deck
	 * @return the main deck log
	 */
	public void wholeDeckLog() {
		String log = "The cards in the main deck are: " + "\n" + this.deckLog(model.getDeck()) + "\n";
		writeInLog(log);
	}
	
	/**
	 * The method is used to get different players' deck contents and differ them.
	 * @return the players' decks log
	 */
	public void playerCardLog() {

		String log = "Your cards are: " + "\n" + this.deckLog(model.getPlayer(0).getDeck()) + "\n";

		for(int i = 1; i < model.numPlayers(); i++) {
			log += "AI Player " + i + "'s cards are: " + "\n" + this.deckLog(model.getPlayer(i).getDeck()) + "\n";
		}

		writeInLog(log);
	}
	
	public void commualPileLog() {
		String log = "The cards in the communal pile: " + "\n" + this.deckLog(model.getCommunalPile()) + "\n";
		writeInLog(log);
	}
	
	public void currentDeskLog(Model_Deck desk) {
		if(!logging) return;
		
		String currentDeskCards = "";
		
		for(int i = 0; i < desk.size(); i++) {
			currentDeskCards += desk.getTopCard().getName() + "\n" + desk.getTopCard() + "\n";
		}
		
		String log = "The cards of this round are: \n" + currentDeskCards + "\n";
		writeInLog(log);
	}
	
	public void selectedAttributeLog(int attrIndex) {
		if(!logging) return;
		
		String log = "The chosen attribute is: " + model.category.getAttribute(attrIndex) + "\n";
		writeInLog(log);
	}
	
	public void winnerLog() {
		if(!logging) return;
		
		String log = "The winner is: " + model.getWinner().getName() + "." + "\n";
		writeInLog(log);
	}
	
	private void writeInLog(String log) {
		if(!logging) return;
		
		try {
			FileWriter fw = new FileWriter(logFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(log);
			bw.close();
			fw.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Getter
	public void setLogging(boolean logging) {
		this.logging = logging;
	}
	
}