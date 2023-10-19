package model;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;

import java.util.List;
import java.util.Map;

import static model.Ghost.*;

import java.util.Set;

import javafx.scene.image.Image;
import model.Critter;
import model.Direction;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public final class MazeState {
    private final MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;

    private Pane root;
    private ImageView l1, l2, l3;

    public MazeState(MazeConfig config, double scale, Pane root) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );
        this.root = root;
        resetCritters();
        Image liveImage = new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true);
        l1 = new ImageView(new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true));
        l2 = new ImageView(new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true));
        l3 = new ImageView(new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true));
        ImageView[] liveImageTab = {l1,l2,l3};
        for (int i = 1; i <= 3; i++){
            this.root.getChildren().add(liveImageTab[i-1]);
            liveImageTab[i-1].setX(scale*(i-1)+5);
            liveImageTab[i-1].setY(710);
        }
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(long deltaTns) {
        // FIXME: too many things in this method. Maybe some responsibilities can be delegated to other methods or classes?
        for(Critter critter: critters) {
            RealCoordinates curPos = critter.getPos();
            RealCoordinates nextPos = critter.nextPos(deltaTns);
            Set<IntCoordinates> curNeighbours = curPos.intNeighbours();
            Set<IntCoordinates> nextNeighbours = nextPos.intNeighbours();

            if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
                switch (critter.getDirection()) {
                    case NORTH -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).northWall()) {
                            nextPos = curPos.floorY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case EAST -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).eastWall()) {
                            nextPos = curPos.ceilX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case SOUTH -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).southWall()) {
                            nextPos = curPos.ceilY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case WEST -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).westWall()) {
                            nextPos = curPos.floorX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }

            }

            critter.setPos(nextPos.warp(width, height));
        }
        // FIXME Pac-Man rules should somehow be in Pacman class
        IntCoordinates pacPos = PacMan.INSTANCE.getPos().round();
        if (!gridState[pacPos.y()][pacPos.x()]) {
            addScore(1);
            gridState[pacPos.y()][pacPos.x()] = true;
        }

        for (Critter critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) { // PacMan energisé tue un fantome
                    addScore(10);
                    resetCritter(critter);
                } else {
                    playerLost(); // PacMan touche un fantome
                    return;
                }
            }
        }
    }

    private void addScore(int increment) {
        score += increment;
        displayScore();
    }

    private void displayScore() {
        // FIXME: this should be displayed in the JavaFX view, not in the console
        System.out.println("Score: " + score);
    }

    private void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;
        if (lives == 2) this.root.getChildren().remove(l3);
        else if (lives == 1) this.root.getChildren().remove(l2);
        else if (lives == 0) {
            this.root.getChildren().remove(l1);
            System.out.println("Game over!");
            System.exit(0);
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (Critter critter: critters) resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    public int getScore(){return this.score;}

    public void upt(){
        if (PacMan.INSTANCE.getDirection() == Direction.EAST){
            if (Math.ceil( PacMan.INSTANCE.getPos().getX() ) - PacMan.INSTANCE.getPos().getX() < 0.04  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH || PacMan.INSTANCE.getNextDirection() == Direction.SOUTH){
                    System.out.println(" Avant tp : " + PacMan.INSTANCE.getPos());
                    IntCoordinates co = new IntCoordinates((int)Math.ceil (PacMan.INSTANCE.getPos().getX()) , (int)PacMan.INSTANCE.getPos().getY()  );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH && !(config.getCell(co).northWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y() - 0.001));
                        System.out.println(" Apres tp : " + PacMan.INSTANCE.getPos());
                        PacMan.INSTANCE.setDirection(Direction.NORTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.SOUTH && !(config.getCell(co).southWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()+ 0.001));
                        PacMan.INSTANCE.setDirection(Direction.SOUTH);
                        System.out.println(" Apres tp : " + PacMan.INSTANCE.getPos());
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.SOUTH){
            if (Math.ceil( PacMan.INSTANCE.getPos().getX() ) - PacMan.INSTANCE.getPos().getX() < 0.04  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.EAST || PacMan.INSTANCE.getNextDirection() == Direction.WEST){
                    IntCoordinates co = new IntCoordinates((int)PacMan.INSTANCE.getPos().getX() , (int)Math.ceil( PacMan.INSTANCE.getPos().getY() ) );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.EAST && !(config.getCell(co).eastWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() + 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.EAST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.WEST && !(config.getCell(co).westWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() - 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.WEST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.WEST){
            if ( PacMan.INSTANCE.getPos().getY() - Math.floor( PacMan.INSTANCE.getPos().getY() )  < 0.04  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH || PacMan.INSTANCE.getNextDirection() == Direction.SOUTH){
                    IntCoordinates co = new IntCoordinates((int)Math.floor (PacMan.INSTANCE.getPos().getX()) , (int)PacMan.INSTANCE.getPos().getY()  );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH && !(config.getCell(co).northWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()- 0.001));
                        PacMan.INSTANCE.setDirection(Direction.NORTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.SOUTH && !(config.getCell(co).southWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()+ 0.001));
                        PacMan.INSTANCE.setDirection(Direction.SOUTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.NORTH){
            if ( PacMan.INSTANCE.getPos().getX() - Math.floor( PacMan.INSTANCE.getPos().getX() )  < 0.04  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.EAST || PacMan.INSTANCE.getNextDirection() == Direction.WEST){
                    IntCoordinates co = new IntCoordinates((int)PacMan.INSTANCE.getPos().getX() , (int)Math.floor( PacMan.INSTANCE.getPos().getY() ) );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.WEST && !(config.getCell(co).westWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() - 0.001  , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.WEST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.EAST && !(config.getCell(co).eastWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() + 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.EAST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }

        }
    }

}
