package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;

public class Connection {

    private Socket server;
    private DataInputStream input;
    private DataOutputStream output;


    public Connection() {

    }


    public String read() {
        try {
            return input.readUTF();
        } catch (IOException e) {log(3, "{Connection read}: "+e.getMessage());}
        return "";
    }

    public void write(String text) {
        try {
            output.writeUTF(text);
        } catch (IOException e) {log(3, "{Connection write}: "+e.getMessage());}
    }

    public boolean connectToLocal() {
        return connectTo("127.0.0.1", 5000);
    }

    public boolean connectTo(String ip, int port) {
        try {
            server = new Socket(ip, port);
            input = new DataInputStream(server.getInputStream());
            output = new DataOutputStream(server.getOutputStream());
        } catch (IOException e) {log(3, "{Connection connectTo}: "+e.getMessage());}

        return server.isConnected();
    }

    public void log(int type, String log) {
        String time = ""+ LocalTime.now();
        switch (type){
            case 1:
                System.out.println("Info["+ time.substring(0, 5) +"]: "+log);
                break;
            case 2:
                System.out.println("Warning["+ time.substring(0, 5) +"]: "+log);
                break;
            case 3:
                System.out.println("Error["+ time.substring(0, 5) +"]: "+log);
                break;
            default:
                System.out.println("Unknown log-type["+ time.substring(0, 5) +"]: "+log);
        }
    }
}