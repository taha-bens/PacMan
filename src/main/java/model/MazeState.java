package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.GameOverView;
import gui.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

import static model.Ghost.*;

public final class MazeState {

    private Timer timer;
    private Timeline tml;

    private final MazeConfig config;

    public MazeConfig getconfig() {
        return config;
    }

    public final String level;
    private final int height;
    private final int width;
    public int pacgumTotal;

    private final boolean[][] gridState;
    private final boolean[][] fruitsGridState;

    private final List<Critter> critters;
    private final List<Fruits> fruits;
    private int score;
    private int pacgum;
    public boolean vie2;
    public boolean vie1;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;

    private Pane root;
    private ImageView l1, l2, l3;
    private boolean eatSuperPacGum;

    public MazeState(MazeConfig config, double scale, Pane root, String level) {
        this.config = config;
        this.level = level;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        fruits = List.of(Fruits.CHERRY);
        gridState = new boolean[height][width];
        fruitsGridState = new boolean[height][width];
        vie1 = false;
        vie2 = false;
        pacgumTotal = pacgumTotal(config);
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );
        this.root = root;
        resetCritters();
        Image liveImage = new Image("pacman/PacmanL1.png", scale * 0.7, scale * 0.7, true, true);
        l1 = new ImageView(liveImage);
        l2 = new ImageView(liveImage);
        l3 = new ImageView(liveImage);

        ImageView[] liveImageTab = { l1, l2, l3 };
        for (int i = 1; i <= 3; i++) {
            this.root.getChildren().add(liveImageTab[i - 1]);
            liveImageTab[i - 1].setX(scale * (i - 1) + 5);
            liveImageTab[i - 1].setY(710);
        }

        for (int i = 0; i < config.getGrid().length; i++) {
            for (int j = 0; j < config.getGrid()[i].length; j++) {
                gridState[i][j] = config.getGrid()[i][j].initialContent() == Cell.Content.NOTHING;
            }
        }
    }

    public int getPacgum() {
        return pacgum;
    }

    public int getPacgumTotal() {
        return this.pacgumTotal;
    }

    public int getLives() {
        return lives;
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public List<Fruits> getFruits() {
        return fruits;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int pacgumTotal(MazeConfig config) {
        int acc = 0;
        for (int i = 0; i < config.getGrid().length; i++) {
            for (int j = 0; j < config.getGrid()[i].length; j++) {
                if (config.getGrid()[i][j].initialContent() == Cell.Content.DOT){
                    acc++;
                }
            }
        }
        return acc;
    }

    public void startTimerEnergized() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                PacMan.INSTANCE.setEnergized(false);
            }
        };
        timer.schedule(timerTask, 8000);
    }
    public void resetTimerEnergized() {
        if (timer != null) {
            timer.cancel();
        }
        startTimerEnergized();
    }

    public void update(long deltaTns, Stage primaryStage) {
        // FIXME: too many things in this method. Maybe some responsibilities can be delegated to other methods or classes?
        for (Critter critter: critters) {
            RealCoordinates curPos = critter.getPos();
            RealCoordinates nextPos = critter.nextPos(deltaTns);
            Set<IntCoordinates> curNeighbours = curPos.intNeighbours();
            Set<IntCoordinates> nextNeighbours = nextPos.intNeighbours();

            if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
                switch (critter.getDirection()) {
                    case NORTH -> {
                        for (IntCoordinates n : curNeighbours) {
                            if (config.getCell(n).northWall()) {
                                nextPos = curPos.floorY();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                    case EAST -> {
                        for (IntCoordinates n : curNeighbours) {
                            if (config.getCell(n).eastWall()) {
                                nextPos = curPos.ceilX();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                    case SOUTH -> {
                        for (IntCoordinates n : curNeighbours) {
                            if (config.getCell(n).southWall()) {
                                nextPos = curPos.ceilY();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                    case WEST -> {
                        for (IntCoordinates n : curNeighbours) {
                            if (config.getCell(n).westWall()) {
                                nextPos = curPos.floorX();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                }
            }
            critter.setPos(nextPos.warp(width, height));
        }
        // FIXME Pac-Man rules should somehow be in Pacman class
        IntCoordinates pacPos = PacMan.INSTANCE.getPos().round();
        if (!gridState[pacPos.y()][pacPos.x()]){
            gridState[pacPos.y()][pacPos.x()] = true;
            fruitsGridState[pacPos.y()][pacPos.x()] = true;
            if (this.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER) {
                eatSuperPacGum = true;
                tml.play();
                PacMan.INSTANCE.setEnergized(true);
                Mode.backward();
                addScore(50);
                resetTimerEnergized();
            } else {
                addScore(10);
                incrPacgum();
            }
        }



        for (Critter critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) { // PacMan energisÃ© tue un fantome
                    addScore(200);
                    resetCritter(critter);
                } else {
                    //playerLost(primaryStage);
                    GameView.setIsDead(true); // PacMan touche un fantome
                    return;
                }
            }
        }

        tml = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> {
                    setEatSuperPacGum(false);
                })
        );
    }

    public void addScore(int increment) {
        Sound.SOUND.playEatSound();
        score += increment;
    }

    public boolean getEatSuperPacGum() {
        return eatSuperPacGum;
    }

    public void setEatSuperPacGum(boolean eatSuperPacGum) {
        this.eatSuperPacGum = eatSuperPacGum;
    }

    private void incrPacgum() {
        pacgum++;
    }

    public void playerLost(Stage primaryStage) {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;

        if (lives == 2) {
            this.root.getChildren().remove(l3);
        } else if (lives == 1) {
            this.root.getChildren().remove(l2);
        } else if (lives == 0) {
            this.root.getChildren().remove(l1);
            Sound.SOUND.stopStartMusic();
            GameView.getReload().stop();
            // --- Game Over Scene
            Pane gameoverroot = new Pane();
            GameOverView menuView = new GameOverView(gameoverroot, primaryStage, score, this.level);
            Scene gameOverScene = new Scene(gameoverroot, 500, 600);
            gameOverScene.setFill(Paint.valueOf("#000000"));

            primaryStage.setScene(gameOverScene);
            primaryStage.setTitle("Pacman - Game Over");
            primaryStage.setResizable(false);
            // ---
        }
        resetCritters();
    }

    private void resetCritter(Critter critter) {
        critter.setPos(initialPos.get(critter));
        critter.setDirection(Direction.NONE);
    }

    private void resetCritters() {
        for (Critter critter: critters) {
            resetCritter(critter);
        }
    }
    public void resetBlinky() {
        BLINKY.setPos(new RealCoordinates(7, 7));
        BLINKY.setDirection(Direction.NONE);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    public boolean getFruitsGridState(IntCoordinates pos) {
        return fruitsGridState[pos.y()][pos.x()];
    }

    public void setFruitsGridState(IntCoordinates pos, boolean b) {
        fruitsGridState[pos.y()][pos.x()] = b;
    }

    public int getScore() {
        return this.score;
    }
}