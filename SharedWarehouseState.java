import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SharedWarehouseState {
    private SharedWarehouseState mySharedObj;
    private  String myThreadName;
    private  double mySharedApples;
    private double mySharedOranges;
    private boolean accessing=false;
    private String accessThread;
    private int threadsWaiting=0;

    private static final int WAITING = 0;

    private static final int ADDAPPLES = 3;
    private static final int ADDORANGES = 4;
    private static final int REMOVEAPPLES = 5;
    private static final int REMOVEORANGES = 6;

    private int state = WAITING;

    SharedWarehouseState(double SharedApples, Double SharedOranges){
        mySharedApples = SharedApples;
        mySharedOranges = SharedOranges;
    }

    public synchronized void aquireLock() throws InterruptedException{
        Thread me = Thread.currentThread();
        System.out.println(me.getName()+ " is attempting to acquire a Lock!");
        ++threadsWaiting;
        while (accessing){
            if (!checkLock()) {
                System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
                wait();
            }else{
                break;
            }
        }
        --threadsWaiting;
        accessing = true;
        accessThread = me.getName();
        System.out.println(me.getName()+ " got a lock!");
    }
    public synchronized void releaseLock(){
        accessing = false;
        notifyAll();
        Thread me = Thread.currentThread();
        System.out.println(me.getName()+" release a lock!");
    }
    public synchronized boolean checkLock() {
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName() + " checking if it has already acquired a lock");
        if (accessThread.equals(me.getName())){
            return true;
        }
        return false;
    }

    public synchronized String processInput(String myThreadName, String theInput){
        System.out.println(myThreadName+ " recieved "+ theInput);
        String theOutput = null;
        switch (state){
            case WAITING:
                if (theInput.equalsIgnoreCase("Check_stock")) {
                    theOutput = "Apples: " + mySharedApples + " Oranges: " + mySharedOranges ;
                    state = WAITING;
                } else if (theInput.equalsIgnoreCase("Add_apples")) {
                    theOutput = "how many would you like to add?";
                    state = ADDAPPLES;
                } else if (theInput.equalsIgnoreCase("Add_oranges")) {
                    theOutput = "how many would you like to add?";
                    state = ADDORANGES;
                } else if (theInput.equalsIgnoreCase("Buy_apples")) {
                    theOutput = "How many would you like to buy?";
                    state = REMOVEAPPLES;
                }else if (theInput.equalsIgnoreCase("Buy_oranges")) {
                    theOutput = "How many would you like to buy?";
                    state = REMOVEORANGES;
                }else{
                    theOutput = "Please choose the right option";
                }
                break;
            case ADDAPPLES:
                try{
                    if (theInput != null && Integer.parseInt(theInput)  > 0) {
                        mySharedApples = mySharedApples + Integer.parseInt(theInput);
                        theOutput = "You have added " + theInput + " apples! Total Apples: " +
                                mySharedApples + ". Going home...";
                        state = WAITING;
                    }
                }catch (Exception e){
                    theOutput = "Please type an integer above 0";
                }
                break;
            case ADDORANGES:
                try {
                    if (theInput != null && Integer.parseInt(theInput)  > 0) {
                        mySharedOranges = mySharedOranges + Integer.parseInt(theInput);
                        theOutput = "You have added " + theInput + " oranges! Total oranges: " +
                                mySharedOranges + ". Going home...";
                        state = WAITING;
                    }
                }catch (Exception e) {
                    theOutput = "Please type an integer above 0";
                }
                break;
            case REMOVEAPPLES:
                try {
                    if (theInput != null && Integer.parseInt(theInput)  > 0) {
                        mySharedApples = mySharedApples - Integer.parseInt(theInput);
                        theOutput = "You have bought " + theInput + "apples! Apples left:" +
                                mySharedApples + ". Going home...";
                        state = WAITING;
                    }
                }catch (Exception e){
                    theOutput = "Please type an integer above 0";
                }
                break;
            case REMOVEORANGES:
                try {
                    if (theInput != null && Integer.parseInt(theInput)  > 0) {
                        mySharedOranges = mySharedOranges - Integer.parseInt(theInput);
                        theOutput = "You have bought " + theInput + " Oranges! Oranges left: " +
                                mySharedOranges + ". Going home...";
                        state = WAITING;
                    }
                }catch (Exception e){
                    theOutput = "Please type an integer above 0";
                }
                break;
        }
        return theOutput;
    }
}

