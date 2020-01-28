package commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

	public class TestLog {
		private Model model;
		
		public TestLog(File file, Model model){
			this.model = model;
		}
		
		//The method can be used to log both the beginning status of the card deck and the status after shuffled.
		//The way to differ them is that when using the update method in model, the argument of update method is different.
		private String wholeDeckLog(Model_Deck deck) {
			//Initialize String allCardLog.
			String allCardLog = "";
			String oneCardLog;
			//The time of for-loop is the number of all cards.
			for(int i = 0;i < deck.size(); i++) {
				//String oneCardLog is the content of one card.
				oneCardLog = deck.getCard(i).getName() + "\n" +  deck.getCard(i).toString();
				//Summarize all the oneCardLogs.
				allCardLog = allCardLog + "\n" + oneCardLog;
			}
			return allCardLog;
		}
		
		//The method is used to get different players' deck contents and differ them.
		private String playerCardLog() {
			String humanPlayerCardLog = "";
			String AIPlayerCardLog = "";
			for(int i = 0;i < model.numPlayers();i++) {
				if(i == 0) {
					humanPlayerCardLog = "Your cards are: " + "\n" + this.wholeDeckLog(model.getPlayer(0).getDeck() ) + "\n";
				}
				else {
					AIPlayerCardLog = AIPlayerCardLog + "AI Player" + i + "'s cards are: " + "\n" + this.wholeDeckLog(model.getPlayer(i).getDeck()) + "\n";
				}
			}
			return humanPlayerCardLog + AIPlayerCardLog;
		}
		
		private String commualPileLog() {
			return "These cards are in the communal pile: " + "\n" + this.wholeDeckLog(model.getCommunalPile()) + "\n";
		}
		
		private String currentDeskLog() {
			String currentDeskCards = "";
			for(int i = 0;i < model.numPlayers();i++) {
				currentDeskCards = model.getPlayer(i).getDeck().getTopCard().getName() + "\n" + model.getPlayer(i).getDeck().getTopCard().getCategory().toString();
			}
			return "The cards of this round are: " + currentDeskCards + "\n";
		}
		
		private String selectedAttributeLog() {
			return "The chosen attribute is: " +model.getDeck().getTopCardAttribute(model.getCurrAttributeIndex()).getName() + "." + "\n"
		+ "And the value of the attribute is: " + model.getDeck().getTopCardAttribute(model.getCurrAttributeIndex()).getValue() + "." + "\n";
		}
		
		private String winnerLog() {
			return "The winner is: " + model.whoIsWinner().getName() + "." + "\n";
		}
		
		public String updateLog(String logType) {
			switch(logType) {
				case "wholeDeck": return wholeDeckLog(model.getDeck());
				case "shuffledDeck": return wholeDeckLog(model.getDeck());
				case "playersDeck": return playerCardLog();
				case "commualPile": return commualPileLog();
				case "currentDesk": return currentDeskLog();
				case "selectedAttribute": return selectedAttributeLog();
				case "playerRemainDeck": return playerCardLog();
				case "winner": return winnerLog();
			}
			return "";
		}
		
		public void writeInLog(File file, String logType) {
			try {
				FileWriter fw = new FileWriter(file,true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(updateLog(logType));
				bw.close();
				fw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}