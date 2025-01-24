import java.io.*;
import java.net.*;

public class SimpleServer {
    public static void main(String[] args) {
        int port = 12345; // Port number
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Accept a single client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Input and output streams for communication
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            // Read and respond to the client
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Client says: " + message);
                writer.println("Server received: " + message);

                // Exit the server loop if client says "bye"
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Connection closing...");
                    break;
                }
            }

            socket.close();
            System.out.println("Client disconnected.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
