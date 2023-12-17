package window;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JOptionPane;
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
    private JLabel messageLabel;
    private JTextField loginIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton rootLoginButton;
    private JButton subscribeButton;
    Window window;
    private int[] userData = new int[2];
    private int userId;
    private int previousBookId;
    PasswordEncoder passEnc = new PasswordEncoder();

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

        messageLabel = new JLabel("welcome to Bookmap");
        messageLabel.setFont(new Font("Meirio UI", Font.PLAIN, 13));
        messageLabel.setBounds(0, 230, 500, 52);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        loginPanel.add(messageLabel);

        /*
         * JButton
         */
        subscribeButton = new JButton("Sign up");
        subscribeButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        subscribeButton.setBounds(299, 180, 80, 30);
        subscribeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String message = null;
                String loginId = loginIdField.getText().stripTrailing();
                if (loginId.isEmpty() || loginId == null) {
                    message = "LoginIDは1文字以上でお願いします。";

                } else if (passEnc.isUsed(loginId)) {
                    message = "既に存在するIDです。別のIDへの変更をお願いします。";
                } else {
                    message = verifyPassword();
                }
                messageLabel.setText(message);
            }
        });

        loginPanel.add(subscribeButton);

        rootLoginButton = new JButton("root");
        rootLoginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        rootLoginButton.setBounds(415, 230, 85, 30);
        rootLoginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // Window window = new Window(1,1); //デバッグ用 後で登録ボタンに変更
                // window.run();
                loginForm.setVisible(false);
                Horizontal horizontal = new Horizontal(1, 1);
                horizontal.run();
            }
        });
        loginPanel.add(rootLoginButton);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        loginButton.setBounds(200, 180, 80, 30);
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PasswordEncoder passEnc = new PasswordEncoder();

                // フィールドのテキストを変数に
                String loginId = loginIdField.getText().stripTrailing(); // 末尾の空白を全て削除
                char[] passwordArray = passwordField.getPassword();
                String password = new String(passwordArray);

                // textareaに文字が入力されているかの確認
                if (!loginId.isEmpty()) {
                    // userCheck()にloginIdとpasswordを渡して照合
                    boolean userIsVerified = passEnc.userCheck(loginId, password);
                    if (userIsVerified) {
                        System.out.println("ログインしました。");
                        userData = passEnc.getUserData(loginId);
                        login(loginId);
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
        loginIdField.setBounds(200, 77, 180, 35);
        loginPanel.add(loginIdField);

        passwordField = new JPasswordField();
        ActionListener[] loginEvent = loginButton.getActionListeners(); // loginButtonのActionListenerの配列を入れる
        passwordField.addActionListener(loginEvent[0]);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 21));
        passwordField.setBounds(200, 127, 180, 35);
        loginPanel.add(passwordField);

    }

    public String verifyPassword() {
        String message = null;
        char[] passwordArray = passwordField.getPassword();
        String password = new String(passwordArray);
        String inputPassword = JOptionPane.showInputDialog(loginForm, "もう一度同じパスワードを入力してください", "パスワードの確認",
                JOptionPane.QUESTION_MESSAGE);
        if (inputPassword == null || inputPassword.isEmpty()) {
            // 何もせずにウィンドウを閉じるだけ
        } else if (inputPassword.equals(password)) {
            message = "登録されました。";
            passEnc.subscribe(loginIdField.getText(), password);
            login(loginIdField.getText());
        } else if (!inputPassword.equals(password)) {
            message = "パスワードが違います";
        }
        return message;
    }

    public void login(String loginId) {
        userData = passEnc.getUserData(loginId);
        userId = userData[0];
        previousBookId = userData[1];
        loginForm.setVisible(false);
        Horizontal horizontal = new Horizontal(userId, previousBookId);
        horizontal.run();
    }

    public void run() {
        loginForm.setVisible(true);
        // Window window = new Window();
        // window.run();
    }

}
