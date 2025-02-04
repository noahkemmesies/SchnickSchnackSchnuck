package client;

public class SchnickSchnackSchnuck {
    Connection connection;

    public SchnickSchnackSchnuck() {
        this.connection = new Connection(this);
        mainMenu();
    }

    private void mainMenu() {
        if (connection.connectToLocal()) {
            connection.start();
        }
        System.out.println("Geben Sie \"Schere\", \"Stein\" oder \"Papier\" ein:");
    }

    public void receive(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        new SchnickSchnackSchnuck();
    }
    /*
    public static void main(String[] args) {
        Connection connection = new Connection();
        System.out.println("Geben Sie \"Schere\", \"Stein\" oder \"Papier\" ein:");
    }*/
}