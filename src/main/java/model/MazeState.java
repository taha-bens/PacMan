package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.GameOverMultiView;
import gui.GameOverView;
import gui.GameView;
import gui.PacmanController;
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
    private Timeline timelineEatSuperPacGumStatus;

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
    public boolean isFirst = true;

    private final Map<Critter, RealCoordinates> initialPos;
    private final Map<Critter, Direction> initialDir;
    private int lives = 3;
    private static boolean sp;

    private Pane root;
    private ImageView l1, l2, l3;
    private boolean eatSuperPacGum;
    private int ghostEatenBySuperPacGum;

    private String mazeLocate;

    public MazeState(MazeConfig config, double scale, Pane root, String level, Stage primaryStage, int oldScore, int lives, boolean sp) {
        this.config = config;
        this.level = level;
        this.score = oldScore;
        this.lives = (lives<=3)?(lives):(3);
        this.sp = sp;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.UN, Ghost.CLYDE, BLINKY, INKY, PINKY, PacMan.DEUX);
        fruits = List.of(Fruits.CHERRY);
        gridState = new boolean[height][width];
        fruitsGridState = new boolean[height][width];
        vie1 = false;
        vie2 = false;
        pacgumTotal = pacgumTotal(config);
        if (sp) {
            initialPos = Map.of(
                PacMan.UN, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );}
        else {
            initialPos = Map.of(
                    PacMan.UN, config.getPacManPos().toRealCoordinates(1.0),
                    PacMan.DEUX, config.getPacMan2Pos().toRealCoordinates(1.0)
            );}

        initialDir = Map.of(
                PacMan.UN, Direction.WEST,
                BLINKY, Direction.NORTH,
                INKY, Direction.EAST,
                CLYDE, Direction.WEST,
                PINKY, Direction.NORTH
        );
        this.root = root;
        resetCritters();
        Image liveImage = new Image("pacman/PacmanL1.png", scale * 0.7, scale * 0.7, true, true);
        l1 = new ImageView(liveImage);
        l2 = new ImageView(liveImage);
        l3 = new ImageView(liveImage);

        ImageView[] liveImageTab = { l1, l2, l3 };
        if (sp){
            for (int i = 1; i <= 3; i++) {
                this.root.getChildren().add(liveImageTab[i - 1]);
                liveImageTab[i - 1].setX(scale * (i - 1) + 5);
                liveImageTab[i - 1].setY(710);
            }
        }

        switch (this.lives) {
            case 1 -> {
                this.root.getChildren().remove(l2);
                this.root.getChildren().remove(l3);
                break;
            }

            case 2 -> {
                this.root.getChildren().remove(l3);
                break;
            }
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
    public boolean getIsFirst(){return isFirst;}

    public int getPacgumTotal() {
        return this.pacgumTotal;
    }

    public int getLives() {
        return lives;
    }

    public static boolean getSp(){return sp;}
    public void setSp(boolean sp) {
        MazeState.sp = sp;
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
                PacMan.UN.setEnergized(false);
                if (sp) {
                    for (Critter c : critters) {
                        if (c instanceof Ghost && !((Ghost) c).isDead()) {
                            ((Ghost) c).setFrightened(false);
                        }
                    }
                }
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

    public void update(long deltaTns, Stage primaryStage, boolean sp) {
        // FIXME: too many things in this method. Maybe some responsibilities can be delegated to other methods or classes?
        for (Critter critter: critters) {
            if (critter.getPos()!=null) {

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
                                    if (critter == BLINKY) {
                                        critter.setDirection(Direction.WEST);
                                    } else {
                                        critter.setDirection(Direction.NONE);
                                    }
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
                                    if (critter == BLINKY) {
                                        BLINKY.setDirection(Direction.NORTH);
                                    } else {
                                        critter.setDirection(Direction.NONE);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                critter.setPos(nextPos.warp(width, height));

            }}
        // FIXME Pac-Man rules should somehow be in Pacman class

        IntCoordinates pacPos = PacMan.UN.getPos().round();
        if (!gridState[pacPos.y()][pacPos.x()]) {
            gridState[pacPos.y()][pacPos.x()] = true;
            fruitsGridState[pacPos.y()][pacPos.x()] = true;
            if (this.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER) {
                ghostEatenBySuperPacGum = 200;
                eatSuperPacGum = true;
                timelineEatSuperPacGumStatus.play();
                PacMan.UN.setEnergized(true);
                Mode.backward();
                PacMan.UN.addScore(50);
                resetTimerEnergized();
                for (Critter c : critters) {
                    if (c instanceof Ghost) {
                        ((Ghost) c).setFrightened(true);
                    }
                }
            } else {
                if (this.getConfig().getCell(pacPos).initialContent() == Cell.Content.DOT) {
                    PacMan.UN.addScore(10);
                    incrPacgum();
                }
            }


            if (pacgum==pacgumTotal) {
                pacgum = 0;
                isFirst = true;
                GameView.getReload().stop();
                switch(level){
                    case "1" : mazeLocate = "src/main/resources/maze"+level+".txt"; break;
                    case "2" : mazeLocate = "src/main/resources/maze"+level+".txt"; break;
                    case "3" : mazeLocate = "src/main/resources/maze"+level+".txt"; break;
                    case "4", "5": playerLost(primaryStage); break;
                    default : mazeLocate = level; break;
                }

                if (sp){
                    Pane root = new Pane(); //organisateur de la fenêtre (stage)
                    Scene gameScene = new Scene(root); //scène (comme caneva dans python tkinter)
                    PacmanController pacmanController = new PacmanController();
                    gameScene.setOnKeyPressed(pacmanController::keyPressedHandler); //ajoute l'event pression sur la scene
                    gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler); //ajoute l'event relachement sur la scene

                    MazeState maze = new MazeState(MazeConfig.makeMazeFINAL(mazeLocate, sp), 50.0, root, level, primaryStage, 0, this.lives, sp); //données du labyrinthe
                    GameView gameView = new GameView(maze, root, 50.0, sp); //apparance graphique du précédent labyrinthe
                    PacMan.UN.setEnergized(false);

                    primaryStage.setScene(gameScene);
                    primaryStage.setTitle("Pacman");
                    primaryStage.setResizable(true);

                    Sound.SOUND.stopMainMusicLoop();
                    gameView.animate(primaryStage);
                }
            }
        }

        if (!sp) {
            IntCoordinates pacPos2 = PacMan.DEUX.getPos().round();
            if (!gridState[pacPos2.y()][pacPos2.x()]) {
                gridState[pacPos2.y()][pacPos2.x()] = true;
                fruitsGridState[pacPos2.y()][pacPos2.x()] = true;
                if (this.getConfig().getCell(pacPos2).initialContent() == Cell.Content.DOT) {
                    PacMan.DEUX.addScore(10);
                    incrPacgum();
                }
                if (pacgum==pacgumTotal) {playerLost(primaryStage);}
            }
        }


        for (Critter critter : critters) {
            if (sp) {
                if (critter instanceof Ghost) {
                    critterBackHome(critter);
                    if (critter.getPos().round().equals(pacPos)){
                        if (((Ghost) critter).isFrightened()) {// PacMan energisÃ© tue un fantome
                            if (!((Ghost) critter).isDead()){
                                ((Ghost) critter).setDead(true);
                                addScore(ghostEatenBySuperPacGum);
                                ghostEatenBySuperPacGum += 200;
                            }
                        } else {
                            GameView.setIsDead(true); // PacMan touche un fantome
                            return;
                        }
                    }
                }
            }
        }

        timelineEatSuperPacGumStatus = new Timeline(
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
    public void setIsFirst (boolean b){isFirst = b;}

    private void incrPacgum() {
        pacgum++;
    }

    public void playerLost(Stage primaryStage) {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;

        if (!sp) {
            Sound.SOUND.stopStartMusic();
            GameView.getReload().stop();

            Pane gameoverroot = new Pane();
            GameOverMultiView menuview = new GameOverMultiView(gameoverroot, primaryStage, PacMan.UN.getScore(), PacMan.DEUX.getScore());
            PacMan.UN.setScore(0);
            PacMan.DEUX.setScore(0);

            Scene gameOverScene = new Scene(gameoverroot, 500, 600);
            gameOverScene.setFill(Paint.valueOf("#000000"));

            primaryStage.setScene(gameOverScene);
            primaryStage.setTitle("Pacman - Game Over");
            primaryStage.setResizable(false);
        } else if (lives == 2) {
            this.root.getChildren().remove(l3);
        } else if (lives == 1) {
            this.root.getChildren().remove(l2);
        } else if (lives == 0) {

            this.root.getChildren().remove(l1);
            Sound.SOUND.stopStartMusic();
            GameView.getReload().stop();
            // --- Game Over Scene
            Pane gameoverroot = new Pane();
            GameOverView menuView = new GameOverView(gameoverroot, primaryStage, PacMan.UN.getScore(), this.level);
            PacMan.UN.setScore(0);

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
        if (critter instanceof Ghost) {
            ((Ghost) critter).setFrightened(false);
            ((Ghost) critter).setDead(false);
        }
    }

    private void critterBackHome(Critter critter){
        IntCoordinates critPos = critter.getPos().round();
        if (critPos.equals(Mode.homeObjectif()) && ((Ghost) critter).isDead()){
            critter.setPos(initialPos.get(PINKY));
            critter.setDirection(initialDir.get(critter));
            ((Ghost) critter).setDead(false);
            ((Ghost) critter).setFrightened(false);
        }
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