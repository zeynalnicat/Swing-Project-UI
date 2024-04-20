import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

    private Socket socket; // Socket for communication with the server
    private BufferedReader in; // Reader to receive messages from the server
    private PrintWriter out; // Writer to send messages to the server
    private String clientNumber; // Unique identifier assigned by the server
    private boolean running; // Flag to indicate if the client is running

    // Method to create a connection to the server
    public void createConnection() {
        try {
            // Create a socket connection to the server
            socket = new Socket(InetAddress.getLocalHost(), 3169);

            // Initialize the reader and writer for communication
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Receive the server's welcome message and client number
            String serverMessage = in.readLine();
            System.out.println(serverMessage);
            clientNumber = in.readLine();
            System.out.println("Client number: " + clientNumber);

        } catch (UnknownHostException e) {
            // Handle if the host is unknown
            throw new RuntimeException(e);
        } catch (IOException e) {
            // Handle IO exceptions
            throw new RuntimeException(e);
        }
    }

    // Method to submit preferences to the server
    public void submitResults(List<String> preferences) {
        // Send the "submit" command to the server
        out.println("submit");
        // Send each preference to the server
        for (String preference : preferences) {
            out.println(preference);
        }
        // Send "end" to indicate the end of preferences
        out.println("end");
        // Flush the output stream to ensure all data is sent
        out.flush();
    }

    // Method to receive the assignment from the server
    public String receiveAssignment() {
        try {
            // Continuously read messages from the server
            String assignment;
            while ((assignment = in.readLine()) != null) {
                // Print the received message
                System.out.println(assignment);
                // Check if the assignment message is received
                if (assignment.startsWith("You are assigned to ")) {
                    // Return the assignment message
                    return assignment;
                }
            }
            // Return a message if no assignment is received
            return "No assignment received";
        } catch (IOException e) {
            // Handle IO exceptions
            throw new RuntimeException(e);
        }
    }

    // Method to disconnect from the server
    public void disconnect() {
        try {
            // Send "disconnect" command to the server
            out.println("disconnect");
            // Flush the output stream to ensure the command is sent
            out.flush();
            // Receive acknowledgment from the server
            String response = in.readLine();
            // Print acknowledgment message
            if (response != null && response.equals("disconnect_ack")) {
                System.out.println("Disconnect acknowledgment received from server.");
            }
            // Close the socket connection
            socket.close();
        } catch (IOException e) {
            // Handle IO exceptions
            throw new RuntimeException(e);
        }
    }
}
