package gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import misc.Debug;
import model.Critter;
import model.Ghost;
import model.PacMan;

//Import ajoutÃ©
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

        return new GraphicsUpdater() {

            @Override
            public void update() {
                image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);

                image.setImage(makeImage(critterDetection(critter)));
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }

    /**
     *  Changer les sprites des critters en fonction des directions
     *  TODO: creer une methode vide qui change les images directement
     */
    public static String critterDetection(Critter cri) {
        return (cri instanceof PacMan) ?
                switch (PacMan.INSTANCE.getDirection()) {
                    case NORTH -> "gif/pacman/PacmanU.gif";
                    case EAST -> "gif/pacman/PacmanR.gif";
                    case SOUTH -> "gif/pacman/PacmanD.gif";
                    case WEST, NONE -> "gif/pacman/PacmanL.gif";
                }
                :
                switch ((Ghost) cri) {
                    case BLINKY -> switch (cri.getDirection()) {
                        case NORTH -> "gif/red/BlinkyU.gif";
                        case EAST -> "gif/red/BlinkyR.gif";
                        case SOUTH -> "gif/red/BlinkyD.gif";
                        case WEST, NONE -> "gif/red/BlinkyL.gif";
                    };

                    case CLYDE -> switch (cri.getDirection()) {
                        case NORTH -> "gif/orange/ClydeU.gif";
                        case EAST -> "gif/orange/ClydeR.gif";
                        case SOUTH -> "gif/orange/ClydeD.gif";
                        case WEST, NONE -> "gif/orange/ClydeL.gif";
                    };

                    case INKY -> switch (cri.getDirection()) {
                        case NORTH -> "gif/cyan/InkyU.gif";
                        case EAST -> "gif/cyan/InkyR.gif";
                        case SOUTH -> "gif/cyan/InkyD.gif";
                        case WEST, NONE -> "gif/cyan/InkyL.gif";
                    };

                    case PINKY -> switch  (cri.getDirection()) {
                        case NORTH -> "gif/pink/PinkyU.gif";
                        case EAST -> "gif/pink/PinkyR.gif";
                        case SOUTH -> "gif/pink/PinkyD.gif";
                        case WEST, NONE -> "gif/pink/PinkyL.gif";
                    };
                };
    }
}