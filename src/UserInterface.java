import javax.swing.*;

public class UserInterface {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomePage homeUi = new HomePage();
            }
        });
    }
}
