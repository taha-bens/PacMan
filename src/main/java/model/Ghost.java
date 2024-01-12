package model;

import geometry.RealCoordinates;

public enum Ghost implements Critter {

    // TODO: implement a different AI for each ghost, according to the description in Wikipedia's page
    BLINKY, INKY, PINKY, CLYDE;

    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private Direction previousDir;
    private boolean isFrightened;
    private boolean isDead;

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public void setDirection(Direction direction) {
        previousDir = this.direction;
        this.direction = direction;
    }

    public Direction getPreviousDir() {
        return this.previousDir;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public boolean isFrightened() {
        return isFrightened;
    }
    public void setFrightened(boolean frightened) {
        isFrightened = frightened;
    }
    public boolean isDead() {
        return isDead;
    }
    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public double getSpeed() {
        if (isFrightened && !isDead){
            return 2;
        } else if (isFrightened){
            return 4;
        } else {
            return 3.6;
        }
    }


}