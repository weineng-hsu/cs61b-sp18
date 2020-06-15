package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */

public class HexWorld {
    private static final int WIDTH = 45;
    private static final int HEIGHT = 45;


    private static class Position {
        int x;
        int y;
        Position(int xPos, int yPos) {
            x = xPos;
            y = yPos;
        }
        public Position copyPos() {
            return new Position(this.x, this.y);
        }
        public Position upperHexPos(int side) {
            return new Position(this.x, this.y + 2 * side);
        }
        public Position sideLowerHexPos(int side) {
            return new Position(this.x + 2 * side - 1, this.y - side);
        }
        public Position raw4HexPos(int side) {
            return new Position(this.x + 3 * (2 * side - 1), this.y - side);
        }
        public Position sideUpperHexPos(int side) {
            return new Position(this.x + 2 * side - 1, this.y + side);
        }

    }

    public static TETile[][] initilize() {
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                hexWorld[x][y] = Tileset.NOTHING;
            }
        }
        return hexWorld;
    }

    public static void drawSingleHex(Position p, int side, TETile[][] world, TETile terrain) {
        Position lineStart = p.copyPos();
        for (int i = 0; i < side; i++) {
            drawALine(lineStart, side + 2 * i, world, terrain);
            lineStart.x -= 1;
            lineStart.y += 1;
        }
        for (int i = side - 1; i >= 0; i--) {
            lineStart.x += 1;
            drawALine(lineStart, side + 2 * i, world, terrain);
            lineStart.y += 1;
        }
    }

    public static void drawALine(Position p, int s, TETile[][] world, TETile terrain) {
        for (int i = 0; i < s; i++) {
            world[p.x + i][p.y] = terrain;
        }
    }

    public static TETile randomTile() {
        Random r = new Random();
        int tileRan = r.nextInt(7);
        switch (tileRan) {
            case 0: return Tileset.FLOOR;
            case 1: return Tileset.WALL;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.TREE;
            case 6: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    public static void drawHexWorld(Position p, int side, TETile[][] world) {
        int hexInRow = 3;
        int rowIndex = 0;
        Position origin = p.copyPos();
        while (rowIndex < 3) {
            drawHexInRaw(origin, side, hexInRow + rowIndex, world);
            origin = origin.sideLowerHexPos(side);
            rowIndex += 1;
        }
        origin = p.raw4HexPos(side);
        rowIndex -= 2;
        while (rowIndex >= 0) {
            drawHexInRaw(origin, side, hexInRow + rowIndex, world);
            origin = origin.sideUpperHexPos(side);
            rowIndex -= 1;
        }
    }

    public static void drawHexInRaw(Position p, int side, int count, TETile[][] world) {
        Position upper = p.upperHexPos(side);
        for (int i = 0; i < count; i++) {
            TETile ranTile = randomTile();
            drawSingleHex(upper, side, world, ranTile);
            upper = upper.upperHexPos(side);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = HexWorld.initilize();
        Position origin = new Position(5, 5);
        //drawALine(origin, 4, world, Tileset.WALL);
        //drawSingleHex(origin, 4, world, Tileset.WALL);
        //drawHexInRaw(origin, 3, 4, world);
        drawHexWorld(origin, 3, world);
        ter.renderFrame(world);
    }
}
