package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;

public class Connection extends Thread {

    private Socket server;
    private DataInputStream input;
    private DataOutputStream output;
    private final SchnickSchnackSchnuck schnickSchnackSchnuck;


    public Connection(SchnickSchnackSchnuck schnickSchnackSchnuck) {
        this.schnickSchnackSchnuck = schnickSchnackSchnuck;
    }

    public void run() {
        while (true) {
            String temp = read();
            try {
                if (temp == null) {
                    Thread.sleep(500);
                } else {
                    schnickSchnackSchnuck.receive(temp);
                }
            } catch (InterruptedException e) {
                log(3, "{Connection run}: "+e.getMessage());
            }
        }
    }


    public String read() {
        if (server != null && server.isConnected()) {
            try {
                return input.readUTF();
            } catch (IOException e) {log(3, "{Connection read}: "+e.getMessage());}
        } else {
            log(2, "{Connection read}: Not connected to Server can't read!");
        }
        return "";
    }

    public void write(String text) {
        if (server != null && server.isConnected()) {
            try {
                output.writeUTF(text);
            } catch (IOException e) {log(3, "{Connection write}: "+e.getMessage());}
        } else {
            log(2, "{Connection write}: Not connected to Server can't write!");
        }
    }

    public boolean connectToLocal() {
        return connectTo("127.0.0.1", 5000);
    }

    public boolean connectTo(String ip, int port) {
        try {
            server = new Socket(ip, port);
            input = new DataInputStream(server.getInputStream());
            output = new DataOutputStream(server.getOutputStream());
            log(1, "{Connection connectTo}: Connected to Server: " + server.getRemoteSocketAddress() + ".");
        } catch (IOException e) {log(3, "{Connection connectTo}: "+e.getMessage());}

        return server != null && server.isConnected();
    }

    public boolean disconnect() {
        try {
            write("~~disconnect~~");
            server.close();
            log(1, "{Connection: disconnect}: successfully disconnected from: " + server.getRemoteSocketAddress() + ".");
            return true;
        } catch (IOException e) {log(3, "{Connection disconnect}: "+e.getMessage());}
        return false;
    }

    public void log(int type, String log) {
        String time = ""+ LocalTime.now();
        switch (type){
            case 1:
                System.out.println("Info ["+ time.substring(0, 5) +"]: "+log);
                break;
            case 2:
                System.out.println("Warn ["+ time.substring(0, 5) +"]: "+log);
                break;
            case 3:
                System.out.println("Error ["+ time.substring(0, 5) +"]: "+log);
                break;
            default:
                System.out.println("Unknown log-type ["+ time.substring(0, 5) +"]: "+log);
        }
    }
}