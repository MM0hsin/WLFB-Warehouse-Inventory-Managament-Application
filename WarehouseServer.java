import java.net.*;
import java.io.*;

public class WarehouseServer {
    public static void main(String[] args) throws IOException{

        ServerSocket WarehouseServerSocket = null;
        boolean listening = true;
        String WarehouseServerName = "Warehouse Server";
        int WarehouseServerNumber = 4545;

        double apples = 1000;
        double oranges = 1000;

        SharedWarehouseState sharedFruit = new SharedWarehouseState(apples, oranges);

        try{
            WarehouseServerSocket = new ServerSocket(WarehouseServerNumber);
        } catch (IOException e){
            System.err.println("Could not start " + WarehouseServerName + " on specified port");
            System.exit(-1);
        }
        System.out.println(WarehouseServerName + " started");

        while (listening){
            new WarehouseServerThread(WarehouseServerSocket.accept(), "WarehouseServerThread1", sharedFruit).start();
            System.out.println("New "+ WarehouseServerName + " thread started");
        }
        WarehouseServerSocket.close();
    }
}
