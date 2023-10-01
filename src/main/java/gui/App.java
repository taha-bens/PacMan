package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import config.MazeConfig;
import model.MazeState;

//Import ajoutés
import gui.GameView;
import gui.PacmanController;

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
        var root = new Pane(); //organisateur de la fenêtre (stage)
        var gameScene = new Scene(root); //scène (comme caneva dans python tkinter)
        var pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler); //ajoute l'event pression sur la scene
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler); //ajoute l'event relachement sur la scene
        var maze = new MazeState(MazeConfig.makeExample1()); //données du labyrinthe
        var gameView = new GameView(maze, root, 70.0); //apparance graphique du précédent labyrinthe
        primaryStage.setScene(gameScene); //place la scèene dans la fenêtre "primaryStage"
        primaryStage.show(); //affiche la fenêtre
        gameView.animate(); //méthode de la classe "gameView" qui sert probablement à actualiser en continue le jeu
    }
}
