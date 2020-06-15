package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int WIDE = 40;
    //private static final long SEED = 7;
    private static final Random RANDOM = new Random();

    // Position reference class for world x,y coordinate.
    private static class Position {
        int xPos;
        int yPos;
        private Position(int x, int y) {
            xPos = x;
            yPos = y;
        }
    }

    /**Function return width of the hex in h-raw.
     * @param h the height of the hex
     * @param sides the size of the hex
     * @return the raw width of the hex
     */
    private static int width(int h, int sides) {
        int add = h;
        if (h >= sides) {
            add = (sides  - (h - sides + 1));
        }
        return sides + 2 * add;
    }

    @Test
    public void testWidth() {
        assertEquals(width(2,3) , 7);
        assertEquals(width(4,4) , 10);
        assertEquals(width(5,4) , 8);
        assertEquals(width(5,3) , 3);
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position.
     * @param h h-raw of the hex, where 0 is the bottom
     * @param sides size of the hex
     * @return
     */

    private static int startOfRaw(int h, int sides) {
        int x = h;
        if (h >= sides ) {
            x = sides - 1 - (h - sides);
        } return - x;
    }

    @Test
    public void testStartOfRaw() {
        assertEquals(startOfRaw(1,2) , -1);
        assertEquals(startOfRaw(4,3) , -1);
        assertEquals(startOfRaw(1, 3) , -1);
    }

    /** Draw one of the line of Hex in the world.
     * @param world the world to drawHex
     * @param width the line width to draw on
     * @param t draw with what kind of tile
     * @param p at which position to draw the line
     */
    private static void drawHexLine(TETile[][] world, int width, TETile t, Position p) {
        for (int i = 0; i < width; i += 1) {
            world [p.xPos + i][p.yPos] = t;
        }
    }

    /** Draw the Hex by point out the raw starting position and
     * the width of that line
     * @param world the world to drawHex
     * @param sides size of the Hex to draw
     * @param t draw with what kind of tile
     * @param p at which position to draw the Hex
     */
    private static void drawAHex(TETile[][] world, int sides, TETile t, Position p) {
        for (int h = 0; h < 2 * sides; h += 1) {
            Position RawStart = new Position(p.xPos + startOfRaw(h , sides),p.yPos + h);
            drawHexLine(world , width(h , sides), t, RawStart);
        }
    }

    /** Create 5 random fill in tile effect.
     * @param world
     * @param sides
     * @param p
     */
    private static void randomDrawAHex(TETile[][] world, int sides, Position p) {
        int hexNum = RANDOM.nextInt(5);
        TETile tile;
        switch (hexNum) {
            case 0:
                tile = Tileset.FLOOR;
                break;
            case 1:
                tile = Tileset.FLOWER;
                break;
            case 2:
                tile = Tileset.GRASS;
                break;
            case 3:
                tile = Tileset.MOUNTAIN;
                break;
            case 4:
                tile = Tileset.SAND;
                break;
            default:
                tile = Tileset.NOTHING;
                break;
        }
        drawAHex(world, sides, tile, p);
    }

    /** Expand one hex to 19 hexes
     *     3/ 4/ 5/ 4/ 3
     *           %
     *        %     %
     *      %    %     %
     *        %     %
     *      %    %     %
     *        %     %
     *   p->%    %     %
     *        %     %
     *           %
     * @param world the world to draw
     * @param sides the size of the hex
     * @param p the hex position show above
     */
    private static void expand(TETile[][] world, int sides, Position p) {
        int xDiff = 2 * sides - 1;
        for (int i = 0; i < 3; i += 1) {
            Position xHex = new Position(p.xPos + i * xDiff, p.yPos - i * sides);
            expandYHelper(world, sides, xHex,  3 + i );
        }
        for (int i = 0; i < 2; i += 1) {
            Position xHex = new Position(p.xPos + (3 + i) * xDiff, p.yPos - (1 - i) * sides);
            expandYHelper(world, sides, xHex, 4 - i );
        }
    }
    /** Helper method of Expand, draw one column of the hex
     *     3
     *      %
     *      %
     *   p->%
     * @param world the world to draw
     * @param sides the size of the hex
     * @param p the hex position show above
     * @param yNumber the number to draw in one column
     */

    private static void expandYHelper(TETile[][] world, int sides, Position p, int yNumber) {
        int yDiff = 2 * sides;
        for (int i = 0; i < yNumber; i += 1) {
            Position YHex = new Position(p.xPos, p.yPos + i * yDiff);
            randomDrawAHex(world, sides, YHex);
        }
    }

    public static void main(String[] args) {

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, WIDE);

        TETile [][] world = new TETile[WIDTH][WIDE];

        //To fill in the world with nothing tile.
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < WIDE; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //drawHexLine(world, 3, t, 5, 8);
        //drawAHex(world, 3, t, 10, 8);
        //randomDrawAHex(world,3, 10, 8);
        Position p = new Position(10, 8);
        expand(world,4, p);

        // draws the world to the screen
        ter.renderFrame(world);
    }
}



