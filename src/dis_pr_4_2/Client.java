package dis_pr_4_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        //client listen on port 1234
        int clientport=1234,serverport=1235;
        MessageListener msglistener = new MessageListener(clientport);
        msglistener.start();
        //it transmits on localhost:1235
        //server listen on 1235
         System.out.println("Enter String that will be wchoed by server : ");
        while(true)
        {
               
                Scanner in = new Scanner(System.in);
                String msg = in.next();

                if(msg!=null)
                {
                    MessageTransmitter msgtrasnmitter = new MessageTransmitter(msg ,"127.0.0.1", serverport);
                    msgtrasnmitter.start();
                }
                else
                    System.out.println("Enter something.");
        }
    }
}

class MessageListener extends Thread {
    
    ServerSocket server;
    int port = 8877;
    
    public MessageListener(int port){
        this.port = port;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @Override
    public void run() {
        Socket clientSocket;
        try {
            while((clientSocket = server.accept()) != null){
                String line = new DataInputStream(clientSocket.getInputStream()).readUTF().toString();
                if(line != null){
                        System.out.println("Received : "+line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class MessageTransmitter extends Thread {

    String message, hostname;
    int port;

    public MessageTransmitter(String message,String hostname, int port) {
        this.message = message;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket s = new Socket(hostname, port);
            new DataOutputStream(s.getOutputStream()).writeUTF(message);
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
