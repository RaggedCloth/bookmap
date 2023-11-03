package window;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.BorderLayout;

import javax.swing.border.LineBorder;
import java.awt.Graphics;
import java.awt.Image;
import image.Images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    private final JFrame frame;
    private final JLabel totalDLabel;
    private final JLabel tDAnsLabel;
    private final JLabel remainPageLabel;
    private final JLabel rPAnsLabel;
    private final JLabel achieveLabel;
    private final JLabel avgPageLabel;
    private final JLabel avgPAnsLabel;
    private final JLabel todayPageLabel;
    private final JButton bookTitleButton;
    private final JButton logoButton;
    private final JButton topButton;
    private final JButton settingsButton;
    private final JButton previousButton;
    private final JButton nextButton;
    private final JTextField avgText;
    private BufferedImage jordan;

    public Window() {

        this.frame = new JFrame();
        frame.setTitle("book map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(1100, 100, 800, 700);

        var getP = frame.getContentPane();
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        GridBagLayout gbLayout = new GridBagLayout();
        panel.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12; j++) {
                gbc.gridx = i;
                gbc.gridy = j;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                JLabel lbl = new JLabel(""
                // "(" + i + ", " + j + ")"
                );
                gbLayout.setConstraints(lbl, gbc);
                panel.add(lbl);
            }
        }

        /*
         * X == 4
         * X == 5
         * 
         */

        this.logoButton = new JButton("LOGO");
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbLayout.setConstraints(logoButton, gbc);
        panel.add(this.logoButton);

        this.totalDLabel = new JLabel("読んだ日数");
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        totalDLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 18));
        // totalDLabel.setPreferredSize(new Dimension(200, 50));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbLayout.setConstraints(totalDLabel, gbc);
        panel.add(this.totalDLabel);

        this.remainPageLabel = new JLabel("残りのページ");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        remainPageLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 18));
        gbLayout.setConstraints(remainPageLabel, gbc);
        panel.add(this.remainPageLabel);

        this.avgPageLabel = new JLabel("1日の平均");
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        avgPageLabel.setFont(new Font("MS ゴシック", Font.PLAIN, 18));
        gbLayout.setConstraints(avgPageLabel, gbc);
        panel.add(this.avgPageLabel);

        /*
         * Answer
         * 
         * 
         */

        this.tDAnsLabel = new JLabel("500P");
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        tDAnsLabel.setFont(new Font("MS ゴシック", Font.BOLD, 30));
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbLayout.setConstraints(tDAnsLabel, gbc);
        panel.add(this.tDAnsLabel);

        this.rPAnsLabel = new JLabel("200P");
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        rPAnsLabel.setFont(new Font("MS ゴシック", Font.BOLD, 30));
        gbLayout.setConstraints(rPAnsLabel, gbc);
        panel.add(this.rPAnsLabel);

        this.avgPAnsLabel = new JLabel("100P");
        gbc.gridx = 5;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        avgPAnsLabel.setFont(new Font("MS ゴシック", Font.BOLD, 30));
        gbLayout.setConstraints(avgPAnsLabel, gbc);
        panel.add(this.avgPAnsLabel);

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

        // this.achieveLabel = new JLabel("達成率:");

        // BufferedImage jordan2 = null;
        // try {
        // jordan2 = ImageIO.read(new File("./src/image/images.jpg"));
        // } catch (Exception e) {
        // // inname(入力JPEG)の読み込みに失敗したときの処理
        // e.printStackTrace();
        // return;
        // }
        // double width = jordan2.getWidth();
        // double height = jordan2.getHeight();
        // int framewidth = frame.getWidth();
        // int frameheight = frame.getHeight();
        // Images image = new Images();
        // image.setSize(width, height, framewidth, frameheight);
        // jordan2 = new BufferedImage(image.getNewWidth(), image.getNewHeight(),
        // BufferedImage.TYPE_INT_BGR);
        // int wid = image.getNewWidth(); // 画像の幅
        // int hei = image.getNewHeight(); // 画像の高さ
        // Image jordanScale = jordan2.getScaledInstance(wid, hei, Image.SCALE_SMOOTH);
        // this.achieveLabel.setIcon(new ImageIcon(jordanScale));

        try {
            jordan = ImageIO.read(new File("./src/image/images.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.achieveLabel = new JLabel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(313, 490);
            }
        };
        this.achieveLabel.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                JLabel achieveLabel = (JLabel) e.getComponent();
                Dimension size = achieveLabel.getSize();
                Insets insets = achieveLabel.getInsets();
                size.width -= insets.left + insets.right;
                size.height -= insets.top + insets.bottom;
                if (size.width > size.height) { // 余白があると画像のアスペクト比が変わってしまうので
                    size.width = -1; // 大きいサイズの方を-1とし、getScaledInstanceで合わせる
                } else {
                    size.height = -1;
                }
                Image scaled = jordan.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
                achieveLabel.setIcon(new ImageIcon(scaled));
            }
        });
        this.achieveLabel.setText("達成率");
        this.achieveLabel.setVerticalTextPosition(JLabel.TOP);
        this.achieveLabel.setHorizontalTextPosition(JLabel.CENTER);
        this.achieveLabel.setBackground(Color.green);
        this.achieveLabel.setOpaque(true);
        this.achieveLabel.setHorizontalAlignment(JLabel.CENTER);
        // this.achieveLabel.setPreferredSize(new Dimension(333, 500));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 9;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbLayout.setConstraints(achieveLabel, gbc);
        panel.add(this.achieveLabel);
        // panel2.add(this.achieveLabel, BorderLayout.CENTER);
        // getP.add(panel2, BorderLayout.WEST);
        getP.add(panel);
    }

    public void run() {
        this.frame.setVisible(true);
    }
}
