import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage {
    private JFrame ui;
    private HashMap<Integer, String> destinations = new HashMap<>();
    private JLabel rightResult;
    private Client client;

    // Constructor
    public HomePage() {
        initialize(); // Initialize UI components
        client = new Client(); // Initialize the client
        client.createConnection(); // Create connection to the server
    }

    // Initialize UI components
    private void initialize() {
        ui = new JFrame("Assignments"); // Create main JFrame
        ui.setSize(550, 550); // Set size of the JFrame
        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set default close operation
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.disconnect(); // Disconnect from the server when closing the window
            }
        });
        ui.setVisible(true); // Set JFrame visible

        // Header panel
        JPanel panel = new JPanel(); // Create header panel
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Set layout for header panel
        panel.setBackground(new Color(79, 113, 202)); // Set background color
        panel.setPreferredSize(new Dimension(200, 80)); // Set preferred size for header panel

        JLabel txtHeader = new JLabel("Assignments"); // Create header label
        txtHeader.setForeground(Color.WHITE); // Set text color
        txtHeader.setFont(new Font("Arial", Font.BOLD, 24)); // Set font
        txtHeader.setBorder(new EmptyBorder(20, 0, 0, 0)); // Set border
        panel.add(txtHeader, BorderLayout.CENTER); // Add label to panel

        // Main label
        JPanel mainPanel = new JPanel(new BorderLayout()); // Create main panel
        mainPanel.setBackground(Color.WHITE); // Set background color

        // Left panel
        JPanel leftPanel = new JPanel() { // Create left panel
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize(); // Get preferred size
                size.width = mainPanel.getWidth() / 2; // Set width
                return size; // Return size
            }
        };

        leftPanel.setLayout(new BorderLayout()); // Set layout for left panel
        leftPanel.setBorder(new EmptyBorder(30, 30, 30, 0)); // Set border
        JLabel leftHeader = new JLabel("Select up to 5 destinations"); // Create header label for left panel
        leftHeader.setVerticalAlignment(SwingConstants.TOP); // Set vertical alignment
        leftHeader.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        leftHeader.setHorizontalAlignment(SwingConstants.CENTER); // Set horizontal alignment
        leftPanel.add(Box.createVerticalGlue(), BorderLayout.NORTH); // Add vertical glue
        leftPanel.add(leftHeader, BorderLayout.NORTH); // Add header label

        JPanel destinationPanel = new JPanel(new GridLayout(5, 1, 0, 10)); // Create panel for destinations
        destinationPanel.setBorder(new EmptyBorder(30, 0, 30, 0)); // Set border

        for (int i = 0; i < 5; i++) { // Loop through 5 destinations
            JPanel destinationEntry = new JPanel(new BorderLayout()); // Create panel for each destination
            JLabel destinationLabel = new JLabel("Destination " + (i + 1)); // Create label for destination

            String places[] = { // Array of destination options
                    "Choose a destination", "Paris", "London", "Dubai", "Tokyo", "Istanbul", "Rome", "Singapore", "Seoul", "New York City", "Baku"};
            JSpinner destinationTextArea = new JSpinner(new SpinnerListModel(places)); // Create spinner for destinations

            Dimension preferredSize = destinationTextArea.getPreferredSize(); // Get preferred size
            preferredSize.height = 50; // Set height
            destinationTextArea.setPreferredSize(preferredSize); // Set preferred size

            int finalI = i;
            destinationTextArea.addChangeListener(new ChangeListener() { // Add change listener to spinner
                @Override
                public void stateChanged(ChangeEvent e) {
                    Object selectedValue = destinationTextArea.getValue(); // Get selected value
                    if (!selectedValue.toString().equals(places[0])) { // Check if it's not the default option
                        String selectedDestination = selectedValue.toString(); // Get selected destination
                        destinations.put(finalI, selectedDestination); // Add to destinations map
                    } else {
                        destinations.remove(finalI); // Remove from destinations map
                    }
                }
            });
            destinationEntry.add(destinationLabel, BorderLayout.NORTH); // Add label to destination entry
            destinationEntry.add(destinationTextArea, BorderLayout.CENTER); // Add spinner to destination entry
            destinationPanel.add(destinationEntry); // Add destination entry to destination panel
        }
        leftPanel.add(destinationPanel, BorderLayout.CENTER); // Add destination panel to left panel

        JButton submit = new JButton(); // Create submit button
        submit.setText("Submit"); // Set text
        submit.setBackground(new Color(79, 113, 202)); // Set background color
        submit.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        submit.setForeground(Color.WHITE); // Set text color

        submit.addActionListener(e -> {
            List<String> preferences = new ArrayList<>(5);
            preferences.addAll(destinations.values());
            client.submitResults(preferences);
            submit.setEnabled(false);
            client.receiveAssignment(result -> {
                updateResultLabel(result);
                submit.setEnabled(true);
            });
        });

        leftPanel.add(submit, BorderLayout.SOUTH); // Add submit button to left panel

        // Right panel
        JPanel rightPanel = new JPanel() { // Create right panel
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize(); // Get preferred size
                size.width = mainPanel.getWidth() / 2; // Set width
                return size; // Return size
            }
        };

        rightPanel.setLayout(new BorderLayout()); // Set layout for right panel
        rightPanel.setBorder(new EmptyBorder(30, 0, 0, 0)); // Set border
        JLabel rightHeader = new JLabel("Result: "); // Create header label for right panel
        rightHeader.setHorizontalAlignment(SwingConstants.CENTER); // Set horizontal alignment
        rightResult = new JLabel("You have not selected any data. "); // Create result label
        rightResult.setHorizontalAlignment(SwingConstants.CENTER); // Set horizontal alignment
        rightResult.setFont(new Font("Arial", Font.BOLD, 14)); // Set font

        rightHeader.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for header
        rightHeader.setVerticalAlignment(SwingConstants.TOP); // Set vertical alignment
        rightPanel.add(rightHeader, BorderLayout.NORTH); // Add header label to right panel
        rightPanel.add(rightResult, BorderLayout.CENTER); // Add result label to right panel

        // Add left and right panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST); // Add left panel to main panel
        mainPanel.add(rightPanel, BorderLayout.EAST); // Add right panel to main panel

        ui.add(panel, BorderLayout.NORTH); // Add header panel to JFrame
        ui.add(mainPanel, BorderLayout.CENTER); // Add main panel to JFrame
    }

    // Method to update the result label
    private void updateResultLabel(String result) {
        rightResult.setText(result); // Set text of the result label
    }
}
