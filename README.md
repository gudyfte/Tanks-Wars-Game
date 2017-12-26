This game is developed in Java. This game is about two tanks fire each other. This game is a two player game. Both players can play from the same computer. Player one represents first tank. Player two represents second tank.

In the github repository, I provided six java files which were called TankBullet.java, MapBackground.java, Walls.java, DrawWalls.java, Tank.java, and tanksGame.java. These six java files were saved in the TanksGame folder. The tanksGame.java provided main function which can compile the program for the tank game. There were many png files and wav files which were saved in the TanksGame/Pictures folder. These png files and wav files are used in the tanksGame code. There were other two java files, which were called KeyboardControl.java and KeyboardEvents.java,were saved in the TanksGame/GameKeyControl folder. The implementation of this game project was to upload png files, upload wav files, place images on the game map, play music and allow players to control two tanks from keyboard when the game is begun.

The program can be compiled and executed on the command prompt.

Command line instructions to compile and execute tank game:

1st step (compile program): javac TanksGame/MapBackground.java TanksGame/TankBullet.java TanksGame/Walls.java TanksGame/DrawWalls.java                               TanksGame/Tank.java TanksGame/tanksGame.java

2nd step (execute program): java TanksGame/tanksGame

How to play the game: Both players use same computer keyboard to control two tanks.

Player One controls are:
* A: Rotate left
* D: Rotate right
* W: Move forward
* S: Move in reverse
* Space: Fire weapon

Player Two controls are:
* Left Arrow: Rotate left
* Right Arrow: Rotate right
* Up Arrow: Move forward
* Down Arrow: Move in reverse
* Return: Fire weapon

In this game, each tank has two lives opportunities. The bullet is tank's weapon. Player one and Player Two can press the key "Space" and the key "Return" to control two tanks to launch bullets to fire each other. When first tank's bullets hit second tank, first tank's scores will be increased. If first tank defeats second tank, second tank will only remain one life opportunity. The game will be started automatically. If second tank doesn't have remaining life in the game, the game will be finished and the game screen will display first tank's scores.

Please open above documentation.docx I attached for more details about this game!
