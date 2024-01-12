package gui;

import geometry.IntCoordinates;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Fruits;
import model.MazeState;
import model.PacMan;

import java.util.Map;

public class FruitsGraphicsFactory {

    private final double scale;

    private final Map<Fruits, String> paths;
    private final IntCoordinates fruitsCoord = new IntCoordinates(7, 4);
    private final double size = 0.7;
    private boolean flag = false;


    public FruitsGraphicsFactory(double scale) {
        this.scale = scale;
        paths = Map.of(Fruits.CHERRY, "Cherry.png");
    }

    public Image makeImage(String url){
        return new Image(url, scale * size, scale * size, true, true);
    }

    public GraphicsUpdater makeGraphics(MazeState state, Fruits fruit) {
        ImageView img = new ImageView(makeImage(paths.get(fruit)));
        img.setTranslateX((7 + (1 - size) / 2) * scale);
        img.setTranslateY((4 + (1 - size) / 2) * scale);
        img.setVisible(false);
        Timeline timelineFruitAppear = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    img.setVisible(true);
                    state.setFruitsGridState(fruitsCoord, false);
                })
        );

        Timeline timelineFruitDisappear = new Timeline(
                new KeyFrame(Duration.seconds(16), event -> {
                    img.setVisible(false);
                    state.setFruitsGridState(fruitsCoord, true);
                    flag = false;
                })
        );

        Timeline timelineFruitReset = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    timelineFruitAppear.play();
                    timelineFruitDisappear.play();
                })
        );

        return new GraphicsUpdater() {

            @Override
            public void update() {
                IntCoordinates pacPos = PacMan.UN.getPos().round();
                state.setFruitsGridState(pacPos, true);
                if (state.getFruitsGridState(fruitsCoord) && !flag) {
                    flag = true;
                    timelineFruitAppear.play();
                    timelineFruitDisappear.play();

                }
                if (img.isVisible()) {
                    if (state.getFruitsGridState(fruitsCoord)) {
                        img.setVisible(false);

                        if (pacPos.estEgal(fruitsCoord)) {PacMan.UN.addScore(100);}
                        else if (PacMan.DEUX.getPos().round().estEgal(fruitsCoord)) {PacMan.DEUX.addScore(100);}

                        //state.addScore(100);
                        timelineFruitDisappear.stop();
                        timelineFruitReset.play();
                    }
                }
            }

            @Override
            public Node getNode() {
                return img;
            }
        };
    }
}
