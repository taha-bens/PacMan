package config;

import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;
import geometry.IntCoordinates;

import java.nio.channels.Pipe;

import static config.Cell.*;
import static config.Cell.Content.*;

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

    /*
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

    */


    /*
    Creation du 1er labyrinthe avec les nouveaux constructeurs (Cell.java)
     tableau de Cell
    */

    public static MazeConfig makeMaze1() {
        return new MazeConfig(new Cell[][]{
                {CornerCell("nw",ENERGIZER), PipeCell('h',DOT), PipeCell('h',DOT), PipeCell('h',DOT), CornerCell("ne",DOT), EntierCell(false, NOTHING), CornerCell("nw",DOT), PipeCell('h',DOT), PipeCell('h',DOT), PipeCell('h',DOT), CornerCell("ne",ENERGIZER)},
                {PipeCell('v',DOT), EntierCell(false,NOTHING), EntierCell(false,NOTHING), EntierCell(false,NOTHING), PipeCell('v',DOT), EntierCell(false,NOTHING),PipeCell('v',DOT), EntierCell(false,NOTHING), EntierCell(false,NOTHING), EntierCell(false,NOTHING), PipeCell('v',DOT)},
                {CornerCell("sw",DOT), TCell('n',DOT), PipeCell('h',DOT), TCell('n',DOT), TCell('s',DOT), PipeCell('h',DOT), TCell('s',DOT), TCell('n',DOT), PipeCell('h',DOT), TCell('n',DOT), CornerCell("se",DOT)},
                {EntierCell(false,NOTHING), PipeCell('v',DOT), EntierCell(false,NOTHING), PipeCell('v',DOT), EntierCell(false,NOTHING), EntierCell(false,NOTHING), EntierCell(false,NOTHING), PipeCell('v',DOT), EntierCell(false,NOTHING), PipeCell('v',DOT), EntierCell(false,NOTHING)},
                {EntierCell(false,NOTHING), PipeCell('v', DOT), EntierCell(false,NOTHING), TCell('w', DOT), PipeCell('h', DOT), PipeCell('h', NOTHING)/*TCell('n', NOTHING)*//*sortie spawn fantome*/, PipeCell('h', DOT), TCell('e', DOT), EntierCell(false,NOTHING), PipeCell('v', DOT), EntierCell(false,NOTHING)},
                {PipeCell('h', NOTHING), EntierCell(false, DOT), PipeCell('h', DOT), TCell('e', DOT), UCell('e', NOTHING), TCell('s', NOTHING), UCell('w', NOTHING), TCell('w', DOT), PipeCell('h',DOT), EntierCell(false,DOT), PipeCell('h', NOTHING)},
                {EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING), TCell('w', DOT), PipeCell('h', DOT), PipeCell('h', DOT), PipeCell('h', DOT), TCell('e', DOT), EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING)},
                {EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING), EntierCell(false, NOTHING), EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING)},
                {CornerCell("nw", DOT), TCell('s', DOT), PipeCell('h', DOT), TCell('s', DOT), CornerCell("ne", DOT), EntierCell(false, NOTHING), CornerCell("nw", DOT), TCell('s', DOT), PipeCell('h', DOT), TCell('s', DOT), CornerCell("ne", DOT)},
                {PipeCell('v', DOT), EntierCell(false, NOTHING), EntierCell(false, NOTHING), EntierCell(false, NOTHING), PipeCell('v', DOT), EntierCell(false, NOTHING),PipeCell('v', DOT), EntierCell(false, NOTHING), EntierCell(false, NOTHING), EntierCell(false, NOTHING), PipeCell('v', DOT)},
                {CornerCell("sw", ENERGIZER), PipeCell('h', DOT), PipeCell('h', DOT), PipeCell('h', DOT), TCell('s', DOT), PipeCell('h', DOT), TCell('s', DOT), PipeCell('h', DOT), PipeCell('h', DOT), PipeCell('h', DOT), CornerCell("se", ENERGIZER)}


        },
                new IntCoordinates(5, 6),//coords depart pacman
                new IntCoordinates(5, 4),//coords depart fantome1
                new IntCoordinates(5, 5),//coords depart fantome2
                new IntCoordinates(4, 5),//coords depart fantome3
                new IntCoordinates(6, 5) //coords depart fantome4
        );
    }



}
