package client;

import java.io.Console;

public class SchnickSchnackSchnuck {
    Connection connection;
    String input;

    public SchnickSchnackSchnuck() {
        this.connection = new Connection(this);
        mainMenu();
    }

    private void mainMenu() {
        if (connection.connectToLocal()) {
            connection.start();
        }

        Console console = System.console();
        char[] input_array =  console.readPassword("Geben Sie \"Schere\", \"Stein\" oder \"Papier\" ein:");
        this.input = new String(input_array);

        switch (input.toLowerCase()) {
            case "schere":
                connection.write(input);
                break;

            case "stein":
                connection.write(input);
                break;

            case "papier":
                connection.write(input);
                break;
        
            default:
                System.out.println("Ungültige Eingabe!");
                break;
        }

    }

    public void receive(String message) {
        System.out.println(ergebnisAusgabe(message, input));
    }

    public String ergebnisAusgabe(String message, String input){
        switch (input.toLowerCase()) {
            case "schere":
                if(message.equalsIgnoreCase("papier")){
                    return "Dein Gegner spielte " + message + ". Du hast GEWONNEN";
                } else if(message.equalsIgnoreCase("stein")){
                    return "Dein Gegner spielte " + message + ". Du hast VERLOREN";
                } else if(message.equalsIgnoreCase("schere")){
                    return "Dein Gegner spielte " + message + ". UNENTSCHIEDEN";
                }
                return "Fehler bei der Verarbeitung der Eingabe des Gegners!";

            case "stein":
                if(message.equalsIgnoreCase("papier")){
                    return "Dein Gegner spielte " + message + ". Du hast VERLOREN";
                } else if(message.equalsIgnoreCase("stein")){
                    return "Dein Gegner spielte " + message + ". UNENTSCHIEDEN";
                } else if(message.equalsIgnoreCase("schere")){
                    return "Dein Gegner spielte " + message + ". Du hast GEWONNEN";
                }
                return "Fehler bei der Verarbeitung der Eingabe des Gegners!";

            case "papier":
                if(message.equalsIgnoreCase("papier")){
                    return "Dein Gegner spielte " + message + ". UNENTSCHIEDEN";
                } else if(message.equalsIgnoreCase("stein")){
                    return "Dein Gegner spielte " + message + ". Du hast GEWONNEN";
                } else if(message.equalsIgnoreCase("schere")){
                    return "Dein Gegner spielte " + message + ". Du hast VERLOREN";
                }
                return "Fehler bei der Verarbeitung der Eingabe des Gegners!";
        
            default:
                return "Fehler bei der Verarbeitung der Eingabe des Gegners!";
        }
    }

    public static void main(String[] args) {
        new SchnickSchnackSchnuck();
    }

}