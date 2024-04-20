public class Test {
    public static void main(String[] args) {
        int numberOfClients = 3; // Define the number of clients to create
        int clientCounter = 0;

        // Create and start threads for each client
        while (clientCounter < numberOfClients) {
            // Create a new thread for each client
            Thread clientThread = new Thread(new UserInterface());
            clientThread.start();
            clientCounter++;
        }
    }
}
