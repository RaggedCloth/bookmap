package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.ShowController;

public class Horizontal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
	private int bookId;
	private List<String> bookList;
	private ManageBooks mBooks;
	private final JButton bookListButton;
	private final JLabel bookTitleLabel;
	private final JLabel sumDaysAnsLabel;
	private final JLabel rPAnsLabel;
	private final JLabel avgPAnsLabel;
	private final DefaultTableModel progressModel;
	private final ShowController showC;
	private JProgressBar progressBar;
	private JButton btnNewButton;
	private JTextField textField;
	private JButton btnNewButton_1;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public Horizontal(int userId, int previousBookId) {
		frame = new JFrame();
		frame.setTitle("Book MAP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(1100, 100, 788, 381);
		//
		// contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// setContentPane(contentPane);
		// contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		var getP = frame.getContentPane();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(48, 48, 48));

		bookId = previousBookId;
		showC = new ShowController(userId, bookId);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 338, 91, 39, 135, 38, 0 };
		gbl_panel.rowHeights = new int[] { 54, 21, 83, 55, 38, 25, 43 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
		panel.setLayout(gbl_panel);
		bookList = showC.getBookList(userId, bookId);
		DefaultComboBoxModel<Object> comboModel = new DefaultComboBoxModel<>();
		for (String bl : bookList) {
			comboModel.addElement(bl);
		}

		bookTitleLabel = new JLabel();
		bookTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		String title = showC.getBookTitle(userId, bookId);
		adjustableFontSize(title);
		// bookTitleLabel.setText(/*showC.getBookTitle(userId, bookId)*/"スッキリJava入門");
		bookTitleLabel.setForeground(new Color(51, 153, 255));
		GridBagConstraints gbc_bookTitleLabel = new GridBagConstraints();
		gbc_bookTitleLabel.weighty = 1.0;
		gbc_bookTitleLabel.weightx = 1.0;
		gbc_bookTitleLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_bookTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bookTitleLabel.gridx = 0;
		gbc_bookTitleLabel.gridy = 0;
		panel.add(bookTitleLabel, gbc_bookTitleLabel);

		bookListButton = new JButton("詳細");
		bookListButton.setForeground(new Color(51, 153, 255));
		bookListButton.setBackground(new Color(48, 48, 48));
		bookListButton.setPreferredSize(new Dimension(65, 25));
		bookListButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mBooks == null) {
					mBooks = new ManageBooks(userId);
				}
				mBooks.run();
				mBooks.updateFrame(userId);
			}
		});

		GridBagConstraints gbc_bookListButton = new GridBagConstraints();
		gbc_bookListButton.anchor = GridBagConstraints.EAST;
		gbc_bookListButton.weightx = 1.0;
		gbc_bookListButton.weighty = 1.0;
		gbc_bookListButton.insets = new Insets(0, 0, 5, 5);
		gbc_bookListButton.gridx = 2;
		gbc_bookListButton.gridy = 0;
		panel.add(bookListButton, gbc_bookListButton);

		JComboBox<Object> bookShelfCombo = new JComboBox<>(comboModel);
		bookShelfCombo.setForeground(new Color(51, 153, 255));
		bookShelfCombo.setBackground(new Color(48, 48, 48));
		bookShelfCombo.setSelectedIndex(-1);
		bookShelfCombo.setMinimumSize(new Dimension(130, 30));
		bookShelfCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String bookTitle;
				bookTitle = String.valueOf(bookShelfCombo.getSelectedItem());
				bookId = showC.getBookId(userId, bookTitle);
				updateText(userId, bookId);
			}
		});
		GridBagConstraints gbc_bookShelfCombo = new GridBagConstraints();
		gbc_bookShelfCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_bookShelfCombo.weighty = 1.0;
		gbc_bookShelfCombo.weightx = 1.0;
		gbc_bookShelfCombo.insets = new Insets(0, 10, 5, 5);
		gbc_bookShelfCombo.gridx = 3;
		gbc_bookShelfCombo.gridy = 0;
		panel.add(bookShelfCombo, gbc_bookShelfCombo);

		rPAnsLabel = new JLabel("<html><nobr><u>　" + showC.currentPages(userId, bookId)
				+ "P / " + showC.totalPages(bookId) + "P</u></nobr></html>");
		rPAnsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		rPAnsLabel.setForeground(new Color(51, 153, 255));
		rPAnsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 38));
		rPAnsLabel.setBackground(UIManager.getColor("InternalFrame.inactiveBorderColor"));
		GridBagConstraints gbc_rPAnsLabel = new GridBagConstraints();
		gbc_rPAnsLabel.weightx = 1.0;
		gbc_rPAnsLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_rPAnsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rPAnsLabel.gridx = 0;
		gbc_rPAnsLabel.gridy = 2;
		panel.add(rPAnsLabel, gbc_rPAnsLabel);

		progressModel = new DefaultTableModel();
		progressModel.addColumn("ページ数");
		progressModel.addColumn("日時");
		List<String[]> tableData = new ArrayList<>();
		tableData = showC.progressData(userId, bookId);
		for (String[] row : tableData) {
			progressModel.addRow(row);
		}
		GridBagConstraints gbc_progressDataTable = new GridBagConstraints();
		gbc_progressDataTable.weighty = 1.0;
		gbc_progressDataTable.weightx = 1.0;
		gbc_progressDataTable.fill = GridBagConstraints.NONE;
		gbc_progressDataTable.weighty = 1.0;
		gbc_progressDataTable.weighty = 1.0;
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		getP.add(panel, BorderLayout.CENTER);
		JTable progressDataTable = new JTable(progressModel);
		TableColumn pages = progressDataTable.getColumnModel().getColumn(0);
		TableColumn days = progressDataTable.getColumnModel().getColumn(1);
		pages.setPreferredWidth(30);
		days.setPreferredWidth(60);
		progressDataTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		progressDataTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

		scrollPane = new JScrollPane(progressDataTable);
		scrollPane.setPreferredSize(new Dimension(150, 140));
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		progressDataTable.setBackground(new Color(48, 48, 48));
		progressDataTable.setForeground(new Color(51, 153, 255));

		// JScrollPaneの背景色を黒に設定
		scrollPane.setBackground(new Color(48, 48, 48));

		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.weightx = 1.0;
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.insets = new Insets(0, 10, 5, 5);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane_1);

		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(48, 48, 48));
		progressBar.setValue(30);
		progressBar.setStringPainted(true);
		progressBar.setOrientation(SwingConstants.VERTICAL);
		progressBar.setForeground(new Color(51, 153, 255));
		progressBar.setFont(new Font("MS UI Gothic", Font.PLAIN, 36));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.weighty = 1.0;
		gbc_progressBar.weightx = 1.0;
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.gridheight = 4;
		gbc_progressBar.insets = new Insets(0, 10, 5, 5);
		gbc_progressBar.gridx = 3;
		gbc_progressBar.gridy = 2;
		panel.add(progressBar, gbc_progressBar);

		avgPAnsLabel = new JLabel(showC.average(userId, bookId) + "P / day");
		avgPAnsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		avgPAnsLabel.setForeground(new Color(51, 153, 255));
		avgPAnsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		GridBagConstraints gbc_avgPAnsLabel = new GridBagConstraints();
		gbc_avgPAnsLabel.weightx = 1.0;
		gbc_avgPAnsLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_avgPAnsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_avgPAnsLabel.gridx = 0;
		gbc_avgPAnsLabel.gridy = 4;
		panel.add(avgPAnsLabel, gbc_avgPAnsLabel);

		textField = new JTextField();
		textField.setForeground(new Color(51, 153, 255));
		textField.setBackground(new Color(48, 48, 48));
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.weighty = 1.0;
		gbc_textField.weightx = 1.0;
		gbc_textField.insets = new Insets(0, 10, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 4;
		panel.add(textField, gbc_textField);

		btnNewButton_1 = new JButton("入力");
		btnNewButton_1.setForeground(new Color(51, 153, 255));
		btnNewButton_1.setBackground(new Color(48, 48, 48));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.weighty = 1.0;
		gbc_btnNewButton_1.weightx = 1.0;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 4;
		panel.add(btnNewButton_1, gbc_btnNewButton_1);

		sumDaysAnsLabel = new JLabel(showC.sumDays(userId, bookId) + "日");
		sumDaysAnsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sumDaysAnsLabel.setForeground(new Color(51, 153, 255));
		sumDaysAnsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		GridBagConstraints gbc_sumDaysAnsLabel = new GridBagConstraints();
		gbc_sumDaysAnsLabel.weightx = 1.0;
		gbc_sumDaysAnsLabel.fill = GridBagConstraints.BOTH;
		gbc_sumDaysAnsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sumDaysAnsLabel.gridx = 0;
		gbc_sumDaysAnsLabel.gridy = 5;
		panel.add(sumDaysAnsLabel, gbc_sumDaysAnsLabel);

		btnNewButton = new JButton("削除");
		btnNewButton.setBackground(new Color(48, 48, 48));
		btnNewButton.setForeground(new Color(51, 153, 255));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.weighty = 1.0;
		gbc_btnNewButton.weightx = 1.0;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		panel.add(btnNewButton, gbc_btnNewButton);
	}

	public void adjustableFontSize(String title) {
		bookTitleLabel.setText(title);
		int variable = title.length() / 10;
		if (variable == 0) {
			bookTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 30));
		} else {
			int fontSize = 20 / variable + 10;
			bookTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, fontSize));
		}
	}

	public void run() {
		this.frame.setVisible(true);
	}

	public void updateText(int userId, int bookId) {
		adjustableFontSize(showC.getBookTitle(userId, bookId));
		sumDaysAnsLabel.setText(showC.sumDays(userId, bookId) + "日");
		rPAnsLabel.setText("<html><nobr><u>　" + showC.currentPages(userId, bookId)
							+ "P / " + showC.totalPages(bookId) + "P</u></nobr></html>");
		avgPAnsLabel.setText(showC.average(userId, bookId) + "P");
		progressModel.setRowCount(0);
		List<String[]> tableData = new ArrayList<>();
		tableData = showC.progressData(userId, bookId);
		for (String[] row : tableData) {
			progressModel.addRow(row);
		}
	}
}
