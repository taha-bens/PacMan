package gui;

import model.Direction;
import model.Ghost;
public class Pinky {
    public void PinkyMove (){
        Ghost.PINKY.setDirection(Direction.NORTH);
    }
}