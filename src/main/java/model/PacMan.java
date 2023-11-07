package model;

import config.Cell;
import config.MazeConfig;
import config.MazeLoader;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.App;
import javafx.scene.layout.Pane;
import model.MazeState;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 * Thibault: Pour l'instant, le caractère de PacMan n'est pas implémenté en tant que singleton pattern puisque l'on souhaite garder
 * l'oeil de pacman. Si problème de pattern, implémenter Pacman en singleton pattern serait une bonne idée.
 */
public final class PacMan implements Critter { //final car il n'y a qu'une seule instance de cette classe
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;

    private RealCoordinates pos;
    private boolean energized;

    private PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 4 : 4;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
    public Direction getNextDirection() {
        return nextDirection;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setNextDirection(Direction direction) {
        this.nextDirection = direction;
    }

    @Override
    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized(){
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }

    /**
     *  TODO: faire les fruits, toucher un fantome tue pacman, manger un super pac gum
     */
}
