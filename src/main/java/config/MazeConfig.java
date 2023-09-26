package config;

import geometry.IntCoordinates;

import static config.Cell.Content.DOT;
import static config.Cell.*;
import static config.Cell.Content.NOTHING;

public class MazeConfig {
    public MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                      IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            if (getWidth() >= 0) System.arraycopy(grid[i], 0, this.grid[i], 0, getHeight());
        }
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
    }

    private final Cell[][] grid;
    private final IntCoordinates pacManPos, blinkyPos, pinkyPos, inkyPos, clydePos;

    public IntCoordinates getPacManPos() {
        return pacManPos;
    }

    public IntCoordinates getBlinkyPos() {
        return blinkyPos;
    }

    public IntCoordinates getPinkyPos() {
        return pinkyPos;
    }

    public IntCoordinates getInkyPos() {
        return inkyPos;
    }

    public IntCoordinates getClydePos() {
        return clydePos;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }

    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }


    // simple example with a square shape
    // TODO: mazes should be loaded from a text file
    
    public static MazeConfig makeExample1() {
        return new MazeConfig(new Cell[][]{
                {seVee(DOT), hPipe(DOT), hPipe(DOT), hPipe(DOT), swVee(DOT) , open(NOTHING), seVee(DOT), hPipe(DOT), hPipe(DOT), hPipe(DOT), swVee(DOT)},
                {vPipe(DOT), open(NOTHING), open(NOTHING), open(NOTHING), vPipe(DOT), open(NOTHING),vPipe(DOT), open(NOTHING), open(NOTHING), open(NOTHING), vPipe(DOT)},
                {neVee(DOT), nTee(DOT), hPipe(DOT), nTee(DOT), sTee(DOT), hPipe(DOT), sTee(DOT), nTee(DOT), hPipe(DOT), nTee(DOT), nwVee(DOT)},
                {open(NOTHING), vPipe(DOT), open(NOTHING), vPipe(DOT), open(NOTHING), open(NOTHING), open(NOTHING), vPipe(DOT), open(NOTHING), vPipe(DOT), open(NOTHING)},
                {open(NOTHING), vPipe(DOT), open(NOTHING), wTee(DOT), hPipe(DOT), nTee(DOT), hPipe(DOT), eTee(DOT), open(NOTHING), vPipe(DOT), open(NOTHING)},
                {hPipe (NOTHING), open(DOT), hPipe(DOT), eTee(DOT), eU(NOTHING), sTee(NOTHING), wU(NOTHING), wTee(DOT), hPipe(DOT), open(DOT), hPipe(NOTHING)},
                {open(NOTHING), vPipe(DOT), open(NOTHING), wTee(DOT), hPipe(DOT), hPipe(DOT), hPipe(DOT), eTee(DOT), open(NOTHING), vPipe(DOT), open(NOTHING)},
                {open(NOTHING), vPipe(DOT), open(NOTHING), vPipe(DOT), open(NOTHING), open(NOTHING), open(NOTHING), vPipe(DOT), open(NOTHING), vPipe(DOT), open(NOTHING)},
                {seVee(DOT), sTee(DOT), hPipe(DOT), sTee(DOT), swVee(DOT), open(NOTHING),seVee(DOT), sTee(DOT), hPipe(DOT), sTee(DOT), swVee(DOT)},
                {vPipe(DOT), open(NOTHING), open(NOTHING), open(NOTHING), vPipe(DOT), open(NOTHING),vPipe(DOT), open(NOTHING), open(NOTHING), open(NOTHING), vPipe(DOT)},
                {neVee(DOT), hPipe(DOT), hPipe(DOT), hPipe(DOT), sTee(DOT), hPipe(DOT), sTee(DOT), hPipe(DOT), hPipe(DOT), hPipe(DOT), nwVee(DOT)}
                
                
        },
                new IntCoordinates(5, 6),
                new IntCoordinates(4, 5),
                new IntCoordinates(5, 5),
                new IntCoordinates(6, 5),
                new IntCoordinates(5, 4)
        );
    }
    
    /*
    
    public static MazeConfig makeExample1() {
        return new MazeConfig(new Cell[][]{
                {nTee(DOT),    hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     nTee(DOT)},
                {vPipe(DOT),    seVee(NOTHING), nTee(NOTHING),  nTee(NOTHING),  swVee(NOTHING), vPipe(DOT)},
                {vPipe(DOT),     wTee(NOTHING),  open(NOTHING),  open(NOTHING),  eTee(NOTHING),  vPipe(DOT)},
                {vPipe(DOT),    wTee(NOTHING),  open(NOTHING),  open(NOTHING),  eTee(NOTHING),  vPipe(DOT)},
                {vPipe(DOT),    neVee(NOTHING), sTee(NOTHING),  sTee(NOTHING),   nwVee(NOTHING), vPipe(DOT)},
                {neVee(DOT),    hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     hPipe(DOT),     nwVee(DOT)}
        },
                new IntCoordinates(3, 0),
                new IntCoordinates(0, 3),
                new IntCoordinates(3, 5),
                new IntCoordinates(5, 5),
                new IntCoordinates(5, 1)
        );
    }
    */
}
