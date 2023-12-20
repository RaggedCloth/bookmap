package controller;

import javax.swing.JComboBox;

import dao.ProgressDAO;
import window.ManageBooks;
import window.TestUI;

public class ActionList {
	private TestUI testUI;
	private ManageBooks mBooks;
	private ProgressDAO pdao;
	private Controller controller;
	
	public ActionList(TestUI testUI) {
		this.testUI = testUI;
	}

	public void bookListButtonAction(int userId) {
		if (mBooks == null) {
			mBooks = new ManageBooks(userId);
		}
		mBooks.run();
		mBooks.updateFrame(userId);
	}
	
	public void bookShelfComboAction(int userId, int bookId) {
		pdao = new ProgressDAO();
		controller = new Controller();
		String bookTitle;
		JComboBox<Object> bookShelfCombo = new JComboBox<>(controller.setBookList(userId, bookId));
		bookTitle = String.valueOf(bookShelfCombo.getSelectedItem());
		bookId = pdao.searchBookId(userId, bookTitle);
		testUI.updateText(userId, bookId);
	}


}
