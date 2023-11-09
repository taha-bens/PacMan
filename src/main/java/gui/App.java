package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import config.MazeConfig;
import model.*;

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


    }
}