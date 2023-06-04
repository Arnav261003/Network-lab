import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println("Connection successful");

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received message: " + inputLine);

            // Split the message into two operands
            String[] operands = inputLine.split(" ");
            int operand1 = Integer.parseInt(operands[0]);
            int operand2 = Integer.parseInt(operands[1]);

            // Perform multiplication
            int result = operand1 * operand2;

            // Send result back to the client
            out.println(result);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
