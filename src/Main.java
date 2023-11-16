import window.LoginForm;
import window.Window;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
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
