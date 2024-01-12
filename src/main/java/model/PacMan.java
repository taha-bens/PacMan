package model;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;

/**
 * final car il n'y a qu'une seule instance de cette classe
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private Direction nextDirection = Direction.NONE;
    private Direction previousDir;

    private RealCoordinates pos;
    private boolean energized;

    private int score;

    private PacMan() {

    }

    public static final PacMan UN = new PacMan();
    public static final PacMan DEUX = new PacMan();

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

    public Direction getPreviousDir() {
        return previousDir;
    }

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
    public boolean isEnergized() {
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }

    public void addScore(int score) {
        if (score == 100){
            Sound.playFruitSound();
        }
        else {
            Sound.playEatSound();
        }
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {this.score = score;}

    public void updatePacman(MazeConfig config) {
        if (this.getDirection() == Direction.EAST) {
            if (Math.ceil(PacMan.UN.getPos().getX()) - this.getPos().getX() < 0.1  &&
                    this.getNextDirection() != Direction.NONE)
            {
                if (this.getNextDirection() == Direction.NORTH ||
                        this.getNextDirection() == Direction.SOUTH)
                {
                    IntCoordinates co = new IntCoordinates(
                            (int) Math.ceil(this.getPos().getX()),
                            (int) this.getPos().getY()
                    );

                    if (this.getNextDirection() == Direction.NORTH &&
                            !(config.getCell(co).northWall()))
                    {
                        this.setPos(new RealCoordinates(co.x(), co.y() - 0.001));
                        this.setDirection(Direction.NORTH);
                        this.setNextDirection(Direction.NONE);
                    }

                    if (this.getNextDirection() == Direction.SOUTH &&
                            !(config.getCell(co).southWall()))
                    {
                        this.setPos(new RealCoordinates(co.x(), co.y() + 0.001));
                        this.setDirection(Direction.SOUTH);
                        this.setNextDirection(Direction.NONE);
                    }
                }
            }
        }

        if (this.getDirection() == Direction.WEST) {
            if (this.getPos().getX() - Math.floor(this.getPos().getX()) < 0.1 &&
                    this.getNextDirection() != Direction.NONE)
            {
                if (this.getNextDirection() == Direction.NORTH ||
                        this.getNextDirection() == Direction.SOUTH)
                {
                    IntCoordinates co = new IntCoordinates(
                            (int) Math.floor(this.getPos().getX()),
                            (int) this.getPos().getY()
                    );

                    if (this.getNextDirection() == Direction.NORTH &&
                            !(config.getCell(co).northWall()))
                    {
                        this.setPos(new RealCoordinates(co.x(), co.y() - 0.001));
                        this.setDirection(Direction.NORTH);
                        this.setNextDirection(Direction.NONE);
                    }
                    if (this.getNextDirection() == Direction.SOUTH &&
                            !(config.getCell(co).southWall()))
                    {
                        this.setPos(new RealCoordinates(co.x()  , co.y() + 0.001));
                        this.setDirection(Direction.SOUTH);
                        this.setNextDirection(Direction.NONE);
                    }
                }
            }
        }

        if (this.getDirection() == Direction.SOUTH) {
            if (Math.ceil(this.getPos().getY()) - this.getPos().getY() < 0.1 &&
                    this.getNextDirection() != Direction.NONE)
            {
                if (this.getNextDirection() == Direction.EAST ||
                        this.getNextDirection() == Direction.WEST)
                {
                    IntCoordinates co = new IntCoordinates(
                            (int) this.getPos().getX(),
                            (int) Math.ceil(this.getPos().getY())
                    );

                    if (this.getNextDirection() == Direction.EAST &&
                            !(config.getCell(co).eastWall()))
                    {
                        this.setPos(new RealCoordinates(co.x() + 0.001, co.y()));
                        this.setDirection(Direction.EAST);
                        this.setNextDirection(Direction.NONE);
                    }

                    if (this.getNextDirection() == Direction.WEST &&
                            !(config.getCell(co).westWall()))
                    {
                        this.setPos(new RealCoordinates(co.x() - 0.001, co.y()));
                        this.setDirection(Direction.WEST);
                        this.setNextDirection(Direction.NONE);
                    }
                }
            }
        }

        if (this.getDirection() == Direction.NORTH) {
            if (this.getPos().getY() - Math.floor(this.getPos().getY()) < 0.1 &&
                    this.getNextDirection() != Direction.NONE)
            {
                if (this.getNextDirection() == Direction.EAST ||
                        this.getNextDirection() == Direction.WEST)
                {
                    IntCoordinates co = new IntCoordinates(
                            (int) this.getPos().getX(),
                            (int) Math.floor(this.getPos().getY())
                    );

                    if (this.getNextDirection() == Direction.WEST &&
                            !(config.getCell(co).westWall()))
                    {
                        this.setPos(new RealCoordinates(co.x() - 0.001, co.y()));
                        this.setDirection(Direction.WEST);
                        this.setNextDirection(Direction.NONE);
                    }

                    if (this.getNextDirection() == Direction.EAST &&
                            !(config.getCell(co).eastWall()))
                    {
                        this.setPos(new RealCoordinates(co.x() + 0.001, co.y()));
                        this.setDirection(Direction.EAST);
                        this.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
    }

    /**
     * retourner les coordonnées de la case qui se trouve à 2 case devant pacman
     * @return
     */
    public IntCoordinates devantPacman() {
        if (PacMan.UN.getDirection() == Direction.NORTH) {
            if (PacMan.UN.getPos().round().y() - 2 < 2) {
                return new IntCoordinates(PacMan.UN.getPos().round().x(), 2);
            } else {
                return new IntCoordinates(PacMan.UN.getPos().round().x(), PacMan.UN.getPos().round().y() - 2);
            }
        }

        if (PacMan.UN.getDirection() == Direction.SOUTH) {
            if (PacMan.UN.getPos().round().y() + 2 > 12) {
                return new IntCoordinates(PacMan.UN.getPos().round().x(), 12);
            } else {
                return new IntCoordinates(PacMan.UN.getPos().round().x(), PacMan.UN.getPos().round().y() + 2);
            }
        }

        if (PacMan.UN.getDirection() == Direction.WEST) {
            if (PacMan.UN.getPos().round().x() - 2 < 0) {
                return new IntCoordinates(0, PacMan.UN.getPos().round().y());
            } else {
                return new IntCoordinates(PacMan.UN.getPos().round().x() - 2, PacMan.UN.getPos().round().y());
            }
        }

        if (PacMan.UN.getDirection() == Direction.EAST) {
            if (PacMan.UN.getPos().round().x() + 2 > 14) {
                return new IntCoordinates(14, PacMan.UN.getPos().round().y());
            } else {
                return new IntCoordinates(PacMan.UN.getPos().round().x() + 2, PacMan.UN.getPos().round().y());
            }
        } else {
            return PacMan.UN.getPos().round();
        }
    }
}
