package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);


        MemoryGame game = new MemoryGame(40, 40, seed);

        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);

        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder RandomS = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            int Random = rand.nextInt(CHARACTERS.length);
            RandomS.append(CHARACTERS[Random]);
        }
        return RandomS.toString();
    }


    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        double centerHeight = height / 2;
        double centerWidth = width / 2;
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.text(centerWidth, centerHeight , s);
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] chars = letters.toCharArray();
        StringBuilder print = new StringBuilder();
        StdDraw.enableDoubleBuffering();
        for (char add : chars) {
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
            print.append(add);
            this.drawFrame(print.toString());
            StdDraw.text(height / 2, width / 2, print.toString());
            StdDraw.show();
            StdDraw.pause(1000);
        }
        StdDraw.disableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder typed = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            typed.append(StdDraw.nextKeyTyped());
        }
        return typed.toString();
    }


    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.round = 1;
        String showRound = "Round ";

        //String show = generateRandomString(round);
        //String input = solicitNCharsInput(round);

        //Boolean gameOver = false;


        //TODO: Establish Game loop
        for (int n = 0; n < this.round; n += 1) {

            this.drawFrame(showRound + round);
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.disableDoubleBuffering();
            StdDraw.clear(Color.BLACK);
            StdDraw.enableDoubleBuffering();
            String show = generateRandomString(round);
            this.flashSequence(show);
            StdDraw.pause(5000);
            String input = solicitNCharsInput(round);
            this.flashSequence(input);
            StdDraw.pause(500);
            StdDraw.disableDoubleBuffering();
            StdDraw.clear(Color.BLACK);
            gameOver = !input.equals(show);
            if (gameOver) {
                this.drawFrame("Gameover");
                break;
            }
            this.round += 1;
        }




    }

}
