package gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
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
    private static String url;
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
         url = (critter instanceof PacMan) ? "pacman/PacmanL1.png" : // PacmanController.changePacman('event')?
                switch ((Ghost) critter) {
                    case BLINKY -> "red/RedL1.png";
                    case CLYDE -> "orange/OrangeL1.png";
                    case INKY -> "cyan/CyanL1.png";
                    case PINKY -> "pink/PinkL1.png";
                };

        ImageView image = new ImageView(makeImage(url));


        return new GraphicsUpdater() {

            @Override
            public void update() {
                image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                // Debug.out("sprite updated");
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
