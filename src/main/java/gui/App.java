package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import config.MazeConfig;
import model.*;
import geometry.*;

//Import ajoutés
import gui.GameView;
import gui.PacmanController;
import javafx.scene.image.Image;

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
        Pane root = new Pane(); //organisateur de la fenêtre (stage)
        Scene gameScene = new Scene(root); //scène (comme caneva dans python tkinter)

        
        PacmanController pacmanController = new PacmanController();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //test

            }
        }, 0, 50);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler); //ajoute l'event pression sur la scene
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler); //ajoute l'event relachement sur la scene
        
        MazeState maze = new MazeState(MazeConfig.makeMazeFINAL("src/main/resources/maze1.txt"), 50.0, root); //données du labyrinthe
        GameView gameView = new GameView(maze, root, 50.0); //apparance graphique du précédent labyrinthe
        
        primaryStage.setScene(gameScene); //place la scène dans la fenêtre "primaryStage"

        primaryStage.setTitle("Pacman");
        Image icon = new Image("pacman/PacmanL1.png");
        primaryStage.getIcons().add(icon);

        primaryStage.show(); //affiche la fenêtre
        gameView.animate(); //méthode de la classe "gameView" qui sert à actualiser en continue le jeu
    }
}