import javax.swing.*;

public class UserInterface implements Runnable {
    @Override
    public void run() {
        SwingUtilities.invokeLater(HomePage::new);
    }
}
