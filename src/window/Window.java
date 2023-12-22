package window;

import javax.swing.JComboBox;

import controller.ActionList;
import controller.Controller;

public abstract class Window {
	public Controller controller = new Controller();
	ActionList actionList;
	protected JComboBox<String> bookShelfCombo = new JComboBox<String>();
	
	public JComboBox<String> getBookShelfCombo() {
		return bookShelfCombo;
	}

	public void setBookShelfCombo(JComboBox<String> bookShelfCombo) {
		this.bookShelfCombo = bookShelfCombo;
	}

	abstract protected void run();

	abstract protected void stop();
	
	abstract public void updateText(int userId, int bookId);
	
	abstract protected void updateBookShlefCombo();
	

}
