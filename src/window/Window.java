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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ShowController;
import dao.ProgressDAO;

public class Window extends JFrame {
    private static int todayPages;
    private static int bookId;
    private final JFrame frame;
    private final JLabel sumDaysLabel;
    private final JLabel sumDaysAnsLabel;
    private final JLabel remainPageLabel;
    private final JLabel rPAnsLabel;
    // private final JLabel achieveLabel;
    private final JLabel avgPageLabel;
    private final JLabel avgPAnsLabel;
    private final JLabel todayPageLabel;
    private final JLabel progressLabel;
    private final JTable recentData;
    private final DefaultTableModel model;
    private final JButton bookTitleButton;
    private final JButton logoButton;
    private final JButton inputButton;
    private final JButton topButton;
    private final JButton settingsButton;
    private final JButton previousButton;
    private final JButton nextButton;
    private final JButton deleteButton;
    private final JTextField avgText;
    private BufferedImage jordan;
    private final JProgressBar progressBar;
    private final ShowController showC;

    public Window() {

        this.frame = new JFrame();
        frame.setTitle("book map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(1100, 100, 800, 700);

        var getP = frame.getContentPane();
        JPanel panel = new JPanel();
        // JPanel panel2 = new JPanel();
        // panel2.setLayout(new BorderLayout());
        bookId = 1;
        showC = new ShowController(bookId);

        GridBagLayout gbLayout = new GridBagLayout();
        panel.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        // for (int i = 0; i < 6; i++) {
        // for (int j = 0; j < 12; j++) {
        // gbc.gridx = i;
        // gbc.gridy = j;
        // gbc.gridwidth = 1;
        // gbc.gridheight = 1;
        // gbc.anchor = GridBagConstraints.NORTHWEST;
        // JLabel lbl = new JLabel( ""
        // //"(" + i + ", " + j + ")"
        // );
        // gbLayout.setConstraints(lbl, gbc);
        // panel.add(lbl);
        // }
        // }

        /*
         * X == 4
         * X == 5
         * 
         */

        this.logoButton = new JButton("LOGO");
        logoButton.addActionListener(e -> {
            updateText(bookId);
        });
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbLayout.setConstraints(logoButton, gbc);
        panel.add(this.logoButton);

        this.sumDaysLabel = new JLabel("読んだ日数");
        this.sumDaysLabel.setBackground(Color.green);
        this.sumDaysLabel.setOpaque(true);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        sumDaysLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 18));
        // totalDLabel.setPreferredSize(new Dimension(200, 50));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbLayout.setConstraints(sumDaysLabel, gbc);
        panel.add(this.sumDaysLabel);

        this.remainPageLabel = new JLabel("残りのページ");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        remainPageLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 18));
        gbLayout.setConstraints(remainPageLabel, gbc);
        panel.add(this.remainPageLabel);

        this.avgPageLabel = new JLabel("1日の平均ページ");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        avgPageLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 18));
        gbLayout.setConstraints(avgPageLabel, gbc);
        panel.add(this.avgPageLabel);

        /*
         * Answer
         * 
         * 
         */
        // 要修正 bookidを直接指定ではなく変数で指定できるようにする。例:本を選択した時に変数に代入しそれを呼ぶ
        this.sumDaysAnsLabel = new JLabel(showC.sumDays(bookId) + "日");
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        sumDaysAnsLabel.setFont(new Font("MS ゴシック", Font.BOLD, 30));
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbLayout.setConstraints(sumDaysAnsLabel, gbc);
        panel.add(this.sumDaysAnsLabel);

        this.rPAnsLabel = new JLabel(showC.remainPages(1) + "P");
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        rPAnsLabel.setFont(new Font("MS ゴシック", Font.BOLD, 30));
        gbLayout.setConstraints(rPAnsLabel, gbc);
        panel.add(this.rPAnsLabel);

        this.avgPAnsLabel = new JLabel(showC.average(bookId) + "P");
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        avgPAnsLabel.setFont(new Font("MS ゴシック", Font.BOLD, 30));
        gbLayout.setConstraints(avgPAnsLabel, gbc);
        panel.add(this.avgPAnsLabel);

        /*
         * Table
         * 
         */
        model = new DefaultTableModel();
        model.addColumn("ページ数");
        model.addColumn("作成日時");
        recentData = new JTable(model);
        List<String[]> tableData = new ArrayList<>();
        tableData = showC.RecentData(bookId);
        for (String[] row : tableData) {
            model.addRow(row);
        }
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 15, 0, 15);
        gbLayout.setConstraints(recentData, gbc);
        //JScrollPane scrollPane = new JScrollPane(recentData);
        // scrollPane.setPreferredSize(new Dimension(150, 103));
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(recentData.getTableHeader(), gbc);
        // panel.add(scrollPane, gbc);
        panel.add(recentData);

        /*
         * 削除ボタン
         * 余裕があれば削除の確認のポップアップウィンドウ
         */
        this.deleteButton = new JButton("1件削除");
        deleteButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                showC.deleteRecentData(bookId);
                updateText(bookId);
        }});
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 15, 0, 15);
        gbLayout.setConstraints(this.deleteButton, gbc);
        panel.add(this.deleteButton);

        /*
         * Text Field-----------------------------------------------
         */

        this.todayPageLabel = new JLabel("今日のページ数");
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(25, 10, 12, 0);
        todayPageLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
        // todayPageLabel.setPreferredSize(new Dimension(200, 50));
        gbLayout.setConstraints(todayPageLabel, gbc);
        panel.add(this.todayPageLabel);

        this.avgText = new JTextField("");
        this.avgText.setColumns(4);
        gbc.gridx = 5;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        // gbc.ipadx = 40;
        avgText.setHorizontalAlignment(JTextField.CENTER);
        avgText.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(25, 10, 10, 25);
        gbLayout.setConstraints(avgText, gbc);
        panel.add(this.avgText);

        /*
         * 
         * Button--------------------------------------------------
         */
        // 追加機能 バックアップテーブルを準備する
        // 過去5日間の読んだページ数を日付とともに表示
        this.inputButton = new JButton("入力");
        inputButton.setPreferredSize(new Dimension(60, 25));
        inputButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (avgText.getText() == null) {
                    return;
                } else {
                    todayPages = Integer.valueOf(avgText.getText());
                    ProgressDAO pdao = new ProgressDAO();
                    pdao.insertTodayPage(todayPages, bookId);
                }
                updateText(bookId);
            }

        });
        gbc.gridx = 5;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbLayout.setConstraints(inputButton, gbc);
        panel.add(this.inputButton);

        this.topButton = new JButton("TOP");
        topButton.setPreferredSize(new Dimension(90, 25));
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbLayout.setConstraints(topButton, gbc);
        panel.add(this.topButton);

        this.settingsButton = new JButton("設定");
        settingsButton.setPreferredSize(new Dimension(90, 25));
        gbc.gridx = 5;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbLayout.setConstraints(settingsButton, gbc);
        panel.add(this.settingsButton);

        this.previousButton = new JButton("前の本");
        previousButton.setPreferredSize(new Dimension(90, 25));
        gbc.gridx = 4;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 15, 15, 15);
        gbLayout.setConstraints(previousButton, gbc);
        panel.add(this.previousButton);

        this.nextButton = new JButton("次の本");
        nextButton.setPreferredSize(new Dimension(90, 25));
        gbc.gridx = 5;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(5, 15, 15, 15);
        gbLayout.setConstraints(nextButton, gbc);
        panel.add(this.nextButton);

        /*
         * X == 2
         * X == 3
         * 
         * 
         */

        this.bookTitleButton = new JButton("Book title");
        bookTitleButton.setPreferredSize(new Dimension(250, 25));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbLayout.setConstraints(bookTitleButton, gbc);
        panel.add(this.bookTitleButton);

        /*
         * 
         * 機能追加 好きな画像を選べるようにする
         * 
         * try {
         * jordan = ImageIO.read(new File("./src/image/images.jpg"));
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * this.achieveLabel = new JLabel() {
         * 
         * @Override
         * public Dimension getPreferredSize() {
         * return new Dimension(313, 490);
         * }
         * };
         * this.achieveLabel.addComponentListener(new ComponentAdapter() {
         * 
         * @Override
         * public void componentResized(ComponentEvent e) {
         * JLabel achieveLabel = (JLabel) e.getComponent();
         * Dimension size = achieveLabel.getSize();
         * Insets insets = achieveLabel.getInsets();
         * size.width -= insets.left + insets.right;
         * size.height -= insets.top + insets.bottom;
         * if (size.width > size.height) { // 余白があると画像のアスペクト比が変わってしまうので
         * size.width = -1; // 大きいサイズの方を-1とし、getScaledInstanceで合わせる
         * } else {
         * size.height = -1;
         * }
         * Image scaled = jordan.getScaledInstance(size.width, size.height,
         * Image.SCALE_SMOOTH);
         * achieveLabel.setIcon(new ImageIcon(scaled));
         * }
         * });
         * 
         * 
         * this.achieveLabel.setText("<html><center>達成率<br>30%</center></html>");
         * this.achieveLabel.setVerticalTextPosition(JLabel.TOP);
         * this.achieveLabel.setHorizontalTextPosition(JLabel.CENTER);
         * this.achieveLabel.setBackground(Color.green);
         * this.achieveLabel.setOpaque(true);
         * this.achieveLabel.setHorizontalAlignment(JLabel.CENTER);
         * // this.achieveLabel.setPreferredSize(new Dimension(333, 500));
         * gbc.gridx = 2;
         * gbc.gridy = 2;
         * gbc.gridwidth = 2;
         * /////////gbc.gridheight = 9;
         * gbc.weightx = 1.0;
         * gbc.weighty = 0.1;
         * gbc.insets = new Insets(0, 0, 0, 0);
         * gbc.anchor = GridBagConstraints.CENTER;
         * gbc.fill = GridBagConstraints.HORIZONTAL;
         * gbLayout.setConstraints(achieveLabel, gbc);
         * ///panel.add(this.achieveLabel);
         * // panel2.add(this.achieveLabel, BorderLayout.CENTER);
         * // getP.add(panel2, BorderLayout.WEST);
         */

        /*
         * 
         * Progress Bar
         * 
         */

        int progress = (showC.progress(1)); // 現在の達成率
        progressLabel = new JLabel();
        progressLabel.setText("<html><center>達成率<br>" + progress + "%</center></html>");
        progressLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 26));
        // progressLabel.setBackground(Color.green);
        // progressLabel.setOpaque(true);
        // progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbLayout.setConstraints(progressLabel, gbc);
        panel.add(progressLabel);

        progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        progressBar.setStringPainted(false);
        progressBar.setValue(progress);
        // progressBar.setPreferredSize(new Dimension(50, 100));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbLayout.setConstraints(progressBar, gbc);
        panel.add(progressBar);

        getP.add(panel);
    }

    public void run() {
        this.frame.setVisible(true);
    }

    public void updateText(int bookId) {
        sumDaysAnsLabel.setText(showC.sumDays(bookId) + "日");
        rPAnsLabel.setText(showC.remainPages(bookId) + "P");
        avgPAnsLabel.setText(showC.average(bookId) + "P");
        model.setRowCount(0);
        List<String[]> tableData = new ArrayList<>();
        tableData = showC.RecentData(bookId);
        for (String[] row : tableData) {
            model.addRow(row);
        }
    }
}
