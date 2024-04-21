import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.Consumer;

public class Client {

    private Socket socket; // Socket for communication with the server
    private BufferedReader in; // Reader to receive messages from the server
    private PrintWriter out; // Writer to send messages to the server
    private String clientNumber; // Unique identifier assigned by the server
    private Thread listenerThread; // Thread for listening to server messages
    private Consumer<String> assignmentListener; // Listener for assignment messages

    // Method to create a connection to the server
    public void createConnection() {
        try {
            // Create a socket connection to the server
            socket = new Socket(InetAddress.getLocalHost(), 3169);

            // Initialize the reader and writer for communication
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

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

    public void submitResults(List<String> preferences) {
        // Send the "submit" command to the server
        out.println("submit");
        // Send each preference to the server
        for (String preference : preferences) {
            out.println(preference);
        }
        out.println("end");
        out.flush();
    }

    public void receiveAssignment(Consumer<String> listener) {
        assignmentListener = listener;
        // Check if the listener thread is already running
        if (listenerThread == null || !listenerThread.isAlive()) {
            // If not, create a new thread for listening to assignment messages
            listenerThread = new Thread(() -> {
                try {
                    String assignment;
                    while ((assignment = in.readLine()) != null) {
                        if (assignment.startsWith("You are assigned to ") || assignment.startsWith("Preferred")) {
                            // Pass the assignment message to the listener
                            assignmentListener.accept(assignment);
                        }
                    }
                    // If no assignment is received, pass a message to the listener
                    assignmentListener.accept("No assignment received");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            listenerThread.start();
        }
    }

    public void disconnect() {
        try {
            // Send "disconnect" command to the server
            out.println("disconnect");
            // Flush the output stream to ensure the command is sent
            out.flush();
            // Receive acknowledgment from the server
            String response = in.readLine();
            if (response != null && response.equals("disconnect_ack")) {
                System.out.println("Disconnect acknowledgment received from server.");
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
