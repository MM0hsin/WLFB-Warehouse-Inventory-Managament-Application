import java.net.*;
import java.io.*;

public class WarehouseServerThread extends Thread{

    private  Socket warehouseSocket = null;

    private SharedWarehouseState mySharedWarehouseStateObject;

    private String myWarehouseServerThreadName;
    private  double mySharedVariable;

    public WarehouseServerThread(Socket warehouseSocket, String WarhouseServerThreadName, SharedWarehouseState SharedObject){

        this.warehouseSocket = warehouseSocket;
        mySharedWarehouseStateObject = SharedObject;
        myWarehouseServerThreadName = WarhouseServerThreadName;
    }

    public void run(){
        try {
            System.out.println(myWarehouseServerThreadName + " initialising.");
            PrintWriter out = new PrintWriter(warehouseSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(warehouseSocket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null){
                try {
                    mySharedWarehouseStateObject.aquireLock();
                    outputLine = mySharedWarehouseStateObject.processInput(myWarehouseServerThreadName, inputLine);
                    out.println(outputLine);
                    if (outputLine.contains("Going home...") || outputLine.contains("Apples: ")) {
                        mySharedWarehouseStateObject.releaseLock();
                    }
                }catch (InterruptedException e){
                    System.err.println("Failed to get lock when reading: "+e);
                }
            }
            out.close();
            in.close();
            warehouseSocket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
