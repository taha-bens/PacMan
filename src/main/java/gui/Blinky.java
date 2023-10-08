package gui;

import model.Direction;
import model.Ghost;
public class Blinky {
    public void BlinkyMove (){
        Ghost.BLINKY.setDirection(Direction.WEST);
    }
}

