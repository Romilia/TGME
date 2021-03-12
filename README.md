# TGME
# Java version 14.0.2

# When the Jar file is run, the application should run on terminal.
# There will be three options which the user can select thorugh inputting numbers and pressing enter.
# The three main menu options are Candy Crush, Bejeweled, and Player stats.

## If you enter the corresponding number for Candy Crush, you will be taken to a screen where two players shall sequentially play Candy Crush.
# Player one will finish level one, then player two will finish level one, and so on until level 5 is complete.
# The objective of each level is to meet the target score before your number of moves to make run out.
# Here are the special tiles in Candy Crush:
# 4 of the same tiles in a row or column form a rocket.
# Rocket (Tile: #): Removes all tiles in the direction of the tile the selected tile is swapping with. For example, if swapping with the top tile, either the selected tile or the swapped tile is a #, all the tiles in that column will get removed.
# if two rocket tiles are swapping with each other, all tiles in the same column and row of the swapped tile will be removed.

# 5-6 of the same tiles form a bomb. Must be a L , 7, + or T shape.
# Bomb (Tile: !): Removes all surrounding tiles within one tile of the piece it is swapped with.

# 7 or more of the same tiles form a chocolate sprinkle.
# Chocolate Sprinkle (Tile: *): If the selected tile is * and swaps with another tile, all the swapped with tile will get removed.
# if two chocolate sprinkle tiles are swapping with each other, all tiles on the board will get removed.
# if the swapped with tile is bomb(!) or rocket(#), the special tiles will take in effect.

## If you enter the corresponding number for Bejeweled, you will be taken to a screen where two players will take turn to play each level. 
# The objective of each level is to meet the target score before your timer runs out.
# Player 1 will start playing level 1 and when time is up, Player 2 will play level 2, and so on until level 5 is complete.
# Each level has a default time of 45 seconds.
# The player shall be given additional time for their next level if they meet their target score before their time runs out for the current level.


## If you click player stats you will be taken to a screen that displays the high scores of all players that exist in the system.
