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
    private JButton subscribeButton;
    Window window;
    private int[] userData = new int[2];
    private int userId;
    private int previousBookId;
    

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
         * JButton
         */
        subscribeButton = new JButton("Sign up");
        subscribeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        subscribeButton.setBounds(250, 180, 94, 35);
        subscribeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Window window = new Window(1,1); //デバッグ用　後で登録ボタンに変更
                loginForm.setVisible(false);
                window.run();
            }
        });
        loginPanel.add(subscribeButton);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        loginButton.setBounds(116, 180, 80, 35);
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PasswordEncoder passEnc = new PasswordEncoder();

                // フィールドのテキストを変数に
                String loginId = loginIdField.getText().stripTrailing(); // 末尾の空白を全て削除
                char[] passwordArray = passwordField.getPassword();
                String password = new String(passwordArray);

                // textareaに文字が入力されているかの確認
                if (!loginId.isEmpty()) {
                    // passEnc.subscribe(loginId, password); //一時的な登録ボタンの代替コマンド 後で消す
                    // userCheck()にloginIdとpasswordを渡して照合
                    boolean userIsVerified = passEnc.userCheck(loginId, password);
                    if (userIsVerified) {
                        System.out.println("ログインしました。");
                        userData = passEnc.getUserData(loginId);
                        userId = userData[0];
                        previousBookId = userData[1];
                        Window window = new Window(userId, previousBookId);
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

        /*
         * JTextField
         */
        loginIdField = new JTextField();
        loginIdField.setFont(new Font("Tahoma", Font.PLAIN, 21));
        loginIdField.setBounds(200, 77, 150, 35);
        loginPanel.add(loginIdField);

        passwordField = new JPasswordField();
        ActionListener[] loginEvent = loginButton.getActionListeners(); // loginButtonのActionListenerの配列を入れる
        passwordField.addActionListener(loginEvent[0]);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 21));
        passwordField.setBounds(200, 127, 150, 35);
        loginPanel.add(passwordField);

    }

    public void run() {
        loginForm.setVisible(true);
        // Window window = new Window();
        // window.run();
    }

}
