package commandline;
//import java.io.File;

import java.io.IOException;
import java.util.logging.*;


	
	public class View_Testlog {
		public static void main(String[] args) {
			Logger log = Logger.getLogger("testlog");
			log.setLevel(Level.ALL);
			FileHandler fileHandler = null;
			try {
				fileHandler = new FileHandler("testlog.log");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new LogFormatter());
			log.addHandler(fileHandler);
			log.info("Test");
		}
	}
