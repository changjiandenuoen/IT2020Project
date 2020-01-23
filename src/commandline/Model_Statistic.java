package commandline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

//Datebase name :
//User name :
//password :

/**
 * all the statistic in Database
 */
public class Model_Statistic {
	
	/* after completion of the game
	 * the user should automatically write the following information:
	 * 		1. How many draws were there? (NumOfDraw)
	 * 		2. who won the game? (GameWinner)
	 * 		3. How many rounds were played in the game? (NumOfRounds)
	 * 		4. How many rounds did each player win? 
	 * 			(NumWinForP1)(NumWinForP2)(NumWinForP3)(NumWinForP4)(NumWinForP5)
	 * 
	 */
	
	/*
	 * If the game is not in progress, the user to connect to the database
	 * can get information about previous games. which should include:
	 * 
	 * 		1. Number of games played overall
	 * 		2. How many times the computer has won
	 * 		3. how many times the human has won
	 * 		4. The average number of draws
	 * 		5. The largest number of rounds played in a single game
	 */
	public Model_Statistic() {
		
	}
	
	public void initializeDB() {
		Connection c = null;
		Statement stmt = null;
		
		
	}
	
	/**
	 * write information into DB at the end of each game
	 * 
	 * @param numOfDraw : total number of draw in this game
	 * @param indexOfWinner : the index of the winner in this game
	 * @param numOfRounds : total number of round in this game
	 * @param scoreList the : score of player index 0 to ?
	 */
	public void writeDataInDB(int numOfDraw, int indexOfWinner, int numOfRounds, int[] scoreList) {
		
		//create connection and statement
		Connection c = null;
		PreparedStatement stmt = null;
		
		try {
			//load DB driver and establish the connection to DB
			Class.forName("org.postgres.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "username", "password");
		
		} catch (ClassNotFoundException e) {
			System.out.println("postgresdriver could not be loaded");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("Could not establish the connection to Database");
			e.printStackTrace();
		}
		
		//This part should be delete if there is no problem for connecting
		if(c != null) {
			System.out.println("connection database successfully");
		}
		
		//now connecting to the DB
		
	}
	
	
	/**
	 * Get the total num of game played
	 * @return the num of game played
	 */
	public int getNumOfGame() {
		//TODO:
		return 0;
	}
	
	
	/**
	 * Get the number the computer has won
	 * @return the num of AI win in previous game
	 */
	public int getNumCompWin() {
		//TODO:
		return 0;
	}
	
	
	/**
	 * Get the number the human has won
	 * @return the num of human win in previous game
	 */
	public int getNumHumWin() {
		//TODO:
		return 0;
	}
	
	
	/**
	 * Get the average draw in the previous games
	 * @return the average num of draw in previous games
	 */
	public int getAvgDraw() {
		//TODO:
		return 0;
	}
	
	
	/**
	 * Get the largest number of rounds played in a single game
	 * @return the longest num of round in the previous game
	 */
	public int getLongestRound() {
		//TODO:
		return 0;
	}
	
}
