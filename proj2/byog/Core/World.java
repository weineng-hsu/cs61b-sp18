package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class World {

    private static int WIDTH;
    private static int HEIGHT;
    public static final int EAST = 0;
    public static final int SOUTH = 1;
    public static final int WEST = 2;
    public static final int NORTH = 3;
    private Random r;

    private static TETile[][] grid;

    private Position initialPosition;

    private class Position {
        private int x;
        private int y;
        Position(int xPos, int yPos) {
            x = xPos;
            y = yPos;
        }
    }
    private class Room {
        private Position bottomLeft;
        private Position upperRight;
        private Position exitEast;
        private Position exitSouth;
        private Position exitWest;
        private Position exitNorth;
        private int roomWide;
        private int roomHeight;
        Room(Position bL, int wide, int height) {
            bottomLeft = bL;
            roomWide = wide;
            roomHeight = height;
            upperRight = new Position(bL.x + wide - 1, bL.y + height - 1);
        }
        Room(int wide, int height, Position uR) {
            upperRight = uR;
            roomWide = wide;
            roomHeight = height;
            bottomLeft = new Position(uR.x - wide + 1, uR.y - height + 1);
        }
    }

    World(long s, int h, int w) {
        HEIGHT = h;
        WIDTH = w;
        r = new Random(s);
        initilize();
        Room firstroom = randomRoomGenerator();
        drawARoom(firstroom, grid);
        generateRoomExit(firstroom);
        drawARoomExit(firstroom);
        drawNextRoom(firstroom);
    }


    public TETile[][] getGrid() {
        return grid;
    }

    public Room randomRoomGenerator() {
        int randomX = WIDTH;
        int randomY = HEIGHT;
        int randomWide = 1;
        int randomHeight = 1;

        while (randomX + randomWide + 1 > WIDTH || randomY + randomHeight + 1 > HEIGHT) {
            randomX = RandomUtils.uniform(r, WIDTH);
            randomY = RandomUtils.uniform(r, HEIGHT);
            randomWide = RandomUtils.uniform(r, 3) + 6;
            randomHeight = RandomUtils.uniform(r, 4) + 6;
        }

        Position leftBottomX = new Position(randomX, randomY);
        Room randomRoom = new Room(leftBottomX, randomWide, randomHeight);
        return randomRoom;
    }

    public void drawARoom(Room todraw, TETile[][] world) {
        for (int x = todraw.bottomLeft.x; x <= todraw.upperRight.x; x++) {
            for (int y = todraw.bottomLeft.y; y <= todraw.upperRight.y; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
        for (int i = todraw.bottomLeft.x; i <= todraw.upperRight.x; i++) {
            world[i][todraw.bottomLeft.y] = Tileset.WALL;
            world[i][todraw.upperRight.y] = Tileset.WALL;
        }
        for (int i = todraw.bottomLeft.y; i <= todraw.upperRight.y; i++) {
            world[todraw.bottomLeft.x][i] = Tileset.WALL;
            world[todraw.upperRight.x][i] = Tileset.WALL;
        }
        if (initialPosition == null) {
            getInitialPosition(todraw);
            world[initialPosition.x][initialPosition.y] = Tileset.LOCKED_DOOR;
        }
    }

    public void getInitialPosition(Room firstRoom) {
        int ranDir = RandomUtils.uniform(r, 4);
        int ranX;
        int ranY;
        int initialPosX;
        int initialPosY;
        switch (ranDir) {
            case 0:
                initialPosX = firstRoom.upperRight.x;
                ranY = RandomUtils.uniform(r, 1, firstRoom.roomHeight - 1);
                initialPosY = firstRoom.bottomLeft.y + ranY;
                initialPosition = new Position(initialPosX, initialPosY);
                break;
            case 1:
                initialPosY = firstRoom.bottomLeft.y;
                ranX = RandomUtils.uniform(r, 1, firstRoom.roomWide - 1);
                initialPosX = firstRoom.bottomLeft.x + ranX;
                initialPosition = new Position(initialPosX, initialPosY);
                break;
            case 2:
                initialPosX = firstRoom.bottomLeft.x;
                ranY = RandomUtils.uniform(r, 1, firstRoom.roomHeight - 1);
                initialPosY = firstRoom.bottomLeft.y + ranY;
                initialPosition = new Position(initialPosX, initialPosY);
                break;
            case 3:
                initialPosY = firstRoom.upperRight.y;
                ranX = RandomUtils.uniform(r, 1, firstRoom.roomWide - 1);
                initialPosX = firstRoom.bottomLeft.x + ranX;
                initialPosition = new Position(initialPosX, initialPosY);
                break;
            default:
                break;
        }
    }

    public boolean checkRoom(Room tocheck) {

        if (tocheck.bottomLeft.x < 0 || tocheck.bottomLeft.y < 0) {
            return false;
        }
        if (tocheck.upperRight.x >= WIDTH) {
            return false;
        }
        if (tocheck.upperRight.y >= HEIGHT) {
            return false;
        }
        for (int x = tocheck.bottomLeft.x; x <= tocheck.upperRight.x; x++) {
            for (int y = tocheck.bottomLeft.y; y <= tocheck.upperRight.y; y++) {
                if (grid[x][y] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public void drawARoomExit(Room todraw) {
        if (todraw.exitEast != null) {
            grid[todraw.exitEast.x][todraw.exitEast.y] = Tileset.FLOOR;
        }
        if (todraw.exitWest != null) {
            grid[todraw.exitWest.x][todraw.exitWest.y] = Tileset.FLOOR;
        }
        if (todraw.exitSouth != null) {
            grid[todraw.exitSouth.x][todraw.exitSouth.y] = Tileset.FLOOR;
        }
        if (todraw.exitNorth != null) {
            grid[todraw.exitNorth.x][todraw.exitNorth.y] = Tileset.FLOOR;
        }
    }

    public static void initilize() {
        grid = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                grid[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void generateRoomExit(Room room) {
        for (int i = 0; i < 7; i++) {
            int randomDir = RandomUtils.uniform(r, 4);
            switch (randomDir) {
                case EAST:
                    Position exitEast = new Position(room.upperRight.x,
                            room.upperRight.y - 1 - RandomUtils.uniform(r, room.roomHeight - 2));
                    if (grid[exitEast.x][exitEast.y] == Tileset.WALL) {
                        room.exitEast = exitEast;
                    }
                    break;
                case SOUTH:
                    Position exitSouth = new Position(room.bottomLeft.x + 1
                            + RandomUtils.uniform(r, room.roomWide - 2), room.bottomLeft.y);
                    if (grid[exitSouth.x][exitSouth.y] == Tileset.WALL) {
                        room.exitSouth = exitSouth;
                    }
                    break;
                case WEST:
                    Position exitWest = new Position(room.bottomLeft.x,
                            room.upperRight.y - 1 - RandomUtils.uniform(r, room.roomHeight - 2));
                    if (grid[exitWest.x][exitWest.y] == Tileset.WALL) {
                        room.exitWest = exitWest;
                    }
                    break;
                case NORTH:
                    Position exitNorth = new Position(room.bottomLeft.x + 1
                            + RandomUtils.uniform(r, room.roomWide - 2), room.upperRight.y);
                    if (grid[exitNorth.x][exitNorth.y] == Tileset.WALL) {
                        room.exitNorth = exitNorth;
                    }
                    break;
                default:
                    break;
            }
        }
    }


    public void drawNextRoom(Room origin) {
        if (origin.exitEast != null) {
            int nextRoomHeight = RandomUtils.uniform(r, 5) + 3;
            int nextRoomWeith = RandomUtils.uniform(r, 5) + 3;
            int nextRoomBottomY = RandomUtils.uniform(r, origin.exitEast.y
                    + 2 - nextRoomHeight, origin.exitEast.y);
            Position nextRoomBottomCorner = new Position(origin.exitEast.x + 1, nextRoomBottomY);
            Room nextRoom = new Room(nextRoomBottomCorner, nextRoomWeith, nextRoomHeight);
            if (checkRoom(nextRoom)) {
                drawARoom(nextRoom, grid);
                generateRoomExit(nextRoom);
                drawARoomExit(nextRoom);
                drawNextRoom(nextRoom);
                grid[origin.exitEast.x + 1][origin.exitEast.y] = Tileset.FLOOR;
            } else {
                grid[origin.exitEast.x][origin.exitEast.y] = Tileset.WALL;
            }

        }
        if (origin.exitSouth != null) {
            int nextRoomHeight = RandomUtils.uniform(r, 5) + 3;
            int nextRoomWeith = RandomUtils.uniform(r, 5) + 3;
            int nextRoomUpperX = RandomUtils.uniform(r, origin.exitSouth.x + 1,
                    origin.exitSouth.x - 1 + nextRoomWeith);
            Position nextRoomUpperCorner = new Position(nextRoomUpperX, origin.exitSouth.y - 1);
            Room nextRoom = new Room(nextRoomWeith, nextRoomHeight, nextRoomUpperCorner);
            if (checkRoom(nextRoom)) {
                drawARoom(nextRoom, grid);
                generateRoomExit(nextRoom);
                drawARoomExit(nextRoom);
                drawNextRoom(nextRoom);
                grid[origin.exitSouth.x][origin.exitSouth.y - 1] = Tileset.FLOOR;
            } else {
                grid[origin.exitSouth.x][origin.exitSouth.y] = Tileset.WALL;
            }
        }
        if (origin.exitWest != null) {
            int nextRoomHeight = RandomUtils.uniform(r, 5) + 3;
            int nextRoomWeith = RandomUtils.uniform(r, 5) + 3;
            int nextRoomUpperY = RandomUtils.uniform(r, origin.exitWest.y + 1,
                    origin.exitWest.y - 1 + nextRoomHeight);
            Position nextRoomUpperCorner = new Position(origin.exitWest.x - 1, nextRoomUpperY);
            Room nextRoom = new Room(nextRoomWeith, nextRoomHeight, nextRoomUpperCorner);
            if (checkRoom(nextRoom)) {
                drawARoom(nextRoom, grid);
                generateRoomExit(nextRoom);
                drawARoomExit(nextRoom);
                drawNextRoom(nextRoom);
                grid[origin.exitWest.x - 1][origin.exitWest.y] = Tileset.FLOOR;
            } else {
                grid[origin.exitWest.x][origin.exitWest.y] = Tileset.WALL;
            }
        }
        if (origin.exitNorth != null) {
            int nextRoomHeight = RandomUtils.uniform(r, 5) + 3;
            int nextRoomWeith = RandomUtils.uniform(r, 5) + 3;
            int nextRoomBottomX = RandomUtils.uniform(r, origin.exitNorth.x + 2 - nextRoomWeith,
                    origin.exitNorth.x);
            Position nextRoomBottomCorner = new Position(nextRoomBottomX, origin.exitNorth.y + 1);
            Room nextRoom = new Room(nextRoomBottomCorner, nextRoomWeith, nextRoomHeight);
            if (checkRoom(nextRoom)) {
                drawARoom(nextRoom, grid);
                generateRoomExit(nextRoom);
                drawARoomExit(nextRoom);
                drawNextRoom(nextRoom);
                grid[origin.exitNorth.x][origin.exitNorth.y + 1] = Tileset.FLOOR;
            } else {
                grid[origin.exitNorth.x][origin.exitNorth.y] = Tileset.WALL;
            }
        }
    }



    public static void main(String[] args) {
        /*
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        World world = new World(4548);
        world.initilize();
        Room firstroom = world.randomRoomGenerator();
        world.drawARoom(firstroom, world.grid);
        world.generateRoomExit(firstroom);
        world.drawARoomExit(firstroom);
        world.drawNextRoom(firstroom);
        ter.renderFrame(world.grid);

         */

        /*
        World world = new World(9);
        TETile[][] finalworld = world.getGrid();
        System.out.println(TETile.toString(finalworld));

         */

    }
}
