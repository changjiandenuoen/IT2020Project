package commandline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;


//Database name :
//User name :
//password :

/**
 * all the operation related to the Database
 */
public class Model_Database {
	
	private int numOfGame;
	private int numAIWin;
	private int numHumWin;
	private double avgDraw;
	private int longestRound;
	
	//create references for SQL and DB
	Connection c;
	PreparedStatement stmt;
	ResultSet rs;

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

	/**
	 * Constructor : this class is for Database operations
	 */
	public Model_Database() {
		
		numOfGame = 0;
		numAIWin = 0;
		numHumWin = 0;
		avgDraw = 0;
		longestRound = 0;
		
		c = null;
		stmt = null;
		rs = null;
		
		initializeDB();
	}

	/**
	 * drop all tables and recreate the tables
	 */
	public void initializeDB() {
		
		connectToDataBase();
		dropAllTable();
		createAllTable();
		closeConnection();
	}
	
	/**
	 * write information into DB at the end of each game <br>
	 * after write in those data, update all the reference in this class based on DB
	 * 
	 * @param numOfDraw : total number of draw in this game
	 * @param indexOfWinner : the index of the winner in this game
	 * @param numOfRounds : total number of round in this game
	 * @param scoreList the : score of player index 0 to ?
	 */
	public void writeDataInDB(int numOfDraw, int indexOfWinner, int numOfRounds, int[] scoreList) {
		
		connectToDataBase();
		
		String winnerName;
		
		if(indexOfWinner == 0) {
			winnerName = "'You'";
		}else {
			winnerName = "'Player_" + indexOfWinner + "'";
		}
		
		//the sql need to insert to Game table
		String sql1 = String.format("INSERT INTO GAME (GAME_ID, NUM_OF_ROUNDS, NUM_OF_DRAW, GAME_WINNER) VALUES "
				+ "(%d, %d, %d, %s)", numOfGame, numOfRounds, numOfDraw, winnerName);
		
		//the sql need to insert into Score table
		String sql2 = String.format("INSERT INTO SCORE (PLAYER_NAME, GAME_ID, SCORE) VALUES ");
		
		for (int i = 0; i < scoreList.length; i++) {
			if(i == 0) {
				sql2 += String.format("(%s, %d, %d)", "'You'", numOfGame, scoreList[i]);
			}else{
				sql2 += String.format("(%s, %d, %d)", "'Player_" + i + "'", numOfGame, scoreList[i]);
			}
			
			if(i < scoreList.length - 1) {
				sql2 += ", ";
			}
			 
		}
		
		//Insert into Database
		executeSQL(sql1);
		executeSQL(sql2);
		
		//update all required information 
		StatisticUpdate();
		
		closeConnection();
	}
	
	/**
	 * Get the largest number of rounds played in a single game
	 * @return the longest num of round in the previous game
	 */
	public int getLongestRound() {
		return longestRound;
	}
	
	/**
	 * In order to avoid connect to the DB for every result <br>
	 * this method allow one-time connection to get all required information <br>
	 * after call this method, the reference in this class will be update based on DB 
	 */
	private void StatisticUpdate() {
		
		//get the Number of games played overall from database
		String sql1 = "SELECT COUNT(GAME_ID) AS NUM_OF_GAMES FROM GAME";
		
		//get the times that computer has won the game
		String sql2 = "SELECT COUNT(GAME_WINNER) AS NUM_AI_WIN FROM GAME WHERE GAME_WINNER <> 'You'";
		
		//get the times that human has won the game
		String sql3 = "SELECT COUNT(GAME_WINNER) AS NUM_HUM_WIN FROM GAME WHERE GAME_WINNER = 'You'";
		
		//get the average number of draws
		String sql4 = "SELECT AVG(NUM_OF_DRAW) AS AVG_DRAW FROM GAME";
		
		//get the longest round in all games
		String sql5 = "SELECT MAX(NUM_OF_ROUNDS) AS LONGEST_ROUND FROM GAME";
		
		numOfGame = ((Number) executeQuery(sql1, "NUM_OF_GAMES")).intValue();
		numAIWin = ((Number) executeQuery(sql2, "NUM_AI_WIN")).intValue();
		numHumWin = ((Number) executeQuery(sql3, "NUM_HUM_WIN")).intValue();
		avgDraw = ((Number) executeQuery(sql4, "AVG_DRAW")).doubleValue();
		longestRound = ((Number) executeQuery(sql5, "LONGEST_ROUND")).intValue();
		
	}
	
	/**
	 * Create Game, Score table for Database
	 */
	private void createAllTable() {

		//the sql to create game table
		String sql1 ="CREATE TABLE IF NOT EXISTS GAME ("
				+ "GAME_ID INT NOT NULL UNIQUE,"
			    + "NUM_OF_ROUNDS INT NOT NULL DEFAULT 0,"
			    + "NUM_OF_DRAW INT NOT NULL DEFAULT 0,"
			    + "GAME_WINNER VARCHAR(10),"
			    + "PRIMARY KEY (GAME_ID))";
		
		//the sql to create score table
		String sql2 ="CREATE TABLE IF NOT EXISTS SCORE ("
				+ "PLAYER_NAME VARCHAR(10) NOT NULL,"
				+ "GAME_ID INT NOT NULL,"
				+ "SCORE    INT NOT NULL,"
				+ "PRIMARY KEY(PLAYER_NAME, GAME_ID))";

		executeSQL(sql1);
		executeSQL(sql2);
	}
	
	/**
	 * Drop Game, Score table for Database
	 * 
	 */
	private void dropAllTable() {
		
		String sql1 = "DROP TABLE IF EXISTS SCORE";
		String sql2 = "DROP TABLE IF EXISTS GAME";
		
		executeSQL(sql1);
		executeSQL(sql2);
	}
	
	/**
	 * There are tons of code duplication for database connection <br>
	 * So combine them
	 * @return true if connection is successful, else return false <br>
	 * @NOTICE the closeConnection() method must be call if this method is called
	 */
	private boolean connectToDataBase() {
		
		try {
			//load DB driver and establish the connection to DB
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
		
		} catch (ClassNotFoundException e) {
			System.err.println("postgresdriver could not be loaded");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.err.println("Could not establish the connection to Database");
			e.printStackTrace();
		}
		
		if(c != null) {
			return true;
		}else {
			System.err.println("connection database failed!");
			return false;
		}
	}

	/**
	 * close the DB connection and set it to null
	 */
	private void closeConnection() {

		try {

			if(c != null) {
				c.close();
			}
			
		} catch (SQLException e) {
			System.out.println("issue on closing Connection");
			e.printStackTrace();
		
		} finally {
			c = null;
		}
	}
	
	/**
	 * Close resultSet and set it to null
	 */
	private void closeResultTable() {
		
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.err.println("issue on closing ResultSet");
			e.printStackTrace();
		
		} finally {
			rs = null;
		}
	}
	
	/**
	 * Close PreparedStatement and set it to null
	 */
	private void closeStatement() {
		
		try {
			if(stmt != null) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			System.err.println("issue on closing PreparedStatement");
			e.printStackTrace();
		} finally {
			stmt = null;
		}
	}
	
	/**
	 * execute DDL & DML SQL with database
	 * @param sql the sql String 
	 */
	private void executeSQL(String sql) {
		
			try {
				stmt = c.prepareStatement(sql);
				stmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Cannot execute SQL :" + sql);
				e.printStackTrace();
			} finally {
				closeStatement();
			}	
	}
	
	/**
	 * execute Query (selection) from database
	 * @param sql SQL query
	 * @param varName the of the Attribute that select
	 * @return the required integer data which read from database
	 */
	@SuppressWarnings("unchecked")
	private <E> E executeQuery(String sql, String varName) {
		
		E data = null;
		
		try {
			stmt = c.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				data =  (E) (rs.getObject(varName));
			}
			
		} catch (SQLException e) {
			System.err.println("Cannot execute Query :" + sql);
			e.printStackTrace();
			
		} finally {
			closeStatement();
			closeResultTable();
		}
		
		return data;
	}
	
	// Getters
	/**
	 * Get the total num of game played
	 * @return the num of game played
	 */
	public int getNumOfGame() {
		return numOfGame;
	}
	/**
	 * Get the number the computer has won
	 * @return the num of AI win in previous game
	 */
	public int getNumAIWin() {
		return numAIWin;
	}
	/**
	 * Get the number the human has won
	 * @return the num of human win in previous game
	 */
	public int getNumHumWin() {
		return numHumWin;
	}
	/**
	 * Get the average draw in the previous games
	 * @return the average num of draw in previous games
	 */
	public double getAvgDraw() {
		return avgDraw;
	}
	
}
