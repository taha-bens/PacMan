package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Sound;

/**
 * La classe App s'occupe du lancement du jeu, c'est la première classe à être appelée
 */
public class App extends Application {

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     *
     * initialise le lancement du jeu, en créant une fenêtre,
     * une scène dans laquelle les différents éléments sont placés,
     * une configuration de labyrinthe (pour ensuite créer le labyrinthe),
     */
    @Override
    public void start(Stage primaryStage) {

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
        Sound.SOUND.playMainMusicLoop();
    }
}