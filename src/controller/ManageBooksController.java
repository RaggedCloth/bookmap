package controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;

import window.ManageBooks;

public class ManageBooksController  {
	ManageBooks mBooks;
	ShowController showC = new ShowController();

	public ManageBooksController(ManageBooks mBooks) {
		this.mBooks = mBooks;
	}

	public void updatedProcess(TableModelEvent e) {
		// 選択されたセルを特定
		int sortedRow = e.getFirstRow();
		int originalRow = mBooks.getBookListTable().convertRowIndexToModel(sortedRow);
		int column = e.getColumn();
		String columnName = mBooks.getBooksModel().getColumnName(column);

		// 用意していたモデルの複製と、変更されたモデルの値を比較
		Object editedDataObject = mBooks.getBooksModel().getValueAt(originalRow, column);
		Object oldDataObject = mBooks.getCopyOfBooksModel().getValueAt(originalRow, column);

		//originalRowからbookIdを取得するメソッド
		int bookId = Integer.parseInt(mBooks.getBooksModel().getValueAt(originalRow, 4).toString());

		//editBookDataのoriginalRowの引数をbookIdに変える
		if (editedDataObject instanceof String && !editedDataObject.equals(oldDataObject)) {
			String editedData = (String) editedDataObject;
			String updatedMessage = showC.editBookData(bookId, columnName, editedData);
			mBooks.displayUpdatedMessage(updatedMessage);
		}
	}

	public void setTraversalOrder(List<Component> order, JFrame frame) {
		frame.setFocusTraversalPolicy(new FocusTraversalPolicy() {

			@Override
			public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
				int index = (order.indexOf(aComponent) + 1) % order.size();
				return order.get(index);
			}

			@Override
			public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
				int index = order.indexOf(aComponent) - 1;
				if (index < 0) {
					index = order.size() - 1;
				}
				return order.get(index);
			}

			@Override
			public Component getFirstComponent(Container focusCycleRoot) {
				return order.get(0);
			}

			@Override
			public Component getLastComponent(Container focusCycleRoot) {
				return order.get(order.size() - 1);
			}

			@Override
			public Component getDefaultComponent(Container focusCycleRoot) {
				return getFirstComponent(focusCycleRoot);
			}

		});
	}
}
