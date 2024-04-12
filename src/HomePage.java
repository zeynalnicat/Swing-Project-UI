import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage {
    private JFrame ui;
    private HashMap<Integer, String> destinations = new HashMap<>();
    private JLabel rightResult;
    private Client client;

    public HomePage() {
        initialize();
        client = new Client();
        client.createConnection();
    }

    private void initialize() {
        ui = new JFrame("Assignments");
        ui.setSize(800, 600);
        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.disconnect();
            }
        });
        ui.setVisible(true);

        //header panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setBackground(new Color(79, 113, 202));
        panel.setPreferredSize(new Dimension(200, 80));

        JLabel txtHeader = new JLabel("Assignments");
        txtHeader.setForeground(Color.WHITE);
        txtHeader.setFont(new Font("Arial", Font.BOLD, 24));
        txtHeader.setBorder(new EmptyBorder(20, 0, 0, 0));
        panel.add(txtHeader, BorderLayout.CENTER);

        // main label
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = mainPanel.getWidth() / 2;
                return size;
            }
        };

        // left panel
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(30, 30, 30, 0));
        JLabel leftHeader = new JLabel("Select up to 5 destinations");
        leftHeader.setVerticalAlignment(SwingConstants.TOP);
        leftHeader.setFont(new Font("Arial", Font.BOLD, 16));
        leftHeader.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(Box.createVerticalGlue(), BorderLayout.NORTH);
        leftPanel.add(leftHeader, BorderLayout.NORTH);

        JPanel destinationPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        ;
        destinationPanel.setBorder(new EmptyBorder(30, 0, 30, 0));
        for (int i = 0; i < 5; i++) {
            JPanel destinationEntry = new JPanel(new BorderLayout());
            JLabel destinationLabel = new JLabel("Destination " + (i + 1));

            String places[] = {
                    "Choose a destination",
                    "Paris",
                    "London",
                    "Dubai",
                    "Tokyo",
                    "Istanbul",
                    "Rome",
                    "Singapore",
                    "Seoul",
                    "New York City",
                    "Baku"
            };
            JSpinner destinationTextArea = new JSpinner(new SpinnerListModel(places));

            Dimension preferredSize = destinationTextArea.getPreferredSize();
            preferredSize.height = 50;
            destinationTextArea.setPreferredSize(preferredSize);

            int finalI = i;
            destinationTextArea.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    Object selectedValue = destinationTextArea.getValue();
                    if (!selectedValue.toString().equals(places[0])) {
                        String selectedDestination = selectedValue.toString();
                        destinations.put(finalI, selectedDestination);
                    } else {
                        destinations.remove(finalI);
                    }
                }
            });
            destinationEntry.add(destinationLabel, BorderLayout.NORTH);
            destinationEntry.add(destinationTextArea, BorderLayout.CENTER);
            destinationPanel.add(destinationEntry);
        }
        leftPanel.add(destinationPanel, BorderLayout.CENTER);

        JButton submit = new JButton();
        submit.setText("Submit");
        submit.setBackground(new Color(79, 113, 202));
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(e -> {

            List<String> preferences = new ArrayList<>(5);
            preferences.addAll(destinations.values());

            client.submitResults(preferences);
            String result = client.receiveAssignment();
            updateResultLabel(result);

        });
        leftPanel.add(submit, BorderLayout.SOUTH);
        leftPanel.add(submit, BorderLayout.SOUTH);

        // right panel
        JPanel rightPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = mainPanel.getWidth() / 2;
                return size;
            }
        };

        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        JLabel rightHeader = new JLabel("Result: ");
        rightHeader.setHorizontalAlignment(SwingConstants.CENTER);
        rightResult = new JLabel("You have not selected any data. ");
        rightResult.setHorizontalAlignment(SwingConstants.CENTER);
        rightResult.setFont(new Font("Arial", Font.BOLD, 14));

        rightHeader.setFont(new Font("Arial", Font.BOLD, 16));
        rightHeader.setVerticalAlignment(SwingConstants.TOP);
        rightPanel.add(rightHeader, BorderLayout.NORTH);
        rightPanel.add(rightResult, BorderLayout.CENTER);

        // add left & right panel to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        ui.add(panel, BorderLayout.NORTH);
        ui.add(mainPanel, BorderLayout.CENTER);
    }

    private void updateResultLabel(String result) {
        rightResult.setText(result);
    }
}
