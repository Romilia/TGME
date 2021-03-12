package Run;

import Main.Main;
import CandyCrushMain.CandyCrush;
import BejeweledMain.Bejeweled;

import java.util.Scanner;

public class Runnable {
    Main mn = new Main();
    private Scanner scan = new Scanner(System.in);

    public Runnable() {
        menuChoice();
    }

    public void menuChoice() {
        boolean cont = true;

        do {
            int choice;

            System.out.println("Make a Selection");
            System.out.println("1. Bejeweled");
            System.out.println("2. Candy Crush");
            System.out.println("3. View Player Stats");
            System.out.println("4. Exit");
            choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 1:
                    Bejeweled bej = new Bejeweled();
                    mn.setGame(bej);
                    cont = bej.startGame();
                    break;
                case 2:
                    CandyCrush crush = new CandyCrush();
                    mn.setGame(crush);
                    cont = crush.startGame();
                    break;
                case 3:
                    if (mn.existingPlayers.isEmpty()) {
                        System.out.println("There are no players");
                    } else {
                        System.out.println("Enter the player's name:");
                        String name = scan.nextLine();
                        mn.viewPlayer(name);
                    }
                    break;
                case 4:
                    cont = false;
                    break;
            }
        } while (cont);
    }


    public static void main(String[] args) {
        Runnable run = new Runnable();
    }
}
