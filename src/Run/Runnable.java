package Run;

import Main.Main;
import Main.Game;
import CandyCrushMain.CandyCrush;
import BejeweledMain.Bejeweled;
import Player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Runnable {
    Main mn = new Main();
    private Scanner scan = new Scanner(System.in);

    public Runnable()
    {
        menuChoice();
    }

    public void menuChoice()
    {
        boolean cont = true;

        do
        {
            System.out.println("Make a Selection");
            System.out.println("1. Bejeweled");
            System.out.println("2. Candy Crush");
            System.out.println("3. View Player Stats");
            int choice = scan.nextInt();
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
                    System.out.println("Enter the player name:");
                    String name = scan.nextLine();
                    mn.viewPlayer(name);
                    break;
            }
        }while(cont);
    }


    public static void main(String[] args) {
        Runnable run = new Runnable();
    }
}
