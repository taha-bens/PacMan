package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import config.MazeConfig;
import model.*;
import geometry.*;

//Import ajoutés
import javafx.scene.image.Image;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

//La classe App s'occupe du lancement du jeu, c'est la première classe à être appelée
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Méthode : start
        //Input : primaryStage (Stage) : fenêtre du jeu
        //Output : void
        /*Description : initialise le lancement du jeu, en créant une fenêtre,
         * une scène dans laquelle les différents éléments sont placés,
         * une configuration de labyrinthe (pour ensuite créer le labyrinthe),
         * */

        Pane menuroot = new Pane();
        MenuView menuView = new MenuView(menuroot, primaryStage);
        Scene menuScene = new Scene(menuroot, 500, 600);
        menuScene.setFill(Paint.valueOf("#000000"));

        primaryStage.setScene(menuScene);

        primaryStage.setTitle("Pacman - Menu");
        Image icon = new Image("pacman/PacmanR1.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.show(); //affiche la fenêtre

        /*AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(new File("src/main/resources/PacmanMainMusic.wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        Clip clip;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(audio);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
        clip.start();
        new AnimationTimer(){
            long last = 0;
            long sound_timer = 0;

            @Override
            public void handle(long now){
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                if (sound_timer > 29540000000L){
                    AudioInputStream audio;
                    try {
                        audio = AudioSystem.getAudioInputStream(new File("src/main/resources/PacmanMainMusic.wav"));
                    } catch (UnsupportedAudioFileException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    Clip clip;
                    try {
                        clip = AudioSystem.getClip();
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        clip.open(audio);
                    } catch (LineUnavailableException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    clip.start();
                    System.out.println(sound_timer);
                    sound_timer = 0;

                }
                sound_timer += now-last;
                last = now;
            }
        }.start();*/

    }
}