package gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import misc.Debug;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;

//Import ajouté
import gui.GraphicsUpdater;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import static model.Ghost.*;

public final class CritterGraphicsFactory {
    private final double scale;
    private String url;
    private static Timer timer;
    private final Map<Critter, String> paths;
    private final double size = 0.7;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        paths = Map.of(
                PacMan.INSTANCE, "pacman/PacmanL1.png",
                BLINKY, "red/RedL1.png",
                CLYDE, "orange/OrangeL1.png",
                INKY, "cyan/CyanL1.png",
                PINKY, "pink/PinkL1.png"
        );
    }
    public Image makeImage(String url){
        return new Image(url, scale*size, scale*size, true, true);
    }

    public GraphicsUpdater makeGraphics(Critter critter) {
        url = (critter instanceof PacMan) ? "gif/pacman/PacmanL.gif" :
                switch ((Ghost) critter) {
                    case BLINKY -> "gif/red/BlinkyL.gif";
                    case CLYDE -> "gif/orange/ClydeL.gif";
                    case INKY -> "gif/cyan/InkyL.gif";
                    case PINKY -> "gif/pink/PinkyL.gif";
                };
        ImageView image = new ImageView(makeImage(url));

        ImageView pacU = new ImageView(makeImage("gif/pacman/PacmanU.gif"));
        ImageView pacR = new ImageView(makeImage("gif/pacman/PacmanR.gif"));
        ImageView pacD = new ImageView(makeImage("gif/pacman/PacmanD.gif"));
        ImageView pacL = new ImageView(makeImage("gif/pacman/PacmanL.gif"));

        ImageView binU = new ImageView(makeImage("gif/red/BlinkyU.gif"));
        ImageView binR = new ImageView(makeImage("gif/red/BlinkyR.gif"));
        ImageView binD = new ImageView(makeImage("gif/red/BlinkyD.gif"));
        ImageView binL = new ImageView(makeImage("gif/red/BlinkyL.gif"));

        ImageView clyU = new ImageView(makeImage("gif/orange/ClydeU.gif"));
        ImageView clyR = new ImageView(makeImage("gif/orange/ClydeR.gif"));
        ImageView clyD = new ImageView(makeImage("gif/orange/ClydeD.gif"));
        ImageView clyL = new ImageView(makeImage("gif/orange/ClydeL.gif"));

        ImageView inkU = new ImageView(makeImage("gif/cyan/InkyU.gif"));
        ImageView inkR = new ImageView(makeImage("gif/cyan/InkyR.gif"));
        ImageView inkD = new ImageView(makeImage("gif/cyan/InkyD.gif"));
        ImageView inkL = new ImageView(makeImage("gif/cyan/InkyL.gif"));

        ImageView pinU = new ImageView(makeImage("gif/pink/PinkyU.gif"));
        ImageView pinR = new ImageView(makeImage("gif/pink/PinkyR.gif"));
        ImageView pinD = new ImageView(makeImage("gif/pink/PinkyD.gif"));
        ImageView pinL = new ImageView(makeImage("gif/pink/PinkyL.gif"));

        return new GraphicsUpdater() {


            @Override
            public void update() {
                image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);

                if(critter instanceof PacMan) {
                    setSprites(PacMan.INSTANCE, image, pacU, pacR, pacD, pacL);
                }

                if(critter instanceof Ghost) {
                    switch((Ghost) critter) {
                        case BLINKY -> setSprites(critter, image, binU, binR, binD, binL);
                        case CLYDE -> setSprites(critter, image, clyU, clyR, clyD, clyL);
                        case INKY -> setSprites(critter, image, inkU, inkR, inkD, inkL);
                        case PINKY -> setSprites(critter, image, pinU, pinR, pinD, pinL);
                    }
                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }

    private void setSprites(Critter critter, ImageView image, ImageView north, ImageView east, ImageView south, ImageView west) {
        switch(critter.getDirection()) {
            case NORTH -> image.setImage(north.getImage());
            case EAST -> image.setImage(east.getImage());
            case SOUTH -> image.setImage(south.getImage());
            case WEST -> image.setImage(west.getImage());
        }
    }
}