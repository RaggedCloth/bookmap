package window;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.PasswordEncoder;

public class LoginForm {
    private final JFrame loginForm;
    private JLabel userName;
    private JLabel password;
    private JTextField loginIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    Window window;

    public LoginForm() {
        /*
         * JFrame
         */
        loginForm = new JFrame();
        loginForm.setTitle("Please Login");
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.setBounds(1100, 100, 525, 310);
        loginForm.setResizable(false);

        /*
         * JPanel
         */
        Container getP = loginForm.getContentPane();
        loginPanel = new JPanel();
        loginPanel.setLayout(null);

        /*
         * JLabel
         */
        userName = new JLabel("LOGIN ID");
        userName.setForeground(Color.BLACK);
        userName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        userName.setBounds(80, 66, 193, 52);
        loginPanel.add(userName);

        password = new JLabel("PASSWORD");
        password.setForeground(Color.BLACK);
        password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        password.setBounds(80, 120, 193, 52);
        loginPanel.add(password);

        /*
         * JTextField
         */
        loginIdField = new JTextField();
        loginIdField.setFont(new Font("Tahoma", Font.PLAIN, 21));
        loginIdField.setBounds(200, 77, 150, 35);
        loginPanel.add(loginIdField);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 21));
        passwordField.setBounds(200, 127, 150, 35);
        loginPanel.add(passwordField);

        /*
         * JButton
         */
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        loginButton.setBounds(116, 180, 80, 35);
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PasswordEncoder passEnc = new PasswordEncoder();

                // テキストフィールドの値を変数に
                String loginId = loginIdField.getText();
                char[] passwordArray = passwordField.getPassword();
                String password = new String(passwordArray);

                // textareaに文字が入力されているかの確認
                if (!loginId.isEmpty()) {
                    // passEnc.subscribe(loginId, password); //一時的な登録ボタンの代替コマンド 後で消す
                    // userCheck()にloginIdとpasswordを渡して照合
                    boolean userIsVerified = passEnc.userCheck(loginId, password);
                    if (userIsVerified) {
                        System.out.println("ログインしました。");
                        Window window = new Window();
                        loginForm.setVisible(false);
                        window.run();
                    } else {
                        System.out.println("IDまたはパスワードが間違っています。");
                    }
                } else {
                    System.out.println("ログインIDを入力してください");
                }
            }
        });
        loginPanel.add(loginButton);
        getP.add(loginPanel);
    }

    public void run() {
        loginForm.setVisible(true);
        // Window window = new Window();
        // window.run();
    }
}
