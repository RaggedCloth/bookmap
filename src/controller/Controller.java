package controller;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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

public class Controller {
	protected DefaultTableModel progressModel;
	DefaultComboBoxModel<Object> comboModel;
    private ProgressDTO pdto;
    private ProgressDAO pdao;
    private ProgressBean pb;
    private BooksDTO bdto;
    private BooksDAO bdao;
    private BookShelfDAO bsdao;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM/dd");
    private Timestamp timestampFromProgress;
    List<String> bookList;
	public Controller() {
	    this.pdao = new ProgressDAO();
	    this.bdao = new BooksDAO();
		progressModel = new DefaultTableModel();
	}

//	abstract protected void run();
//
//	abstract protected void stop();

	public String setBookTitle(int userId, int bookId) {
        return bdao.selectBookTitle(userId, bookId);
    }
	public DefaultComboBoxModel<Object> setBookList(int userId, int bookId) {
		comboModel = new DefaultComboBoxModel<>();
		bookList = new ArrayList<String>();
		
		bookList.removeAll(bookList);
        bookList = bdao.searchBookList(userId);
		for (String bl : bookList) {
			comboModel.addElement(bl);
		}
		
		return comboModel;
	}
	
	public String setRemainPageLabel (int userId, int bookId) {
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
		progressDataTable.setForeground(new Color(51, 153, 255));
		
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
		return (pdao.selectCurrentPages(userId, bookId) * 100) / bdao.selectTotalPages(bookId); // 現在の達成率
	}
	public String setProgressLabelString(int userId, int bookId) {
		return "<html><center><nobr>達成率" + setProgress(userId, bookId) + "％</nobr></center></html>";
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
