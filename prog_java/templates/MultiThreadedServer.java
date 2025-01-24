import java.io.*;
import java.net.*;

public class MultiThreadedServer {
    public static void main(String[] args) {
        int port = 12345; // Port number
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                // Accept a new client connection
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Create a new thread for the client
                new ClientHandler(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Input and output streams for communication
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Client says: " + message);

                // Respond to the client
                writer.println("Server received: " + message);

                // Exit the loop if client sends "bye"
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Client disconnected");
                    break;
                }
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Error handling client: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
