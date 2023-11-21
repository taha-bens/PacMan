package config;

import com.sun.javafx.scene.EnteredExitedHandler;
import geometry.IntCoordinates;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


import static config.Cell.Content.*;
import static config.Cell.*;

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

    public Cell[][] getGrid(){
        return grid;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      construction du maze
    ////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static MazeConfig makeMazeFINAL(String txt) {
        Cell[][] laby;
        laby = MazeLoader.mazeLoader(txt);
        return new MazeConfig(laby,

                new IntCoordinates(7, 8),//coords depart pacman
                new IntCoordinates(7, 6),//coords depart BLINKY
                new IntCoordinates(7, 7),//coords depart PINKY
                new IntCoordinates(6, 7),//coords depart INKY
                new IntCoordinates(8, 7) //coords depart CLYDE
        );
    }


}
