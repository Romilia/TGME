# TGME

## Java Version
adopt-openjdk-14.0.2

## Usage
When the Jar file is run, the application should run on terminal.

## Instructions
#### Main Menu
- There will be three options which the user can select through inputting numbers and pressing enter.
The four main menu options are Candy Crush, Bejeweled, Player stats, and Exit.
    ```
    Make a Selection
    1. Bejeweled
    2. Candy Crush
    3. View Player Stats
    4. Exit
    ```
  
#### Candy Crush

- If you enter the corresponding number for Candy Crush, you will be taken to a screen where two players shall sequentially play Candy Crush.
- Player one will finish level one, then player two will finish level one, and so on until level 5 in complete.
- The objective of each level is to meet the target score before your number of turns run out.
- Each level, each player will be given 3 hints.
  

- Here are the special tiles in Candy Crush:
  
  - **Rocket (Tile: #)**: Removes all instances of the specific piece in the direction it is being swapped with from the board.
      - For example, if the selected tile is # and the direction it is swapping with is left, all the tiles in the same row will get removed.
      - If two rocket tiles are swapping with each other, all tiles in the same column and row of the swapped tile will be removed.
      - 4 of the same tiles form a rocket(#) tile.
  - **Bomb (Tile: !)**: Removes all surrounding tiles within one tile of the piece it is swapped with.
    -  5-6 of the same tiles form a bomb, must have 3 of the same tiles in row or column.
    -  In T shape, 7 shaped, L shape, + shape. One exception is five of the same tiles in row or column.
  - **Chocolate Sprinkle (Tile: \* )**: If the selected tile is * and the swapped with tile is G, then all the G tiles will get removed.
    - If two chocolate sprinkle tiles are swapping with each other, all tiles on the board will get removed.
    - If swapping with a special tile, all the special tiles will take in to effect.
    - 7 or more of the same tiles will form a * tile.

######Preview: First Board
```
>>>CANDY CRUSH LEVEL 1<<<
NUMBER OF MOVES AVAILABLE: 10
TARGET SCORE: 5

PLAYER TURN: Player 1

Moves left: 10
Current Score: 0
Hint:3
  -- 0 -- 1 -- 2 -- 3 -- 4 --
0 |  Y    Y    A    A    O  |
1 |  O    Y    P    Y    P  |
2 |  A    P    A    P    D  |
3 |  D    O    Y    O    O  |
4 |  P    O    O    Y    D  |
   -------------------------
Do you want to use the hint?(Yes/No):

```
#### Bejeweled
- If you enter the corresponding number for Bejeweled, you will be taken to a screen where two players shall sequentially play.
- The objective of each level is to meet the target score before your timer runs out.
- The player shall be given additional time for their next level if they meet their target score before their time runs out for the current level.

######Preview: First Board
```
>>>BEJEWELED LEVEL 1<<<
TARGET SCORE: 5
TIMER STARTED AT: 10

PLAYER TURN: Player 1

Time Left: 9
Current Score: 0
  -- 0 -- 1 -- 2 -- 3 -- 4 --
0 |  B    R    B    R    G  |
1 |  G    B    Y    B    Y  |
2 |  B    G    B    G    Y  |
3 |  B    Y    R    B    R  |
4 |  R    G    B    B    R  |
   -------------------------
Enter the row of the position: 
```

#### Player Stats
- . This displays the previous game history of all the players in the TMGE.
```
Number of games played: 2
Game:Bejeweled
Player Andres's Score:3
Player Itzel's Score:3

Game:Bejeweled
Player Andres's Score:11
Player Itzel's Score:3
```
