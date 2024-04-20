# UI for Assignment System

This user interface (UI) is designed to interact with the server-side application for managing assignments. It allows clients to submit their preferences for destinations and receive assignments from the server.

## Components

### Client Class

- **Socket Communication**: Manages socket communication with the server.
- **Submit Preferences**: Submits preferences to the server.
- **Receive Assignment**: Receives assignments from the server.
- **Disconnect**: Disconnects from the server.

### HomePage Class

- **UI Initialization**: Initializes the main UI components.
- **Destination Selection**: Allows clients to select up to 5 destinations.
- **Submit Button**: Triggers the submission of preferences to the server.
- **Result Display**: Displays the assignment result received from the server.

### UserInterface Class

- **Runnable Interface**: Implements the Runnable interface for concurrent execution.
- **Invoke Later**: Ensures UI components are accessed on the event dispatch thread.

### Test Class

- **Main Method**: Creates and starts multiple instances of the UserInterface class.

## Usage

1. **Compile**: Compile all Java files in the UI directory.
2. **Run**: Execute the Test class to launch multiple client UIs.
3. **Submit Preferences**: Select destinations and click the "Submit" button to send preferences to the server.
4. **Receive Assignment**: View the assignment result displayed on the UI.

## Dependencies

- **Java**: Ensure Java Development Kit (JDK) is installed to compile and run the UI.
- **Server**: Requires a running server application to communicate with.
