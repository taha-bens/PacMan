package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;

//Import ajouté
import gui.GraphicsUpdater;

public final class CritterGraphicsFactory {
    private final double scale;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(Critter critter) {
        double size = 0.7;
        String url = (critter instanceof PacMan) ? "pacman/PacmanL1.png" : // PacmanController.changePacman('event')?
                switch ((Ghost) critter) {
                    case BLINKY -> "red/RedL1.png";
                    case CLYDE -> "orange/OrangeL1.png";
                    case INKY -> "cyan/CyanL1.png";
                    case PINKY -> "pink/PinkL1.png";
                };
        ImageView image = new ImageView(new Image(url, scale * size, scale * size, true, true));

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
