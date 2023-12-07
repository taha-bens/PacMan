package gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import model.Direction;
import model.PacMan;

public class PacmanController {

    /**
     * Lance la méthode à chaque fois qu'une touche est pressée.
     * @param event
     */
    public void keyPressedHandler(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            if (PacMan.INSTANCE.getPos().getY() - Math.floor(PacMan.INSTANCE.getPos().getY()) == 0) {
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
                PacMan.INSTANCE.setDirection(Direction.WEST);
            } else {
                PacMan.INSTANCE.setNextDirection(Direction.WEST);
            }
        }

        if (event.getCode() == KeyCode.RIGHT) {
            if (PacMan.INSTANCE.getPos().getY() - Math.floor(PacMan.INSTANCE.getPos().getY()) == 0) {
                PacMan.INSTANCE.setDirection(Direction.EAST);
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
            } else {
                PacMan.INSTANCE.setNextDirection(Direction.EAST);
            }
        }

        if (event.getCode() == KeyCode.UP) {
            if (PacMan.INSTANCE.getPos().getX() - Math.floor(PacMan.INSTANCE.getPos().getX()) == 0) {
                PacMan.INSTANCE.setDirection(Direction.NORTH);
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
            } else {
                PacMan.INSTANCE.setNextDirection(Direction.NORTH);
            }
        }

        if (event.getCode() == KeyCode.DOWN) {
            if (PacMan.INSTANCE.getPos().getX() - Math.floor(PacMan.INSTANCE.getPos().getX()) == 0) {
                PacMan.INSTANCE.setDirection(Direction.SOUTH);
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
            } else {
                PacMan.INSTANCE.setNextDirection(Direction.SOUTH);
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