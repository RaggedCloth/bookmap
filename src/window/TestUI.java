package window;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TestUI extends BaseItems{


    TestUI(int userId, int previousBookId) {
        super();
        super.makeFrame(userId, previousBookId);
        SpringLayout sl_panel = new SpringLayout();
		panel.setBackground(new Color(48, 48, 48));
		panel.setLayout(sl_panel);
        var getP = frame.getContentPane();
        getP.add(panel);

        sl_panel.putConstraint(SpringLayout.NORTH, bookTitleLabel, 31, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, bookTitleLabel, 150, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, bookTitleLabel, -29, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, bookTitleLabel, -36, SpringLayout.EAST, panel);
		bookTitleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		bookTitleLabel.setText(/*showC.getBookTitle(userId, bookId)*/"スッキリJava入門");
		bookTitleLabel.setForeground(new Color(51, 153, 255));
		bookTitleLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 30));
		panel.add(bookTitleLabel);
        sl_panel.putConstraint(SpringLayout.WEST, changeUI, 250, SpringLayout.WEST, panel);
        panel.add(changeUI);

    }


    @Override
    void run() {
        this.frame.setVisible(true);
    }

    @Override
    void stop() {
        this.frame.setVisible(false);
    }
    
}
