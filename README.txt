
Run Game.java to play! Path: AliceJava/org/src/Game.java

=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- Core concepts, the features they implement, and why each feature
  is an appropriate use of the concept.

  1. 2D Arrays: I use a int 2D array to represent the game board.
     0 = empty. 1 = enemy. 2 = player. 3 = treasure. 4 = door.
     This makes it simple to display the game board, so when I call paintComponent,
     if the square is empty I dont add anything, if theres an enemy it displays a black circle,
     if the player is there it displays a white circle, if there is a treasure it displays a gold
     box, if it is the door it displays a dark purple circle.

  2. File I/O: The player can press quick save and save all the game's current state. This includes
     the player's state, the rooms state, and more. Then when the player clicks load save it will
     restore the game state as it was when the file was saved. It catches errors and will print "error".
     I made the file save strings because they could be converted into ints and booleans, and I needed
     to save strings, ints, and booleans.

  3. Inheritance and Subtyping: The Player class and Monster class extends Creature. Player and Monster
     share attributes such as health, attack damage, and position on the board. However, they have
     significant differences. Player needs number of potions, number of kills, and methods like
     getTreasure() and usePotion(). Monster needs a linked attack variable.
     I use dynamic dispatch because the damage() methods in Player and Monster are significantly different.

  4. JUnit Testable Component: JUnit tests are being used, Code designed to be unit-testable
                               Separation of concern, My model does not rely on GUI components
                               I test by calling methods. I use correct assertions (assertTrue, assertEquals)
                               No two tests do the same thing. I test edge cases

=========================
=: Implementation :=
=========================

- Overview of each of the classes and what their
  function is in the overall game.

  Creature: stores health and attack damage and position.
  Player: stores the player's number of potions and number of kills.
          Has getTreasure() and usePotion() and damage() for those events.
          when instantiated, creates a player with health 30, attack damage 3,
          default position, no potions or kills.
          Has Player(java.util.List<String> p) to make the player be in the same state as the save file.
  Monster: creates a monster with a random linked attack A B or C.
           has a method for when the monster is damaged
  Game: Main method run to start and run the game. Initializes the runnable game
        class of my choosing and runs it.
  Room: Has numEnemies, numTreasure, rmLvl, and a room array. Has method create room which
        makes a room with the given level, randomizing the number of enemies and treasure.
        Has method playerMove() so that when a player moves onto a square, the status will change
        depending on what was on the square they moved on, and the board will change the player's
        position.
        Generates a monster if the square the player moved onto had one.
        Has Room(java.util.List<String> p) to make the room be in the same state as the save file.
  DungeonBoard: Has KeyListeners for when the player moves or attacks or uses a potion.
                Has methods for how to reset the board, how to save and load.
                Has tick method which always checks if the player is still alive.
                Paints the board in checkered pattern. Paints the enemies, treasures, player, and door.
                Updates the strings for what should be displayed on the UI for kills, health, level,
                and the two statuses.
  RunDungeon: Creates the displayed ui window. Displays the values for kills, health, level and the two statuses.
              Has the buttons for reset, quick save, load save, and instructions, which each have an action
              listener that activates the respective functions when the button is pressed.
              Has the text for the instruction window.
              Sets colors and fonts and sizes of display.

- Significant stumbling blocks

  I kept on having an issue where when I quick saved and then moved and then loaded the save, I would have two
  players on the screen. I realized after a long time that it was because in the Player(java.util.List<String> p)
  method I was creating a new player. Instead of making a new player I made all the values in player change.

  Same for the room, I used to make a new room for when I loaded the save and was confused why it was so different
  from the save file. I realized it was because I would call createRoom(i) which would make a new randomized room.


- Design Evaluation

  I think there is a good separation of functionality. Maybe I could have created a new class for Instructions
  to set the color and text and window size etc.

  My private state is not encapsulated the best because I ended up making my status labels public so that I could
  change them easier. With more time maybe I would have made everything private and used get and set functions.
