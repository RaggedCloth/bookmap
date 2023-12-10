package window;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import controller.ShowController;
import window.ManageBooks.LabelTimer;

public class ManageBooks {

    private final JFrame manageFrame;
    private final DefaultTableModel booksModel;
    private final DefaultTableModel copyOfBooksModel;
    private final JScrollPane booksScrollPane;
    private final JTable bookListTable;
    private final JTable copyOfBookListTable;
    private final JButton addBookButton;
    private final JButton deleteBookButton;
    private final JLabel titleLabel;
    private final JLabel authorLabel;
    private final JLabel genreLabel;
    private final JLabel totalPagesLabel;
    private final JLabel errorMessageLabel;
    private final JLabel updatedMessageLabel;
    private final JTextField inputTitle;
    private final JTextField inputAuthor;
    private final JTextField inputGenre;
    private final JTextField inputTotalPages;
    private String addTitle;
    private String addAuthor;
    private String addGenre;
    private String errorMessage;
    private int addTotalPages;
    private String updatedMessage;
    LabelTimer labelTimer;
    Timer timer;
    int sec = 0;
    ShowController showC;

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

        showC = new ShowController();
        /*
         * Table
         */

        // modelにカラムを追加しDAOから受け取ったデータを入れる
        booksModel = new DefaultTableModel();
        booksModel.addColumn("タイトル");
        booksModel.addColumn("著者");
        booksModel.addColumn("ジャンル");
        booksModel.addColumn("ページ数");
        booksModel.addColumn("book_id");
        List<String[]> booksData = new ArrayList<>();
        booksData = showC.getBookShelfList(userId);
        for (String[] bd : booksData) {
            booksModel.addRow(bd);
        }
        copyOfBooksModel = new DefaultTableModel();
        copyOfBooksModel.addColumn("タイトル");
        copyOfBooksModel.addColumn("著者");
        copyOfBooksModel.addColumn("ジャンル");
        copyOfBooksModel.addColumn("ページ数");
        copyOfBooksModel.addColumn("book_id");
        List<String[]> copyOfBooksData = new ArrayList<>();
        for (String[] bd : booksData) {
            // 配列を複製して新しいリストに追加
            String[] copiedArray = Arrays.copyOf(bd, bd.length);
            copyOfBooksData.add(copiedArray);
        }
        for (String[] bd : copyOfBooksData) {
            copyOfBooksModel.addRow(bd);
        }
        // ModelをTableに入れる
        bookListTable = new JTable(booksModel);
        copyOfBookListTable = new JTable(copyOfBooksModel); // 変更がなければSQLを発行しないようにするためのデータ比較用
        bookListTable.setAutoCreateRowSorter(true);
        bookListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // book_idのカラムは非表示にする
        TableColumn bookIdColumn = bookListTable.getColumnModel().getColumn(4);
        bookIdColumn.setMinWidth(0);
        bookIdColumn.setMaxWidth(0);
        bookIdColumn.setWidth(0);
        bookIdColumn.setPreferredWidth(0);

        labelTimer = new LabelTimer();
        booksModel.addTableModelListener(new TableModelListener() {
            /*
             * Tableの編集処理
             * row,columnをshowCからDAOに送ってその結果をJLabelで受け取る
             */
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {

                    // 選択されたセルを特定
                    int sortedRow = e.getFirstRow();
                    int originalRow = bookListTable.convertRowIndexToModel(sortedRow);
                    int column = e.getColumn();
                    // DefaultTableModel model = (DefaultTableModel) e.getSource();
                    String columnName = booksModel.getColumnName(column);

                    // 用意していたモデルの複製と、変更されたモデルの値を比較
                    Object editedDataObject = booksModel.getValueAt(originalRow, column);
                    Object oldDataObject = copyOfBookListTable.getValueAt(originalRow, column);
                    if (editedDataObject instanceof String && !editedDataObject.equals(oldDataObject)) {
                        String editedData = (String) editedDataObject;
                        updatedMessage = showC.editBookData(originalRow, columnName, editedData);

                        // updateが完了したメッセージを5秒間表示
                        updatedMessageLabel.setText(updatedMessage);
                        updatedMessageLabel.setVisible(true);
                        timer = new Timer(5000, labelTimer);
                        timer.start();
                    }
                }
            }
        });

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
                System.out.println(result);
                updateFrame(userId);
            }
        });
        sLayout.putConstraint(SpringLayout.SOUTH, addBookButton, 50, SpringLayout.SOUTH, booksScrollPane);
        sLayout.putConstraint(SpringLayout.EAST, addBookButton, -30, SpringLayout.EAST, bPanel);
        bPanel.add(addBookButton);

        deleteBookButton = new JButton("削除");
        deleteBookButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = bookListTable.getSelectedRow();
                if (selectedRow != -1) {

                    // 選択された行とそのbook_idを特定
                    int modelRow = bookListTable.convertRowIndexToModel(selectedRow);
                    int bookId = Integer.parseInt(booksModel.getValueAt(modelRow, 4).toString());

                    // 本当に削除しますか？のポップアップ
                    int userAnswer = JOptionPane.showConfirmDialog(null,
                            "この本の進捗データは失われます。本当に削除しますか？", "注意", JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (userAnswer == JOptionPane.YES_OPTION) {
                        showC.deleteBookByTable(userId, bookId);
                        updateFrame(userId);
                    } else if (userAnswer == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
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

        updatedMessageLabel = new JLabel();
        updatedMessageLabel.setVisible(false);
        sLayout.putConstraint(SpringLayout.SOUTH, updatedMessageLabel, 20, SpringLayout.SOUTH, booksScrollPane);
        sLayout.putConstraint(SpringLayout.EAST, updatedMessageLabel, -30, SpringLayout.EAST, bPanel);
        bPanel.add(updatedMessageLabel);

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

    /*
     * 本のTableを編集後、5秒間updatedMessageLabelを表示
     */
    class LabelTimer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            updatedMessageLabel.setVisible(false);
        }
    }

    // 追加buttonを押した後にJTableの内容を更新
    public void updateFrame(int userId) {
        booksModel.setRowCount(0);
        List<String[]> booksData = new ArrayList<>();
        booksData = showC.getBookShelfList(userId);
        for (String[] bd : booksData) {
            booksModel.addRow(bd);
        }
    }

    public void run() {
        this.manageFrame.setVisible(true);
    }
}
