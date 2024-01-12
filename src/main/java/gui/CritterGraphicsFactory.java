package gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.*;

import java.util.ArrayList;
import java.util.Map;

import static model.Ghost.*;

public final class CritterGraphicsFactory {
    private final double scale;
    private final Map<Critter, String> paths;
    private final double size = 0.7;
    private final KeyFrame[] frightenedTimeline = new KeyFrame[9];

    /**
     * Constructeur de la classe
     * @param scale la taille
     */
    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        paths = Map.of(
                PacMan.UN, "pacman/PacmanL1.png",
                BLINKY, "red/RedL1.png",
                CLYDE, "orange/OrangeL1.png",
                INKY, "cyan/CyanL1.png",
                PINKY, "pink/PinkL1.png",
                PacMan.DEUX, "mspacman/msPacmanL1.png"
        );
    }

    public Image makeImage(String url){
        return new Image(url, scale * size, scale * size, true, true);
    }

    public GraphicsUpdater makeGraphics(MazeState maze, Critter critter) {
        String url = null;
        if (critter == PacMan.UN) {url = "gif/pacman/PacmanL.gif";}
                else if (critter == PacMan.DEUX) {url = "gif/mspacman/msPacmanL.gif";}
                else {
            switch ((Ghost) critter) {
                case BLINKY -> url = "gif/red/BlinkyL.gif";
                case CLYDE -> url = "gif/orange/ClydeL.gif";
                case INKY -> url = "gif/cyan/InkyL.gif";
                case PINKY -> url = "gif/pink/PinkyL.gif";
            };
        }


        ImageView empty = new ImageView(makeImage("1x1_empty.png"));

        ImageView image = new ImageView(makeImage(url));

        ImageView friB = new ImageView(makeImage("gif/frightened/frightenedBlue.gif"));
        ImageView friW = new ImageView(makeImage("gif/frightened/frightenedWhite.gif"));

        ImageView pacU = new ImageView(makeImage("gif/pacman/PacmanU.gif"));
        ImageView pacR = new ImageView(makeImage("gif/pacman/PacmanR.gif"));
        ImageView pacD = new ImageView(makeImage("gif/pacman/PacmanD.gif"));
        ImageView pacL = new ImageView(makeImage("gif/pacman/PacmanL.gif"));

        ImageView mspacU = new ImageView(makeImage("gif/mspacman/msPacmanU.gif"));
        ImageView mspacR = new ImageView(makeImage("gif/mspacman/msPacmanR.gif"));
        ImageView mspacD = new ImageView(makeImage("gif/mspacman/msPacmanD.gif"));
        ImageView mspacL = new ImageView(makeImage("gif/mspacman/msPacmanL.gif"));


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

        ImageView deadU = new ImageView(makeImage("dead/DeadU.png"));
        ImageView deadR = new ImageView(makeImage("dead/DeadR.png"));
        ImageView deadD = new ImageView(makeImage("dead/DeadD.png"));
        ImageView deadL = new ImageView(makeImage("dead/DeadL.png"));

        //Creation de la timeline de l'animation des fantomes frightened

        frightenedTimeline[0] = new KeyFrame(Duration.seconds(0), event -> {
            setSprites(critter, image, friB, friB, friB, friB);
        });

        double seconds = 6;
        for (int i = 1; i < 9; i++) {
            if (i % 2 == 0) {
                frightenedTimeline[i] = new KeyFrame(Duration.seconds(seconds), event -> {
                    setSprites(critter, image, friB, friB, friB, friB);
                });
            } else {
                frightenedTimeline[i] = new KeyFrame(Duration.seconds(seconds), event -> {
                    setSprites(critter, image, friW, friW, friW, friW);
                });
            }
            seconds += 0.25;
        }

        Timeline tml = new Timeline(frightenedTimeline);

        return new GraphicsUpdater() {

            @Override
            public void update() {
                if (critter.getPos()!=null) {
                    if (critter instanceof PacMan){
                        if (((PacMan) critter).equals(PacMan.UN)) {
                            setSprites(PacMan.UN, image, pacU, pacR, pacD, pacL);
                        } else if (!MazeState.getSp() && ((PacMan) critter).equals(PacMan.DEUX)) {
                            setSprites(PacMan.DEUX, image, mspacU, mspacR, mspacD, mspacL);
                        }
                    } else {
                        if (!((Ghost) critter).isFrightened()) {
                            tml.stop();
                            switch ((Ghost) critter) {
                                case BLINKY -> setSprites(critter, image, binU, binR, binD, binL);
                                case CLYDE -> setSprites(critter, image, clyU, clyR, clyD, clyL);
                                case INKY -> setSprites(critter, image, inkU, inkR, inkD, inkL);
                                case PINKY -> setSprites(critter, image, pinU, pinR, pinD, pinL);
                            }
                        } else {
                            if (maze.getEatSuperPacGum() || tml.getStatus() == Animation.Status.STOPPED) {
                                tml.playFromStart();
                            }
                        }
                        if (((Ghost) critter).isDead()){
                            setSprites(critter, image, deadU, deadR, deadD, deadL);
                        }
                    }
                    image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                    image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                }
                else {
                    setSprites(critter, image, empty, empty, empty, empty);
                }
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }

    /**
     * Changer l'image du critter en fonction de sa direction
     * @param critter
     * @param image
     * @param north
     * @param east
     * @param south
     * @param west
     */
    private void setSprites(Critter critter, ImageView image, ImageView north, ImageView east, ImageView south, ImageView west) {
        if (critter instanceof PacMan && critter.getPos()!=null && critter.getDirection() == Direction.NONE) {
            switch (critter.getPreviousDir()) {
                case NORTH -> image.setImage(north.getImage());
                case EAST -> image.setImage(east.getImage());
                case SOUTH -> image.setImage(south.getImage());
                case WEST -> image.setImage(west.getImage());
            }
        } else {
            switch (critter.getDirection()) {
                case NORTH -> image.setImage(north.getImage());
                case EAST -> image.setImage(east.getImage());
                case SOUTH -> image.setImage(south.getImage());
                case WEST, NONE -> image.setImage(west.getImage());
            }
        }
    }
}