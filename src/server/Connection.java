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
            String message = readSmart();
            if (message.equals("~~disconnected~~")) {
                break;
            } else {
                if (message.equalsIgnoreCase("schere") || message.equalsIgnoreCase("stein") || message.equalsIgnoreCase("papier")) {
                    server.handleCommunication(this, message);
                } else {
                    server.log(3, "{Connection: "+client.getRemoteSocketAddress()+"}: invalid message for: " + message);
                }
            }
        }
    }

    public String readSmart() {
        String temp = read();
        if (temp.equals("~~disconnect~~") && disconnect()) {
            return "~~disconnected~~";
        }
        return temp;
    }

    public String read() {
        if (client != null && client.isConnected()) {
            try {
                return input.readUTF();
            } catch (IOException e) {server.log(3, "{Connection: "+client.getRemoteSocketAddress()+" read}: "+e.getMessage());}
        } else {
            server.log(2, "{Connection: "+ (client != null ? client.getRemoteSocketAddress() : "unknown RemoteSocketAddress") +" read}: Not connected to Server can't read!");
        }
        return "";
    }

    public void write(String text) {
        if (client != null && client.isConnected()) {
            try {
                output.writeUTF(text);
            } catch (IOException e) {server.log(3, "{Connection: "+client.getRemoteSocketAddress()+" write}: "+e.getMessage());}
        } else {
            server.log(2, "{Connection: "+ (client != null ? client.getRemoteSocketAddress() : "unknown RemoteSocketAddress") +" write}: Not connected to Server can't write!");
        }
    }

    public boolean disconnect() {
        try {
            client.close();
            server.log(1, "{Connection: "+client.getRemoteSocketAddress()+" disconnect}: successfully disconnected");
            return true;
        } catch (IOException e) {server.log(3, "{Connection: "+client.getRemoteSocketAddress()+"  disconnect}: "+e.getMessage());}
        return false;
    }
}