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

        //int seed = Integer.parseInt(args[0]);
        int seed = 8;
        MemoryGame game = new MemoryGame(40, 40, seed);

        //String test = game.generateRandomString(5);
        //System.out.println(test);
        //game.drawFrame("test");
        //game.flashSequence("test");
        //game.solicitNCharsInput(5);

        game.startGame();
    }

    public MemoryGame(int width, int height, int randSeed) {
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
        rand = new Random(randSeed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length
        StringBuilder genString = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int charIndex = rand.nextInt(CHARACTERS.length);
            genString.append(CHARACTERS[charIndex]);
        }
        return genString.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        Font font = new Font ("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();

        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(width / 2, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }
        /*char[] toPrintChars = letters.toCharArray();
        for (int i = 0; i < toPrintChars.length; i++) {
            drawFrame(String.valueOf(toPrintChars[i]));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }
         */
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        /*StringBuilder input =  new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue; //If no input return
            }
            input.append(StdDraw.nextKeyTyped());
            drawFrame(input.toString());
            StdDraw.pause(1000);
        }
        return input.toString();

         */
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            drawFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        String randString;
        String userAns;
        while (!gameOver) {
            drawFrame("Round: " + round);
            StdDraw.pause(3000);
            randString = generateRandomString(round);
            flashSequence(randString);
            StdDraw.pause(3000);
            userAns = solicitNCharsInput(round);
            drawFrame(userAns);
            StdDraw.pause(3000);
            if (!userAns.equals(randString)) {
                int completed = round - 1;
                drawFrame("Game Over! You made it to round: " + completed);
                gameOver = true;
            } else {
                round += 1;
                drawFrame("Correct, well done!");
                StdDraw.pause(1500);
            }
        }
    }
}
