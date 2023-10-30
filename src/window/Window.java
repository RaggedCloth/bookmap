package window;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

import javax.swing.JLabel;

public class Window extends JFrame {
    private final JFrame frame;
    private final JLabel totalDLabel;
    private final JLabel remainPageLabel;
    private final JLabel achieveLabel;
    private final JLabel avgPageLabel;
    private final JLabel allTotalDayLabel;
    private final JButton bookTitleButton;
    private final JButton logoButton;
    private final JButton topButton;
    private final JButton settingsButton;
    private final JButton previousButton;
    private final JButton nextButton;

    private final JTextField avgText;

    private JLabel image;

    public Window() {

        this.frame = new JFrame();
        frame.setTitle("book map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(1000, 100, 800, 700);

        var getP = frame.getContentPane();
        JPanel panel = new JPanel();
        GridBagLayout gbLayout = new GridBagLayout();
        panel.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        /*
         * X == 0
         * X == 1
         * 
         */

        this.logoButton = new JButton("LOGO");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbLayout.setConstraints(logoButton, gbc);
        panel.add(this.logoButton);

        this.totalDLabel = new JLabel("Total Days:");
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        totalDLabel.setFont(new Font("MS ゴシック", Font.BOLD, 20));
        totalDLabel.setPreferredSize(new Dimension(200, 50));
        totalDLabel.setBorder(new LineBorder(Color.BLACK, 2, true));
        gbc.fill = GridBagConstraints.CENTER;
        // gbc.insets = new Insets(3, 3, 3, 3);
        gbLayout.setConstraints(totalDLabel, gbc);
        panel.add(this.totalDLabel);

        this.remainPageLabel = new JLabel("Remain Page:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbLayout.setConstraints(remainPageLabel, gbc);
        panel.add(this.remainPageLabel);

        this.avgPageLabel = new JLabel("1日平均ページ数:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbLayout.setConstraints(avgPageLabel, gbc);
        panel.add(this.avgPageLabel);

        this.allTotalDayLabel = new JLabel("All of Total Days:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbLayout.setConstraints(allTotalDayLabel, gbc);
        panel.add(this.allTotalDayLabel);

        this.topButton = new JButton("TOP");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 25, 0, 25);
        gbLayout.setConstraints(topButton, gbc);
        panel.add(this.topButton);

        this.settingsButton = new JButton("settings");
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 25, 0, 25);
        gbLayout.setConstraints(settingsButton, gbc);
        panel.add(this.settingsButton);

        this.avgText = new JTextField("");
        this.avgText.setColumns(3);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 25, 0, 25);
        gbLayout.setConstraints(avgText, gbc);
        panel.add(this.avgText);

        this.previousButton = new JButton("前の本");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 25, 0, 25);
        gbLayout.setConstraints(previousButton, gbc);
        panel.add(this.previousButton);

        this.nextButton = new JButton("前の本");
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbLayout.setConstraints(nextButton, gbc);
        panel.add(this.nextButton);

        /*
         * X == 2
         * X == 3
         * 
         * 
         */

        this.bookTitleButton = new JButton("Book title");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbLayout.setConstraints(bookTitleButton, gbc);
        panel.add(this.bookTitleButton);

        /*
         * X == 4
         * X == 5
         * 
         * 
         */

        ImageIcon jordan = new ImageIcon("./src/window/images.jpg");
        JLabel image = new JLabel(jordan);
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 11;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbLayout.setConstraints(image, gbc);
        panel.add(image);

        this.achieveLabel = new JLabel("達成率:");
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbLayout.setConstraints(achieveLabel, gbc);
        panel.add(this.achieveLabel);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 12; j++) {
                gbc.gridx = i;
                gbc.gridy = j;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                JLabel lbl = new JLabel("(" + i + ", " + j + ")");
                gbLayout.setConstraints(lbl, gbc);
                panel.add(lbl);
            }
        }

        getP.add(panel);
    }

    public void run() {
        this.frame.setVisible(true);
    }
}
