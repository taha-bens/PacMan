package gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import model.Direction;
import model.PacMan;
import model.MazeState;

public class PacmanController {

    /**
     * Lance la méthode à chaque fois qu'une touche est pressée.
     * @param event
     */
    public void keyPressedHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            if (PacMan.UN.getPos().getY() - Math.floor(PacMan.UN.getPos().getY()) == 0) {
                PacMan.UN.setNextDirection(Direction.NONE);
                PacMan.UN.setDirection(Direction.WEST);
            } else {
                PacMan.UN.setNextDirection(Direction.WEST);
            }
        }

        if (event.getCode() == KeyCode.RIGHT) {
            if (PacMan.UN.getPos().getY() - Math.floor(PacMan.UN.getPos().getY()) == 0) {
                PacMan.UN.setDirection(Direction.EAST);
                PacMan.UN.setNextDirection(Direction.NONE);
            } else {
                PacMan.UN.setNextDirection(Direction.EAST);
            }
        }

        if (event.getCode() == KeyCode.UP) {
            if (PacMan.UN.getPos().getX() - Math.floor(PacMan.UN.getPos().getX()) == 0) {
                PacMan.UN.setDirection(Direction.NORTH);
                PacMan.UN.setNextDirection(Direction.NONE);
            } else {
                PacMan.UN.setNextDirection(Direction.NORTH);
            }
        }

        if (event.getCode() == KeyCode.DOWN) {
            if (PacMan.UN.getPos().getX() - Math.floor(PacMan.UN.getPos().getX()) == 0) {
                PacMan.UN.setDirection(Direction.SOUTH);
                PacMan.UN.setNextDirection(Direction.NONE);
            } else {
                PacMan.UN.setNextDirection(Direction.SOUTH);
            }
        }

        if (!MazeState.getSp()){
            if (event.getCode() == KeyCode.Q) {
                if (PacMan.DEUX.getPos().getY() - Math.floor(PacMan.DEUX.getPos().getY()) == 0) {
                    PacMan.DEUX.setNextDirection(Direction.NONE);
                    PacMan.DEUX.setDirection(Direction.WEST);
                } else {
                    PacMan.DEUX.setNextDirection(Direction.WEST);
                }
            }

            if (event.getCode() == KeyCode.D) {
                if (PacMan.DEUX.getPos().getY() - Math.floor(PacMan.DEUX.getPos().getY()) == 0) {
                    PacMan.DEUX.setDirection(Direction.EAST);
                    PacMan.DEUX.setNextDirection(Direction.NONE);
                } else {
                    PacMan.DEUX.setNextDirection(Direction.EAST);
                }
            }

            if (event.getCode() == KeyCode.Z) {
                if (PacMan.DEUX.getPos().getX() - Math.floor(PacMan.DEUX.getPos().getX()) == 0) {
                    PacMan.DEUX.setDirection(Direction.NORTH);
                    PacMan.DEUX.setNextDirection(Direction.NONE);
                } else {
                    PacMan.DEUX.setNextDirection(Direction.NORTH);
                }
            }

            if (event.getCode() == KeyCode.S) {
                if (PacMan.DEUX.getPos().getX() - Math.floor(PacMan.DEUX.getPos().getX()) == 0) {
                    PacMan.DEUX.setDirection(Direction.SOUTH);
                    PacMan.DEUX.setNextDirection(Direction.NONE);
                } else {
                    PacMan.DEUX.setNextDirection(Direction.SOUTH);
                }
            }
        }

    }

    /**
     * Lance la méthode à chaque fois qu'une touche est relachée.
     * @param event
     */
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}