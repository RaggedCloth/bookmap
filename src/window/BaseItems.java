package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import controller.ShowController;

public abstract class BaseItems extends JFrame implements ActionListener {
    int bookId;
    int userId;
    JFrame frame;
    List<String> bookList;
    JPanel panel;
    ManageBooks mBooks;
    JButton bookListButton;
    JButton inputButton;
    JButton deleteButton;
    JButton changeUI;
    JLabel bookTitleLabel;
    JLabel sumDaysAnsLabel;
    JLabel rPAnsLabel;
    JLabel avgPAnsLabel;
    JLabel progressLabel;
    DefaultTableModel progressModel;
    ShowController showC;
    JProgressBar progressBar;
    JTextField inputTodayPages;
    JScrollPane scrollPane;
    static int todayProgress;

    BaseItems() {
        frame = new JFrame();
        userId = 1;
        bookId = 1;
        bookList = new ArrayList<>();
        bookTitleLabel = new JLabel();
        changeUI = new JButton("UI");
        panel = new JPanel();
        inputButton = new JButton();
        deleteButton = new JButton();
        sumDaysAnsLabel = new JLabel();
        rPAnsLabel = new JLabel();
        avgPAnsLabel = new JLabel();
        progressLabel = new JLabel();
        progressModel = new DefaultTableModel();
        showC = new ShowController();
        progressBar = new JProgressBar();
        inputTodayPages = new JTextField();
        scrollPane = new JScrollPane();
    }

    abstract void run();

    abstract void stop();

    public void makeFrame(int userId, int previousBookId) {
        this.frame = new JFrame();
        this.frame.setTitle("Book MAP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(1100, 100, 788, 381);

        this.bookId = previousBookId;
        ShowController showC = new ShowController(userId, bookId);

        changeUI.setText("UI");
        bookList = showC.getBookList(userId, bookId);
        DefaultComboBoxModel<Object> comboModel = new DefaultComboBoxModel<>();
        for (String bl : bookList) {
            comboModel.addElement(bl);
        }
        bookTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        String title = showC.getBookTitle(userId, bookId);

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

    public void updateText(int userId, int bookId) {
        adjustableFontSize(showC.getBookTitle(userId, bookId));
        rPAnsLabel.setText("<html><nobr><u>　" + showC.currentPages(userId, bookId)
                + "P / " + showC.totalPages(bookId) + "P</u></nobr></html>");
        avgPAnsLabel.setText(showC.average(userId, bookId) + "P");
        sumDaysAnsLabel.setText(showC.sumDays(userId, bookId) + "日");
        progressModel.setRowCount(0);
        List<String[]> tableData = new ArrayList<>();
        tableData = showC.progressData(userId, bookId);
        for (String[] row : tableData) {
            progressModel.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        // if (cmd.equals("UI"))
        stop();
        Horizontal horizontal = new Horizontal(userId, bookId);
        horizontal.run();
    }
}
