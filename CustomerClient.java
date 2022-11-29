import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CustomerClient {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket CustomerClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int CustomerSocketNumber = 4545;
        String CustomerServerName = "localhost";
        String CustomerClientID = "CustomerClient";

        try {
            CustomerClientSocket = new Socket(CustomerServerName, CustomerSocketNumber);
            out = new PrintWriter(CustomerClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(CustomerClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + CustomerSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + CustomerClientID + " client and IO connections");

        int counter = 0;
        System.out.println("Hello Customer A! what would you like to do? Check_stock, Buy_apples, Buy_oranges");
        while (true) {
            fromUser = stdIn.readLine();
            try {
                if (fromUser != null && counter == 0) {
                    if (fromUser.equalsIgnoreCase("Check_stock") || fromUser.equalsIgnoreCase("Buy_apples")
                            || fromUser.equalsIgnoreCase("Buy_oranges")) {
                        out.println(fromUser);
                        counter++;
                        fromServer = in.readLine();
                        System.out.println("Server: " + fromServer);
                    } else {
                        System.out.println("please choose right input! Check_stock, Buy_apples, Buy_oranges");
                    }
                } else if (Integer.parseInt(fromUser) > 0 && counter > 0) {
                    out.println(fromUser);
                    counter--;
                    fromServer = in.readLine();
                    System.out.println("Server: " + fromServer);
                }
            } catch (Exception e) {
                out.println(fromUser);
                fromServer = in.readLine();
                System.out.println("Server: " + fromServer);
            }

        }
    }
}
