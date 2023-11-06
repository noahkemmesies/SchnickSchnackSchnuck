package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {

    private final  Server server;
    private final Socket client;
    private final DataInputStream input;
    private final DataOutputStream output;


    public Connection(Server server, Socket client) {
        this.server = server;
        this.client = client;
        try {
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {server.log(3, "{Connection: "+client.getRemoteSocketAddress()+"}: "+e.getMessage());throw new RuntimeException(e);} //why is "throw new RuntimeException(e);" needed?
    }


    public Socket getClient() {return this.client;}

    public void run() {
        while (true) {
            try {
                String test = input.readUTF();
                System.out.println(test);
            } catch (IOException e) {server.log(3, "{Connection: "+client.getRemoteSocketAddress()+" run}: "+e.getMessage());}
        }
    }
}