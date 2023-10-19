package model;

import geometry.RealCoordinates;

public class Blinky {
    public void BlinkyStart (){
        if (Ghost.BLINKY.getChase()){
            RealCoordinates pos = Ghost.BLINKY.getPos();
        }


        Ghost.BLINKY.setDirection(Direction.WEST);
    }
}

