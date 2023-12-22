package controller;

import javax.swing.JComboBox;

import dao.ProgressDAO;
import window.ManageBooks;
import window.Window;

public class ActionList {
	private Window window;
	private ManageBooks mBooks;
	private ProgressDAO pdao;
	private Controller controller;
	
	public ActionList(Window window) {
		this.window = window;
	}

	public void bookListButtonAction(int userId, Window window) {
		if (mBooks == null) {
			mBooks = new ManageBooks(userId, window);
		}
		mBooks.run();
		mBooks.updateFrame(userId);
	}
	
	public void bookShelfComboAction(int userId, int bookId) {
		pdao = new ProgressDAO();
		controller = new Controller();
		String bookTitle;
		JComboBox<String> bookShelfCombo = new JComboBox<>(controller.setBookList(userId));
		bookTitle = String.valueOf(bookShelfCombo.getSelectedItem());
		bookId = pdao.searchBookId(userId, bookTitle);
		
	}

//	public void reloadBookShelfCombo(int userId) {
//		window.bookShelfCombo.setModel(controller.setBookList(userId));
//	}

}
