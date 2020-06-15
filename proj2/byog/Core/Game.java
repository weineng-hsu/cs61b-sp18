package byog.Core;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static long seed;
    private TETile[][] gameBoard;
    private int playerPosX;
    private int playerPosY;
    private boolean newGame;
    private boolean startDrawGame;
    private boolean loadGame;
    private boolean winning;
    private String seedString;
    private StringBuilder seedSB = new StringBuilder();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.enableDoubleBuffering();
        welcomeAndReadSeed();
        if (newGame) {
            seed = readInputSeed(seedString);
            gameBoard = new World(seed, HEIGHT, WIDTH).getGrid();
            initialPlayer();
        } else if (loadGame) {
            gameBoard = loadWorld();
        }
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(gameBoard);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                playerMove(Character.toString(c));
                saveWorld(gameBoard);
                ter.renderFrame(gameBoard);
            }
            ter.renderFrame(gameBoard);
            showTileOnHover();
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {

        char checkMenu = Character.toLowerCase(input.charAt(0));
        switch (checkMenu) {
            case 'n':
                seed = readInputSeed(input);
                gameBoard = loadWorld();
                initialPlayer();
                break;
            case 'l':
                gameBoard = loadWorld();
                loadPlayerPos();
                break;
            case 'q':
                System.out.println("Come back later");
                System.exit(0);
                break;
            default:
        }
        playerMove(input);
        saveWorld(gameBoard);
        return gameBoard;
    }

    private void drawWelcomeBoard() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font header = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(header);
        StdDraw.text(0.5, 0.8, "CS61B: THE GAME");
        Font menu = new Font("Arial", Font.PLAIN, 20);
        StdDraw.setFont(menu);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.45, "Load Game (L)");
        StdDraw.text(0.5, 0.4, "Quit (Q)");
        if (newGame) {
            StdDraw.text(0.5, 0.35, "Seed: " + seedString);
            StdDraw.text(0.5, 0.3, "Press S to start the Game");
        }
        StdDraw.show();
    }

    private void drawWinBoard() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font header = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(header);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Congrats, YOU WON!!!! ");
        StdDraw.show();
        StdDraw.pause(5000);
        System.exit(0);
    }


    private TETile[][] loadWorld() {
        File f = new File("maze.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                gameBoard = (TETile[][]) os.readObject();
                os.close();
                return gameBoard;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        gameBoard = new World(seed, HEIGHT, WIDTH).getGrid();
        return gameBoard;
    }

    private long readInputSeed(String input) {
        StringBuilder seedBuilder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char toCheck = input.charAt(i);
            if (Character.isDigit(toCheck)) {
                seedBuilder.append(toCheck);
            }
        }
        return Long.parseLong(seedBuilder.toString());
    }

    private void playerMove(String input) {
        if (input == null) {
            System.out.println("no input given");
            System.exit(0);
        }
        for (int i = 0; i < input.length(); i++) {
            char dir = input.charAt(i);
            if (checkPlayerMove(dir)) {
                gameBoard[playerPosX][playerPosY] = Tileset.FLOOR;
                switch (dir) {
                    case 'w':
                        playerPosY += 1;
                        //System.out.println("UP");
                        break;
                    case 'a':
                        playerPosX -= 1;
                        //System.out.println("LEFT");
                        break;
                    case 's':
                        playerPosY -= 1;
                        //System.out.println("DOWN");
                        break;
                    case 'd':
                        playerPosX += 1;
                        //System.out.println("Right");
                        break;
                    default:
                        break;
                }
                checkWin();
                if (winning) {
                    drawWinBoard();
                } else {
                    gameBoard[playerPosX][playerPosY] = Tileset.PLAYER;
                }

            }
        }
    }

    private boolean checkPlayerMove(char dir) {
        switch (dir) {
            case 'w':
                return gameBoard[playerPosX][playerPosY + 1].equals(Tileset.FLOOR)
                        || gameBoard[playerPosX][playerPosY + 1].equals(Tileset.LOCKED_DOOR);
            case 'a':
                return gameBoard[playerPosX - 1][playerPosY].equals(Tileset.FLOOR)
                        || gameBoard[playerPosX - 1][playerPosY].equals(Tileset.LOCKED_DOOR);
            case 's':
                return gameBoard[playerPosX ][playerPosY - 1].equals(Tileset.FLOOR)
                        || gameBoard[playerPosX ][playerPosY - 1].equals(Tileset.LOCKED_DOOR);
            case 'd':
                return gameBoard[playerPosX + 1][playerPosY].equals(Tileset.FLOOR)
                        || gameBoard[playerPosX + 1][playerPosY].equals(Tileset.LOCKED_DOOR);
            default:
                return false;
        }
    }

    private void saveWorld(TETile[][] w) {
        File f = new File("maze.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("e");
            System.exit(0);
        }
    }

    private void initialPlayer() {
        Random r = new Random(seed);
        playerPosX = RandomUtils.uniform(r, WIDTH);
        playerPosY = RandomUtils.uniform(r, HEIGHT);
        while (!gameBoard[playerPosX][playerPosY].equals(Tileset.FLOOR)) {
            playerPosX = RandomUtils.uniform(r, WIDTH);
            playerPosY = RandomUtils.uniform(r, HEIGHT);
        }
        gameBoard[playerPosX][playerPosY] = Tileset.PLAYER;
    }

    private void checkQuitNSave(String input) {
        if (input == null) {
            System.out.println("no input given");
            System.exit(0);
        }
        for (int i = 0; i < input.length(); i++) {
            char end = input.charAt(i);
            if (end == ':') {
                char endChecking = input.charAt(i + 1);
                if (endChecking == 'Q') {
                    saveWorld(gameBoard);
                }
            }
        }
    }

    private void loadPlayerPos() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (gameBoard[i][j].equals(Tileset.PLAYER)) {
                    playerPosX = i;
                    playerPosY = j;
                }
            }
        }
    }

    private boolean checkWin() {
        if (gameBoard[playerPosX][playerPosY].equals(Tileset.LOCKED_DOOR)) {
            winning = true;
        }
        return winning;
    }

    private void welcomeAndReadSeed() {
        while (true) {
            drawWelcomeBoard();
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                switch (c) {
                    case 'n':
                        newGame = true;
                        break;
                    case 'l':
                        loadGame = true;
                        gameBoard = loadWorld();
                        loadPlayerPos();
                        break;
                    case 'q':
                        System.out.println("Quiting");
                        break;
                    case 's':
                        startDrawGame = true;
                        break;
                    default:
                        if (newGame) {
                            if (Character.isDigit(c)) {
                                seedSB.append(c);
                                seedString = seedSB.toString();
                            }
                            break;
                        }
                }
            }
            if (startDrawGame || loadGame) {
                break;
            }
        }
    }

    private void showTileOnHover() {
        // turn the position of mouse pointer into xy-coordinate
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        TETile mouseTile = gameBoard[mouseX][mouseY];

        // draw as text
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(1, HEIGHT - 1, mouseTile.description());
        StdDraw.show();
        StdDraw.pause(100);


    }


}
