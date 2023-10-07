package gui;

import geometry.IntCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;

import static config.Cell.Content.DOT;

//Imports ajoutés
import config.Cell;
import gui.GraphicsUpdater;
public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        Group group = new Group();
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        Cell cell = state.getConfig().getCell(pos);
        Circle dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) { case DOT -> scale/15; case ENERGIZER -> scale/5; case NOTHING -> 0; });
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.YELLOW);
        if (cell.northWall()) {
            Rectangle nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(0);
            nWall.setX(0);

            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.eastWall()) {
            Rectangle nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(9*scale/10);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.southWall()) {
            Rectangle nWall = new Rectangle();
            nWall.setHeight(scale/10);
            nWall.setWidth(scale);
            nWall.setY(9*scale/10);
            nWall.setX(0);
            System.out.println(pos.x() + "   " + state.getWidth());
            nWall.setFill((pos.x() == state.getWidth()/2 && pos.y() == state.getHeight()/2-1) ? Color.rgb(255,217,179) : Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        if (cell.westWall()) {
            Rectangle nWall = new Rectangle();
            nWall.setHeight(scale);
            nWall.setWidth(scale/10);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
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
