package server;

public class Login {
    public int newClient(Server server, Connection c, int clientCount) {
        if (clientCount == 0) {
            c.write("wait");
            return 1;
        }
        if (clientCount == 1) {
            c.write("start");
            server.handleCommunication(c, "start");
        }
        if (clientCount >= 2) {
            c.write("too many connections");
            return 2;
        }
        return 0;
    }
}
