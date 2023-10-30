package gui;

import javafx.scene.input.KeyCode;
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
            default -> "pacman_right.png";
        };
    }

    public void keyPressedHandler(KeyEvent event) {
        //Méthode : keyPressed Handler
        //Input : event (KeyEvent)
        //Output : void
        /*Description : lance la méthode à chaque fois qu'une touche est préssée.
         * Si PacMan peut aller dans cette direction , alors change sa direction
         * Sinon, cette direction pour le moment impossible est stocké dans PacMan.INSTANCE.nextDirection */

        if (event.getCode() == KeyCode.LEFT){
            if ( PacMan.INSTANCE.getPos().getY() - Math.floor(PacMan.INSTANCE.getPos().getY()) == 0){
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
                PacMan.INSTANCE.setDirection(Direction.WEST);
            }
            else {PacMan.INSTANCE.setNextDirection(Direction.WEST);}
        }
        if (event.getCode() == KeyCode.RIGHT){
            if ( PacMan.INSTANCE.getPos().getY() - Math.floor(PacMan.INSTANCE.getPos().getY()) == 0){
                PacMan.INSTANCE.setDirection(Direction.EAST);
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
            }
            else { PacMan.INSTANCE.setNextDirection(Direction.EAST); }
        }
        if (event.getCode() == KeyCode.UP){
            if ( PacMan.INSTANCE.getPos().getX() - Math.floor(PacMan.INSTANCE.getPos().getX()) == 0 ){
                PacMan.INSTANCE.setDirection(Direction.NORTH);
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
            }
            else { PacMan.INSTANCE.setNextDirection(Direction.NORTH); }
        }
        if (event.getCode() == KeyCode.DOWN){
            if ( PacMan.INSTANCE.getPos().getX() - Math.floor(PacMan.INSTANCE.getPos().getX()) == 0 ){
                PacMan.INSTANCE.setDirection(Direction.SOUTH);
                PacMan.INSTANCE.setNextDirection(Direction.NONE);
            }
            else { PacMan.INSTANCE.setNextDirection(Direction.SOUTH); }
        }

    }

    public void keyReleasedHandler(KeyEvent event) {
        //Méthode : keyReleasedHandler
        //Input : event (KeyEvent)
        //Output : void
        /*Description : Lance la méthode à chaque fois qu'une touche est relachée.*/
        // Nothing to do?
    }
}
