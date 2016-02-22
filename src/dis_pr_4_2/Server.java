package dis_pr_4_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) {
        int clientport=1234,serverport=1235;
        System.out.println("Server is running");
        MessageReplier msgreplier =  new MessageReplier(clientport, serverport,"127.0.0.1");
        msgreplier.start();
    }
}
class MessageReplier extends Thread {
    
    ServerSocket server;
    int clientport,serverport;
    String hostname;
    public MessageReplier(int clientport,int serverport,String hostname){
        this.clientport=clientport;
        this.serverport=serverport;
        this.hostname=hostname;
        try {
            server = new ServerSocket(serverport);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @Override
    public void run() {
        Socket serversocket;
        try {
            while((serversocket = server.accept()) != null){
                String line = new DataInputStream(serversocket.getInputStream()).readUTF().toString();
                if(line != null){
                        System.err.println("Received from " +clientport +" is " +line);
                        Socket s = new Socket(hostname, clientport);
                        new DataOutputStream(s.getOutputStream()).writeUTF(line);
                        s.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

