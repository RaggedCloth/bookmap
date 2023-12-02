import window.LoginForm;
import window.Window;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                bookMap();
            }
        });
    }

    public static void bookMap() {
        LoginForm login = new LoginForm();
        login.run();
        // Window window = new Window();
        // window.run();
    }
}
