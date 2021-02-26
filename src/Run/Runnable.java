package Run;

import Main.Main;
import Main.Game;
import CandyCrushMain.CandyCrush;
import BejeweledMain.Bejeweled;
import Player.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Runnable {
    Main mn;
    private Scanner scan = new Scanner(System.in);
    private Game games[];

    private Game currentGame;
    private String names[] = new String[]{"BEJEWELED", "CANDY_CRUSH"};

    public Runnable()
    {
        menuChoice();
    }

    public void menuChoice()
    {
        System.out.println("Make a Selection");
        System.out.println("1. Bejeweled");
        System.out.println("2. Candy Crush");
        System.out.println("3. View Player Stats");
        int choice = scan.nextInt();
        scan.nextLine();


        switch (choice){
            case 1:
                mn = new Main(new Bejeweled());
                break;
            case 2:
                mn = new Main(new CandyCrush());
                break;
            case 3:
                System.out.println("Enter the player name:");
                String name = scan.nextLine();
                mn.viewPlayer(name);
                break;
        }

    }


    public static void main(String[] args) {
        Runnable run = new Runnable();
    }
}
