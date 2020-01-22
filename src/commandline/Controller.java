package commandline;

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
	
	public void getUserInput() {
		
	}
	
}
