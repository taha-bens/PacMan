package model;

import config.*;
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
    private Direction previousDir;

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
        return 4;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
    public Direction getNextDirection() {
        return nextDirection;
    }
    public Direction getPreviousDir(){ return previousDir; }

    @Override
    public void setDirection(Direction direction) {
        previousDir = this.direction;
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

    public void updatePacman(MazeConfig config){
        if (PacMan.INSTANCE.getDirection() == Direction.EAST){
            if (Math.ceil( PacMan.INSTANCE.getPos().getX() ) - PacMan.INSTANCE.getPos().getX() < 0.1  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH || PacMan.INSTANCE.getNextDirection() == Direction.SOUTH){
                    IntCoordinates co = new IntCoordinates((int)Math.ceil (PacMan.INSTANCE.getPos().getX()) , (int)PacMan.INSTANCE.getPos().getY()  );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH && !(config.getCell(co).northWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y() - 0.001));
                        PacMan.INSTANCE.setDirection(Direction.NORTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.SOUTH && !(config.getCell(co).southWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()+ 0.001));
                        PacMan.INSTANCE.setDirection(Direction.SOUTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.WEST){
            if ( PacMan.INSTANCE.getPos().getX() - Math.floor( PacMan.INSTANCE.getPos().getX()  )  < 0.1  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH || PacMan.INSTANCE.getNextDirection() == Direction.SOUTH){
                    IntCoordinates co = new IntCoordinates((int)Math.floor (PacMan.INSTANCE.getPos().getX()) , (int)PacMan.INSTANCE.getPos().getY()  );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH && !(config.getCell(co).northWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()- 0.001));
                        PacMan.INSTANCE.setDirection(Direction.NORTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.SOUTH && !(config.getCell(co).southWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()+ 0.001));
                        PacMan.INSTANCE.setDirection(Direction.SOUTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.SOUTH){
            if (Math.ceil( PacMan.INSTANCE.getPos().getY() ) - PacMan.INSTANCE.getPos().getY() < 0.1  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.EAST || PacMan.INSTANCE.getNextDirection() == Direction.WEST){
                    IntCoordinates co = new IntCoordinates((int)PacMan.INSTANCE.getPos().getX() , (int)Math.ceil( PacMan.INSTANCE.getPos().getY() ) );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.EAST && !(config.getCell(co).eastWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() + 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.EAST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.WEST && !(config.getCell(co).westWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() - 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.WEST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.NORTH){
            if ( PacMan.INSTANCE.getPos().getY() - Math.floor( PacMan.INSTANCE.getPos().getY() )  < 0.1  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.EAST || PacMan.INSTANCE.getNextDirection() == Direction.WEST){
                    IntCoordinates co = new IntCoordinates((int)PacMan.INSTANCE.getPos().getX() , (int)Math.floor( PacMan.INSTANCE.getPos().getY() ) );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.WEST && !(config.getCell(co).westWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() - 0.001  , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.WEST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.EAST && !(config.getCell(co).eastWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() + 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.EAST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }

        }
    }

    public IntCoordinates devantPacman (){
        //Cette fonction retourne les coordonnées de la case qui se trouve à 2 case devant pacman
        if (PacMan.INSTANCE.getDirection() == Direction.NORTH) {
            if (PacMan.INSTANCE.getPos().round().y() - 2 < 2) {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), 2);
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), PacMan.INSTANCE.getPos().round().y() - 2);
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.SOUTH) {
            if (PacMan.INSTANCE.getPos().round().y() + 2 > 12) {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), 12);
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), PacMan.INSTANCE.getPos().round().y() + 2);
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.WEST) {
            if (PacMan.INSTANCE.getPos().round().x() - 2 < 0) {
                return new IntCoordinates(0, PacMan.INSTANCE.getPos().round().y());
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x() - 2, PacMan.INSTANCE.getPos().round().y());
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.EAST) {
            if (PacMan.INSTANCE.getPos().round().x() + 2 > 14) {
                return new IntCoordinates(14, PacMan.INSTANCE.getPos().round().y());
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x() + 2, PacMan.INSTANCE.getPos().round().y());
            }
        } else {
            return PacMan.INSTANCE.getPos().round();
        }
    }
    /**
     *  TODO: faire les fruits, toucher un fantome tue pacman, manger un super pac gum
     */
}
