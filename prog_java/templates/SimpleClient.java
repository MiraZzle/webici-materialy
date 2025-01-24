import java.io.*;
import java.net.*;

public class SimpleClient {
    public static void main(String[] args) {
        String host = "127.0.0.1"; // Server IP
        int port = 12345; // Server Port

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to the server.");

            // Input and output streams for communication
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Send messages to the server
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String text;

            System.out.println("Enter a message (type 'bye' to quit):");
            while ((text = consoleReader.readLine()) != null) {
                writer.println(text); // Send message to server
                String response = reader.readLine(); // Read server response
                System.out.println("Server replied: " + response);

                if (text.equalsIgnoreCase("bye")) {
                    System.out.println("Closing connection...");
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
