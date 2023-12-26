package controller;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import dao.BookShelfDAO;
import dao.BooksDAO;
import dao.ProgressDAO;
import dto.BooksDTO;
import dto.ProgressDTO;
import entity.ProgressBean;

public class Controller extends DefaultComboBoxModel<String>  {
	protected DefaultTableModel progressModel;
	DefaultComboBoxModel<String> comboModel;
    private ProgressDTO pdto;
    private ProgressDAO pdao;
    private ProgressBean pb;
    private BooksDTO bdto;
    private BooksDAO bdao;
    private BookShelfDAO bsdao;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM/dd");
    private Timestamp timestampFromProgress;
    List<String> bookList;
    JComboBox<String> bookShelfCombo; 
    
	public Controller() {
	    this.pdao = new ProgressDAO();
	    this.bdao = new BooksDAO();
		progressModel = new DefaultTableModel();
	}


	
	public String setBookTitle(int userId, int bookId) {
        return bdao.selectBookTitle(userId, bookId);
    }
	
	public DefaultComboBoxModel<String> setBookList(int userId) {
		comboModel = new DefaultComboBoxModel<>();
		this.bookList = new ArrayList<String>();
		bookList.removeAll(this.bookList);
		
        this.bookList = bdao.searchBookList(userId);
        
		for (String bl : this.bookList) {
			comboModel.addElement(bl);
		}
		int newIndexStart = 0; // 新しい要素の最初のインデックス
	    int newIndexEnd = bookList.size() - 1; // 新しい要素の最後のインデックス

	    // fireIntervalAdded を呼び出して変更を通知
	    fireIntervalAdded(this, newIndexStart, newIndexEnd);
		return comboModel;
	}
	
	public void setBookShelfCombo(DefaultComboBoxModel<String> comboModel) {
		 bookShelfCombo.setModel(comboModel);
	}
	
	public String setRemainPageLabel (int userId, int bookId) {
		int currentPages = pdao.selectCurrentPages(userId, bookId);
		int totalPages = bdao.selectTotalPages(bookId);
		
		if (currentPages > totalPages) {
			return  "Finish!!";
		}
		return "<html><nobr><u>　" 
				+ pdao.selectCurrentPages(userId, bookId) + "P / "
				+ bdao.selectTotalPages(bookId)
				+ "P</u></nobr></html>";
	}
	public DefaultTableModel reloadProgressModel(DefaultTableModel progressModel, int userId, int bookId) {
		progressModel.setRowCount(0);
		
		List<String[]> tableData = new ArrayList<>();
		tableData = pdao.getProgressData(userId, bookId);
		for (String[] row : tableData) {
			progressModel.addRow(row);
		}
		return progressModel;
	}
	public JTable tableSettings(JTable progressDataTable) {
		progressDataTable.setBackground(new Color(48, 48, 48));
		progressDataTable.setForeground(new Color(230, 230, 230));
		
		TableColumn pages = progressDataTable.getColumnModel().getColumn(0);
		TableColumn days = progressDataTable.getColumnModel().getColumn(1);
		pages.setPreferredWidth(30);
		days.setPreferredWidth(60);
		
		progressDataTable = setRightRenderer(progressDataTable);
		return progressDataTable;
	}
	public JTable setRightRenderer(JTable progressDataTable) {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		progressDataTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		progressDataTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		return progressDataTable;
	}
	/*
	 * 進捗率
     * （例外を防ぐため先に100を掛けて％を出す）
	 */
	public int setProgress(int userId, int bookId) {
		int currentPages = pdao.selectCurrentPages(userId, bookId);
		int totalPages = bdao.selectTotalPages(bookId);
		
		if (totalPages == 0) {
			return 0;
		} else if (currentPages > totalPages) {
			return 100;
		} else {
			return (currentPages * 100) / totalPages;// 現在の達成率
		}
	}
	public String setProgressLabelString(int userId, int bookId) {
		return "<html><center><nobr>" + setProgress(userId, bookId) + "％</nobr></center></html>";
	}

	public int sumDays(int userId, int bookId) {
        int sum = 0;
        String previousDate = null;
        String nowDate = null;
		pdto =  pdao.select(userId, bookId);
		
        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            timestampFromProgress = pb.getCreatedAt();
            nowDate = dateFormat.format(timestampFromProgress);
            if (pb.getTodayProgress() == 0) {
                continue;
            }
            if (nowDate.equals(previousDate)) {
                continue;
            }
            previousDate = dateFormat.format(timestampFromProgress);
            sum++;
        }
        return sum;
    }
	public String setAvgPagesLabel(int userId, int bookId) {
		int sumPage = 0;
		pdto =  pdao.select(userId, bookId);
        for (int i = 0; i < pdto.size(); i++) {
            pb = pdto.get(i);
            sumPage += pb.getTodayProgress();
        }
        if (sumDays(userId, bookId) == 0) {
            return String.valueOf(sumPage) + "P / day";
        } else {
        	return String.valueOf(sumPage / sumDays(userId, bookId)) + "P / day";
        }
	}
}
