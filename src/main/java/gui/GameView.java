package gui;

import Pacman.src.main.java.model.Sound;
import geometry.IntCoordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import misc.Debug;
import model.*;


import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//Imports ajoutés
import gui.GraphicsUpdater;
import gui.CritterGraphicsFactory;
import gui.CellGraphicsFactory;
import model.PacMan;
import static model.Ghost.*;

import javax.sound.sampled.*;

public class GameView {
    // class parameters
    private final MazeState maze;
    private final Pane gameRoot; // main node of the game

    private final List<GraphicsUpdater> graphicsUpdaters;

    private static Label scoreLabel;

    private void addGraphics(GraphicsUpdater updater) {
        gameRoot.getChildren().add(updater.getNode());
        graphicsUpdaters.add(updater);
    }

    /**
     * @param maze  le "modèle" de cette vue (le labyrinthe et tout ce qui s'y trouve)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le nombre de pixels représentant une unité du labyrinthe
     */
    public GameView(MazeState maze, Pane root, double scale) {
        this.maze = maze;
        this.gameRoot = root;
        // pixels per cell
        root.setMinWidth(maze.getWidth() * scale);
        root.setMinHeight(maze.getHeight() * scale);
        root.setStyle("-fx-background-color: #000000");
        CritterGraphicsFactory critterFactory = new CritterGraphicsFactory(scale);
        CellGraphicsFactory cellFactory = new CellGraphicsFactory(scale);
        graphicsUpdaters = new ArrayList<>();

        for(Critter critter : maze.getCritters()) {
            addGraphics(critterFactory.makeGraphics(critter));
        }

        for(int x = 0; x < maze.getWidth(); x++){
            for(int y = 0; y < maze.getHeight(); y++) {
                addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));
            }
        }
        scoreLabel = new Label(String.valueOf(0));
        scoreLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 25));
        scoreLabel.setTextFill(Color.WHITE);
        this.gameRoot.getChildren().add(scoreLabel);
        scoreLabel.setTranslateX(50.0);
        scoreLabel.setTranslateY(10.0);
    }

    public void animate(){
        Sound.SOUND.playStartMusic();

        new AnimationTimer(){
            long last = 0;
            long sound_timer = 0;
            static long timerBrut = 0;
            long startMusicTimer = 0;
            boolean freezeBolean = true;
            @Override
            public void handle(long now) {
                if (freezeBolean){
                    freezeBolean = false;
                    for (var updater : graphicsUpdaters) {
                        updater.update();
                    }
                }
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                if (startMusicTimer > 4217000000L) {
                    if (!maze.vie2 && maze.getLives() == 2) {
                        timerBrut = 0;
                        maze.vie2 = true;
                    }
                    if (!maze.vie1 && maze.getLives() == 1) {
                        timerBrut = 0;
                        maze.vie1 = true;
                    }

                    if (maze.getHasEat() && sound_timer < 25000000) {
                        Sound.SOUND.playEatSound();
                    }

                    if (sound_timer > 607000000) {
                        sound_timer = 0;
                        maze.setHasEat(false);
                    }

                    long timerNet = timerBrut / 1000000000;
                    int intTimerNet = (int) timerNet;
                    var deltaT = now - last;
                    maze.update(deltaT);
                    maze.updatePacman();

                    if (BLINKY.getPos().round().estEgal(new IntCoordinates(7, 6)) && intTimerNet < 2) {
                        maze.blinkyStart();
                    }
                    if ((timerNet > 2) && PINKY.getPos().round().estEgal(new IntCoordinates(7, 7))) {
                        maze.pinkyStart();
                    }
                    if ((maze.getPacgum() > 30) && INKY.getPos().round().estEgal(new IntCoordinates(6, 7))) {
                        maze.inkyStart();
                    }
                    if (maze.getPacgum() > 60 && CLYDE.getPos().round().estEgal(new IntCoordinates(8, 7))) {
                        maze.clydeStart();
                    }


                    if (!PacMan.INSTANCE.isEnergized()) {
                        if (maze.isScatter(intTimerNet)) {
                            maze.scatterMode();
                        } else {
                            maze.chaseMode();
                        }
                    } else {
                        maze.frightenedMode();
                    }
                    scoreLabel.setText(String.valueOf(maze.getScore()));
                    for (var updater : graphicsUpdaters) {
                        updater.update();
                    }

                    if (maze.getHasEat()) {
                        sound_timer += now - last;
                    }

                    timerBrut += now - last;
                }
                startMusicTimer += now - last;
                last = now;
            }
        }.start();
    }
}
