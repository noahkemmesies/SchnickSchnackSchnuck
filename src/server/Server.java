package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.StandardSocketOptions;
import java.time.LocalTime;
import java.util.ArrayList;

public class Server {
    private ServerSocket server;
    private final ArrayList<Connection> clients;
    private final ArrayList<Thread> clientThreads;


        public Server(int port) {
            try {
                server = new ServerSocket(port);
                //server.setSoTimeout(1000000000);
            } catch (IOException e) {log(3, "{Server}: "+e.getMessage());}
            clients = new ArrayList<>();
            clientThreads = new ArrayList<>();
            log(1,"{Server}: Server ist auf Port " + server.getLocalPort() + " gestartet und wartet auf clients.");
            connect();
        }


        private void connect() {
            while (true) {
                try {
                    Connection c = new Connection(this, server.accept());
                    clients.add(c);
                    Thread t = new Thread(c);
                    clientThreads.add(t);
                    t.start();
                    log(1, "{Server connect}: Client " + clients.indexOf(c) + ", " + c.getClient().getRemoteSocketAddress() +" just connected and is ready for communication.");
                } catch (IOException e) {log(3, "{Server connect}: "+e.getMessage());}
            }
        }

        public void log(int type, String log) { //Formatierung: "{Class method}: Message"
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

    public static void main(String [] args) {
        new Server(5000);
    }
}