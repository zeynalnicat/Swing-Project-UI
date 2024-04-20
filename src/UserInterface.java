import javax.swing.*;

public class UserInterface implements Runnable {
    @Override
    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomePage homeUi = new HomePage();
            }
        });
    }
}
