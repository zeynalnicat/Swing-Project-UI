import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomePage {
    private JFrame ui;

    private HashMap<Integer,String> destinations = new HashMap<>();

    private JLabel rightResult;

    public HomePage() {
        destinations = new HashMap<>();
        initialize();
    }

    private void initialize() {
        ui = new JFrame("Assignments");
        ui.setSize(800, 600);
        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

            String places[] = {"Choose a destination", "Baku", "Qazax", "Quba", "Qebele", "Berlin", "Istanbul"};
            JSpinner destinationTextArea = new JSpinner(new SpinnerListModel(places));

            JScrollPane destinationScrollPane = new JScrollPane(destinationTextArea);

            int finalI = i;
            destinationTextArea.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    Object selectedValue = destinationTextArea.getValue();
                    if (!Objects.equals(selectedValue.toString(), places[0])) {
                        String selectedDestination = selectedValue.toString();
                        destinations.put(finalI,selectedDestination);
                    }
                    else {
                        destinations.remove(finalI);
                    }
                }
            });
            destinationEntry.add(destinationLabel, BorderLayout.NORTH);
            destinationEntry.add(destinationScrollPane, BorderLayout.CENTER);
            destinationPanel.add(destinationEntry);
        }
        leftPanel.add(destinationPanel, BorderLayout.CENTER);


        JButton submit = new JButton();
        submit.setText("Submit");
        submit.setBackground(new Color(79, 113, 202));
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        submit.setForeground(Color.WHITE);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(ui, "Submitted successfully");
                updateResultLabel();

            }
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


        rightResult = new JLabel(destinations.toString());
        rightResult.setHorizontalAlignment(SwingConstants.CENTER);
        rightResult.setFont(new Font("Arial", Font.BOLD, 14));


        rightHeader.setFont(new Font("Arial", Font.BOLD, 16));
        rightHeader.setVerticalAlignment(SwingConstants.TOP);
        rightPanel.add(rightHeader, BorderLayout.NORTH);
        rightPanel.add(rightResult, BorderLayout.CENTER);


        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        ui.add(panel, BorderLayout.NORTH);
        ui.add(mainPanel, BorderLayout.CENTER);
    }


    private void updateResultLabel() {
        rightResult.setText(destinations.toString());
    }


}
