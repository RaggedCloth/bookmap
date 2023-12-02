package window;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controller.ShowController;

public class ManageBooks {

    private final JFrame manageFrame;
    private final DefaultTableModel booksModel;
    private final JScrollPane booksScrollPane;
    private final JTable bookListTable;
    private final JButton addBookButton;
    private final JButton deleteBookButton;
    private final JLabel titleLabel;
    private final JLabel authorLabel;
    private final JLabel genreLabel;
    private final JLabel totalPagesLabel;
    private final JLabel errorMessageLabel;
    private final JTextField inputTitle;
    private final JTextField inputAuthor;
    private final JTextField inputGenre;
    private final JTextField inputTotalPages;
    private String addTitle;
    private String addAuthor;
    private String addGenre;
    private String errorMessage;
    private int addTotalPages;
    
    public ManageBooks(int userId) {
        /*
         * Frame
         */
        this.manageFrame = new JFrame();
        manageFrame.setTitle("Books");
        manageFrame.setBounds(900, 200, 500, 350);

        /*
         * Panel
         */
        Container getBooksPanel = manageFrame.getContentPane();
        JPanel bPanel = new JPanel();
        SpringLayout sLayout = new SpringLayout();
        bPanel.setLayout(sLayout);

        ShowController showC = new ShowController();

        /*
         * Table
         */
        booksModel = new DefaultTableModel();
        booksModel.addColumn("タイトル");
        booksModel.addColumn("著者");
        booksModel.addColumn("ジャンル");
        booksModel.addColumn("ページ数");

        // Modelにデータを格納
        List<String[]> booksData = new ArrayList<>();
        booksData = showC.getBookTable(userId);
        for (String[] bRow : booksData) {
            booksModel.addRow(bRow);
        }

        // ModelをTableに入れる
        bookListTable = new JTable(booksModel);
        bookListTable.setAutoCreateRowSorter(true);

        // TableをScrollPaneに入れる
        booksScrollPane = new JScrollPane(bookListTable);
        booksScrollPane.setPreferredSize(new Dimension(400, 100));
        sLayout.putConstraint(SpringLayout.NORTH, booksScrollPane, 30, SpringLayout.NORTH, bPanel);
        sLayout.putConstraint(SpringLayout.WEST, booksScrollPane, 30, SpringLayout.WEST, bPanel);
        sLayout.putConstraint(SpringLayout.EAST, booksScrollPane, -30, SpringLayout.EAST, bPanel);
        bPanel.add(booksScrollPane);

        /*
         * Button
         */
        addBookButton = new JButton("追加");
        addBookButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addTitle = inputTitle.getText();
                addAuthor = inputAuthor.getText();
                addGenre = inputGenre.getText();
                String totalPagesText = inputTotalPages.getText();
                if (!totalPagesText.isEmpty()) {
                    try {
                        addTotalPages = Integer.parseInt(totalPagesText);
                    } catch (NumberFormatException ne) {
                        ne.printStackTrace();
                        System.out.println("数字を入力してください。");
                    }
                }
                String result = showC.addBook(userId, addTitle, addAuthor, addGenre, addTotalPages);
                List<String> booksData = new ArrayList<>();
                
                
                System.out.println(result);
            }
        });
        sLayout.putConstraint(SpringLayout.SOUTH, addBookButton, 50, SpringLayout.SOUTH, booksScrollPane);
        sLayout.putConstraint(SpringLayout.EAST, addBookButton, -30, SpringLayout.EAST, bPanel);
        bPanel.add(addBookButton);

        deleteBookButton = new JButton("削除");
        deleteBookButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 本当に削除しますか？のポップアップ
            }
        });
        sLayout.putConstraint(SpringLayout.SOUTH, deleteBookButton, 34, SpringLayout.SOUTH, addBookButton);
        sLayout.putConstraint(SpringLayout.EAST, deleteBookButton, -30, SpringLayout.EAST, bPanel);
        bPanel.add(deleteBookButton);

        /*
         * Label
         */
        titleLabel = new JLabel("タイトル");
        sLayout.putConstraint(SpringLayout.SOUTH, titleLabel, 46, SpringLayout.SOUTH, booksScrollPane);
        sLayout.putConstraint(SpringLayout.WEST, titleLabel, 30, SpringLayout.WEST, bPanel);
        bPanel.add(titleLabel);

        authorLabel = new JLabel("著者");
        sLayout.putConstraint(SpringLayout.SOUTH, authorLabel, 29, SpringLayout.SOUTH, titleLabel);
        sLayout.putConstraint(SpringLayout.WEST, authorLabel, 30, SpringLayout.WEST, bPanel);
        bPanel.add(authorLabel);

        genreLabel = new JLabel("ジャンル");
        sLayout.putConstraint(SpringLayout.SOUTH, genreLabel, 29, SpringLayout.SOUTH, authorLabel);
        sLayout.putConstraint(SpringLayout.WEST, genreLabel, 30, SpringLayout.WEST, bPanel);
        bPanel.add(genreLabel);

        totalPagesLabel = new JLabel("ページ数");
        sLayout.putConstraint(SpringLayout.SOUTH, totalPagesLabel, 29, SpringLayout.SOUTH, genreLabel);
        sLayout.putConstraint(SpringLayout.WEST, totalPagesLabel, 30, SpringLayout.WEST, bPanel);
        bPanel.add(totalPagesLabel);

        errorMessage = "※数字のみ                ";
        errorMessageLabel = new JLabel(errorMessage);
        sLayout.putConstraint(SpringLayout.SOUTH, errorMessageLabel, 29, SpringLayout.SOUTH, genreLabel);
        sLayout.putConstraint(SpringLayout.EAST, errorMessageLabel, -80, SpringLayout.EAST, bPanel);
        bPanel.add(errorMessageLabel);
        
        /*
         * TextField
         */
        inputTitle = new JTextField();
        inputTitle.setPreferredSize(new Dimension(200, 24));
        sLayout.putConstraint(SpringLayout.SOUTH, inputTitle, 50, SpringLayout.SOUTH, booksScrollPane);
        sLayout.putConstraint(SpringLayout.WEST, inputTitle, 58, SpringLayout.WEST, titleLabel);
        bPanel.add(inputTitle);

        inputAuthor = new JTextField();
        inputAuthor.setPreferredSize(new Dimension(200, 24));
        sLayout.putConstraint(SpringLayout.SOUTH, inputAuthor, 29, SpringLayout.SOUTH, inputTitle);
        sLayout.putConstraint(SpringLayout.WEST, inputAuthor, 58, SpringLayout.WEST, authorLabel);
        bPanel.add(inputAuthor);

        inputGenre = new JTextField();
        inputGenre.setPreferredSize(new Dimension(200, 24));
        sLayout.putConstraint(SpringLayout.SOUTH, inputGenre, 29, SpringLayout.SOUTH, inputAuthor);
        sLayout.putConstraint(SpringLayout.WEST, inputGenre, 58, SpringLayout.WEST, genreLabel);
        bPanel.add(inputGenre);
        
        inputTotalPages = new JTextField();
        inputTotalPages.setPreferredSize(new Dimension(200, 24));
        inputTotalPages.addKeyListener(new KeyListener() {
            // キーが半角数字でない場合、入力を無視する
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();

                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        sLayout.putConstraint(SpringLayout.SOUTH, inputTotalPages, 29, SpringLayout.SOUTH, inputGenre);
        sLayout.putConstraint(SpringLayout.WEST, inputTotalPages, 58, SpringLayout.WEST, totalPagesLabel);
        bPanel.add(inputTotalPages);

        getBooksPanel.add(bPanel, BorderLayout.CENTER);

    }

    public void run() {
        this.manageFrame.setVisible(true);
    }
}
