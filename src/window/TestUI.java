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
import javax.swing.table.DefaultTableModel;

import controller.ActionList;
import controller.Controller;
import controller.ShowController;

public class TestUI extends Window {

	protected ShowController showC;

	/**
	 * Create the frame.
	 */
	public TestUI(int userId, int previousBookId) {
		frame = new JFrame();
		frame.setTitle("Book MAP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(1000, 200, 714, 381);

		var getP = frame.getContentPane();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(10, 18, 17));
		this.userId = userId;
		this.bookId = previousBookId;

		Window testUI = this;
		showC = new ShowController(userId, bookId);
		actionList = new ActionList(this);
		controller = new Controller();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 70, 261, 96, 84, 147, 19, 0 };
		gbl_panel.rowHeights = new int[] { 54, 21, 83, 55, 36, 25, 43 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };

		panel.setLayout(gbl_panel);

		bookTitleLabel = new JLabel();
		bookTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		adjustableFontSize(userId, bookId);
		bookTitleLabel.setForeground(new Color(250, 250, 250));

		GridBagConstraints gbc_bookTitleLabel = new GridBagConstraints();
		gbc_bookTitleLabel.gridwidth = 2;
		gbc_bookTitleLabel.weighty = 1.0;
		gbc_bookTitleLabel.weightx = 1.0;
		gbc_bookTitleLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_bookTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bookTitleLabel.gridx = 0;
		gbc_bookTitleLabel.gridy = 0;
		panel.add(bookTitleLabel, gbc_bookTitleLabel);

		bookListButton = new JButton("詳細");
		bookListButton.setForeground(new Color(250, 250, 250));
		bookListButton.setBackground(new Color(48, 48, 48));
		bookListButton.setPreferredSize(new Dimension(65, 25));
		bookListButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actionList.bookListButtonAction(userId, testUI);
			}
		});

		GridBagConstraints gbc_bookListButton = new GridBagConstraints();
		gbc_bookListButton.anchor = GridBagConstraints.EAST;
		gbc_bookListButton.weightx = 1.0;
		gbc_bookListButton.weighty = 1.0;
		gbc_bookListButton.insets = new Insets(0, 0, 5, 5);
		gbc_bookListButton.gridx = 3;
		gbc_bookListButton.gridy = 0;
		panel.add(bookListButton, gbc_bookListButton);

		comboModel = controller.setBookList(userId);
		bookShelfCombo = new JComboBox<>(comboModel);
		bookShelfCombo.setForeground(new Color(250, 250, 250));
		bookShelfCombo.setBackground(new Color(48, 48, 48));
		bookShelfCombo.setSelectedIndex(-1);
		bookShelfCombo.setMinimumSize(new Dimension(130, 30));
		bookShelfCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String bookTitle = String.valueOf(bookShelfCombo.getSelectedItem());
				bookId = showC.getBookId(userId, bookTitle);
				updateText(userId, bookId);
			}
		});
		GridBagConstraints gbc_bookShelfCombo = new GridBagConstraints();
		gbc_bookShelfCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_bookShelfCombo.weighty = 1.0;
		gbc_bookShelfCombo.weightx = 1.0;
		gbc_bookShelfCombo.insets = new Insets(0, 10, 5, 5);
		gbc_bookShelfCombo.gridx = 4;
		gbc_bookShelfCombo.gridy = 0;
		panel.add(bookShelfCombo, gbc_bookShelfCombo);

		changeUI = new JButton("UI");
		changeUI.setForeground(new Color(250, 250, 250));
		changeUI.setBackground(new Color(48, 48, 48));
		changeUI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				Classic classic = new Classic(userId, bookId);
				classic.run();
			}

		});
		GridBagConstraints gbc_changeUI = new GridBagConstraints();
		gbc_changeUI.anchor = GridBagConstraints.EAST;
		gbc_changeUI.weighty = 1.0;
		gbc_changeUI.weightx = 1.0;
		gbc_changeUI.insets = new Insets(0, 10, 5, 5);
		gbc_changeUI.gridx = 3;
		gbc_changeUI.gridy = 1;
		panel.add(changeUI, gbc_changeUI);

		rPAnsLabel = new JLabel(controller.setRemainPageLabel(userId, bookId));
		rPAnsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		rPAnsLabel.setForeground(new Color(250, 250, 250));
		rPAnsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 38));
		GridBagConstraints gbc_rPAnsLabel = new GridBagConstraints();
		gbc_rPAnsLabel.weightx = 1.0;
		gbc_rPAnsLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_rPAnsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rPAnsLabel.gridx = 1;
		gbc_rPAnsLabel.gridy = 2;
		panel.add(rPAnsLabel, gbc_rPAnsLabel);

		getP.add(panel, BorderLayout.CENTER);

		progressModel = new DefaultTableModel();
		progressModel.addColumn("ページ数");
		progressModel.addColumn("日時");
		progressDataTable = new JTable(progressModel);
		progressModel = controller.reloadProgressModel(progressModel, userId, bookId);
		progressDataTable = controller.tableSettings(progressDataTable);

		//progressDataTable = new JTable(progressModel);
		scrollPane = new JScrollPane(progressDataTable);
		scrollPane.setPreferredSize(new Dimension(150, 140));
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		scrollPane.setBackground(new Color(48, 48, 48));
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.weightx = 1.0;
		gbc_scrollPane_1.gridheight = 2;
		gbc_scrollPane_1.insets = new Insets(0, 10, 5, 5);
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane_1);

		progressLabel = new JLabel(controller.setProgressLabelString(userId, bookId));
		progressLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 34));
		progressLabel.setForeground(new Color(48, 48, 48));
		GridBagConstraints gbc_progressLabel = new GridBagConstraints();
		gbc_progressLabel.weighty = 1.0;
		gbc_progressLabel.weightx = 1.0;
		gbc_progressLabel.gridheight = 1;
		gbc_progressLabel.insets = new Insets(0, 10, 5, 5);
		gbc_progressLabel.gridx = 4;
		gbc_progressLabel.gridy = 4;
		panel.add(progressLabel, gbc_progressLabel);

		progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
		progressBar.setValue(controller.setProgress(userId, bookId));
		progressBar.setBackground(new Color(48, 48, 48));
		progressBar.setForeground(new Color(250, 250, 250));
		progressBar.setFont(new Font("MS UI Gothic", Font.PLAIN, 36));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.weighty = 1.0;
		gbc_progressBar.weightx = 1.0;
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.gridheight = 4;
		gbc_progressBar.insets = new Insets(0, 10, 5, 5);
		gbc_progressBar.gridx = 4;
		gbc_progressBar.gridy = 2;
		panel.add(progressBar, gbc_progressBar);

		avgPAnsLabel = new JLabel(controller.setAvgPagesLabel(userId, bookId));
		avgPAnsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		avgPAnsLabel.setForeground(new Color(250, 250, 250));
		avgPAnsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		GridBagConstraints gbc_avgPAnsLabel = new GridBagConstraints();
		gbc_avgPAnsLabel.weightx = 1.0;
		gbc_avgPAnsLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_avgPAnsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_avgPAnsLabel.gridx = 1;
		gbc_avgPAnsLabel.gridy = 5;
		panel.add(avgPAnsLabel, gbc_avgPAnsLabel);

		inputTodayPages = new JTextField();
		inputTodayPages.setForeground(new Color(250, 250, 250));
		inputTodayPages.setBackground(new Color(48, 48, 48));
		inputTodayPages.setCaretColor(new Color(230, 253, 255));
		inputTodayPages.setHorizontalAlignment(SwingConstants.RIGHT);
		inputTodayPages.setColumns(10);
		allowOnlyNumbers(inputTodayPages);
		GridBagConstraints gbc_inputTodayPages = new GridBagConstraints();
		gbc_inputTodayPages.fill = GridBagConstraints.HORIZONTAL;
		gbc_inputTodayPages.weighty = 1.0;
		gbc_inputTodayPages.weightx = 1.0;
		gbc_inputTodayPages.insets = new Insets(0, 10, 5, 5);
		gbc_inputTodayPages.gridx = 2;
		gbc_inputTodayPages.gridy = 4;
		panel.add(inputTodayPages, gbc_inputTodayPages);

		inputButton = new JButton("入力");
		inputButton.setForeground(new Color(250, 250, 250));
		inputButton.setBackground(new Color(48, 48, 48));
		inputButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputTodayPages.getText() == null) {
					return;
				} else {
					todayProgress = Integer.valueOf(inputTodayPages.getText());
					showC.addRecentData(userId, bookId, todayProgress);
					inputTodayPages.setText(null);
				}
				updateText(userId, bookId);
			}

		});
		
		ActionListener[] inputButtonEvent = inputButton.getActionListeners(); // inputButtonのActionListenerの配列を入れる
		inputTodayPages.addActionListener(inputButtonEvent[0]);
		GridBagConstraints gbc_inputButton = new GridBagConstraints();
		gbc_inputButton.anchor = GridBagConstraints.EAST;
		gbc_inputButton.weighty = 1.0;
		gbc_inputButton.weightx = 1.0;
		gbc_inputButton.insets = new Insets(0, 0, 5, 5);
		gbc_inputButton.gridx = 3;
		gbc_inputButton.gridy = 4;
		panel.add(inputButton, gbc_inputButton);

		sumDaysAnsLabel = new JLabel("Total Days  " + controller.sumDays(userId, bookId) + "days");
		sumDaysAnsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		sumDaysAnsLabel.setForeground(new Color(250, 250, 250));
		sumDaysAnsLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 20));
		GridBagConstraints gbc_sumDaysAnsLabel = new GridBagConstraints();
		gbc_sumDaysAnsLabel.weightx = 1.0;
		gbc_sumDaysAnsLabel.fill = GridBagConstraints.BOTH;
		gbc_sumDaysAnsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sumDaysAnsLabel.gridx = 1;
		gbc_sumDaysAnsLabel.gridy = 4;
		panel.add(sumDaysAnsLabel, gbc_sumDaysAnsLabel);

		deleteButton = new JButton("削除");
		deleteButton.setBackground(new Color(48, 48, 48));
		deleteButton.setForeground(new Color(250, 250, 250));
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showC.deleteRecentData(userId, bookId);
				updateText(userId, bookId);
			}
		});
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.anchor = GridBagConstraints.EAST;
		gbc_deleteButton.weighty = 1.0;
		gbc_deleteButton.weightx = 1.0;
		gbc_deleteButton.insets = new Insets(0, 0, 5, 5);
		gbc_deleteButton.gridx = 3;
		gbc_deleteButton.gridy = 5;
		panel.add(deleteButton, gbc_deleteButton);
	}

	public void run() {
		this.frame.setVisible(true);
	}

	public void stop() {
		this.frame.setVisible(false);
	}

	public void adjustableFontSize(int userId, int bookId) {
		String title = controller.setBookTitle(userId, bookId);
		bookTitleLabel.setText(title);
		int variable = title.length() / 10;
		if (variable == 0) {
			bookTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 30));
		} else {
			int fontSize = 20 / variable + 10;
			bookTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, fontSize));
		}
	}

	public void updateText(int userId, int bookId) {
		adjustableFontSize(userId, bookId);
		rPAnsLabel.setText(controller.setRemainPageLabel(userId, bookId));
		avgPAnsLabel.setText(controller.setAvgPagesLabel(userId, bookId));
		sumDaysAnsLabel.setText(controller.sumDays(userId, bookId) + "日");
		progressBar.setValue(controller.setProgress(userId, bookId));
		progressLabel.setText(controller.setProgressLabelString(userId, bookId));
		progressModel = controller.reloadProgressModel(progressModel, userId, bookId);

	}

}
