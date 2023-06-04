import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter operand 1: ");
        int operand1 = scanner.nextInt();

        System.out.print("Enter operand 2: ");
        int operand2 = scanner.nextInt();

        // Send operands to the server
        out.println(operand1 + " " + operand2);

        // Get result from the server and display it
        String result = in.readLine();
        System.out.println("Result: " + result);

        out.close();
        in.close();
        scanner.close();
        socket.close();
    }
}
