import javax.swing.SwingUtilities;

import window.Horizontal;
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
        //LoginForm login = new LoginForm();
        //login.run();
        // Window window = new Window();
        // window.run();
//    	layouttest layout = new layouttest();
//    	layout.run();
    	Horizontal horizon = new Horizontal();
    	horizon.run();
    }
}
