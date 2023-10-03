package gui;

import misc.Debug;
import model.Direction;
import model.PacMan;

import javafx.scene.input.KeyEvent;

public class PacmanController {

    /**
     * Méthode : changePacman à combiner avec #keyPressedHandler(KeyEvent event)
     * Input : event (KeyEvent)
     * @param event la touche détectée via KeyEvent
     * @return le nom de Pacman selon la touche
     */
    public static String changePacman(KeyEvent event) {
        return switch(event.getCode()) {
            case LEFT -> "pacman_left.png";
            case RIGHT -> "pacman_right.png";
            case UP -> "pacman_up.png";
            case DOWN -> "pacman_down.png";
            default -> "pacman_left.png";
        };
    }

    public void keyPressedHandler(KeyEvent event) {
        //Méthode : keyPressed Handler
        //Input : event (KeyEvent)
        //Output : void
        /*Description : lance la méthode à chaque fois qu'une touche est préssée.
         * Si la touche préssée est une flèche directionnelle, pacman prendra la
         * direciton de la flèche préssée s'il n'y a pas de mur en face.*/

        PacMan.INSTANCE.setDirection(
                switch (event.getCode()) {
                    case LEFT -> Direction.WEST;
                    case RIGHT -> Direction.EAST;
                    case UP -> Direction.NORTH;
                    case DOWN -> Direction.SOUTH;

                    default -> PacMan.INSTANCE.getDirection(); // do nothing
                }
        );
    }

    public void keyReleasedHandler(KeyEvent event) {
        //Méthode : keyReleasedHandler
        //Input : event (KeyEvent)
        //Output : void
        /*Description : Lance la méthode à chaque fois qu'une touche est relachée.*/
        // Nothing to do?
    }
}
