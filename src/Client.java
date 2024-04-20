import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientNumber;
    private boolean running;

    public void createConnection() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 3169);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String serverMessage = in.readLine();
            System.out.println(serverMessage);
            clientNumber = in.readLine();
            System.out.println("Client number: " + clientNumber);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitResults(List<String> preferences) {
        out.println("submit");
        for (String preference : preferences) {
            out.println(preference);
        }
        out.println("end");
        out.flush();
    }

    public String receiveAssignment() {
        try {
            String assignment;
            while ((assignment = in.readLine()) != null) {
                System.out.println(assignment);
                if (assignment.startsWith("You are assigned to ")) {
                    return assignment;
                }
            }
            return "No assignment received";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            out.println("disconnect");
            out.flush();
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
