package server;

import java.net.Socket;

public class Connection implements Runnable {

    private final Socket client;

    public Connection(Socket client) {
        this.client = client;
    }


    public Socket getClient() {return this.client;}

    public void run() {
        while (true) {
            test();
        }
    }

    public void test(){
        System.out.println("test2");
    }
}