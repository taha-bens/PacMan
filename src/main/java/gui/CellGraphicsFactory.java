package gui;

import config.Cell;
import geometry.IntCoordinates;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.MazeState;

import static config.Cell.Content.ENERGIZER;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    /**
     * Affiche le labyrinthe
     * @param state
     * @param pos
     * @return
     */
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        Group group = new Group();
        group.setTranslateX(pos.x() * scale);
        group.setTranslateY(pos.y() * scale);

        Cell cell = state.getConfig().getCell(pos);
        Circle dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(
                switch (cell.initialContent()) {
                    case DOT -> scale / 15;
                    case ENERGIZER -> scale / 5;
                    case NOTHING -> 0;
                }
        );
        dot.setCenterX(scale / 2);
        dot.setCenterY(scale / 2);

        // Condition si la pacgomme est une super pacgomme: faire clignoter la super pacgomme
        if (cell.initialContent() == ENERGIZER) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4), event -> {
                if (dot.getFill() == Color.YELLOW) {
                    dot.setFill(Color.BLACK);
                } else {
                    dot.setFill(Color.YELLOW);
                }
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        } else { // sinon : gomme jaune normale
            dot.setFill(Color.YELLOW);
        }

        if (cell.northWall()) {
            Rectangle northWall = new Rectangle();
            northWall.setHeight(scale / 10);
            northWall.setWidth(scale);
            northWall.setY(0);
            northWall.setX(0);
            if ((pos.x() == 6 && pos.y() == 7) || (pos.x() == 8 && pos.y() == 7)) {
                northWall.setFill(Color.BLACK);
            } else {
                northWall.setFill(Color.BLUEVIOLET);
            }
            group.getChildren().add(northWall);
        }

        if (cell.eastWall()) {
            Rectangle northWall = new Rectangle();
            northWall.setHeight(scale);
            northWall.setWidth(scale / 10);
            northWall.setY(0);
            northWall.setX(9 * scale / 10);
            if ((pos.x() == 7 && pos.y() == 7) ||
                    (pos.x() == 6 && pos.y() == 7) ||
                    (pos.x() == 8 && pos.y() == 7)
            ) {
                northWall.setFill(Color.BLACK);
            } else {
                northWall.setFill(Color.BLUEVIOLET);
            }
            group.getChildren().add(northWall);
        }

        if (cell.southWall()) {
            Rectangle northWall = new Rectangle();
            northWall.setHeight(scale / 10);
            northWall.setWidth(scale);
            northWall.setY(9 * scale / 10);
            northWall.setX(0);
            if ((pos.x() == 7 && pos.y() == 7) ||
                    (pos.x() == 6 && pos.y() == 7) ||
                    (pos.x() == 8 && pos.y() == 7)
            ) {
                northWall.setFill(Color.BLACK);
            } else if (pos.x() == 7 && pos.y() == 6) {
                northWall.setFill(Color.rgb(255, 217, 179));
            } else {
                northWall.setFill(Color.BLUEVIOLET);
            }
            group.getChildren().add(northWall);
        }

        if (cell.westWall()) {
            Rectangle northWall = new Rectangle();
            northWall.setHeight(scale);
            northWall.setWidth(scale / 10);
            northWall.setY(0);
            northWall.setX(0);
            northWall.setFill((pos.x() == 7 && pos.y() == 7) ?
                    Color.rgb(0, 0, 0) : Color.BLUEVIOLET
            );
            group.getChildren().add(northWall);
        }
        return new GraphicsUpdater() {

            @Override
            public void update() {
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}