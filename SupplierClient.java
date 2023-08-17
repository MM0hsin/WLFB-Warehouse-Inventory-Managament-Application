//import jdk.jfr.TransitionFrom;

import java.io.*;
import java.net.*;
import java.security.spec.ECField;

public class SupplierClient {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket SupplierClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int SupplierSocketNumber = 4545;
        String SupplierServerName = "localhost";
        String SupplierClientID = "SupplierClient";

        try {
            SupplierClientSocket = new Socket(SupplierServerName, SupplierSocketNumber);
            out = new PrintWriter(SupplierClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(SupplierClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + SupplierSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + SupplierClientID + " client and IO connections");

        System.out.println("Hello Supplier! What would you like to do? Check_stock, Add_apples, Add_oranges");
        int counter = 0;
        while (true) {
            fromUser = stdIn.readLine();
            try {
                if (fromUser != null && counter == 0) {
                    if (fromUser.equalsIgnoreCase("Check_stock") || fromUser.equalsIgnoreCase("Add_apples")
                            || fromUser.equalsIgnoreCase("Add_oranges")) {
                        out.println(fromUser);
                        counter++;
                        fromServer = in.readLine();
                        System.out.println("Server: " + fromServer);
                    } else {
                        System.out.println("please choose right input! Check_stock, Add_apples, Add_oranges");
                    }
                } else if (Integer.parseInt(fromUser) > 0 && counter > 0) {
                    out.println(fromUser);
                    counter--;
                    fromServer = in.readLine();
                    System.out.println("Server: " + fromServer);
                }
            }catch (Exception e){
                out.println(fromUser);
                fromServer = in.readLine();
                System.out.println("Server: " + fromServer);
            }
        }
    }
}
