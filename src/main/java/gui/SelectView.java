package gui;

import config.MazeConfig;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.MazeState;
import model.Sound;

import java.io.File;

public class SelectView {

    private final Pane gameroot;
    private String mazeLocate;
    public Stage primaryStage;

    public void setMazeLocate(String txt) {
        this.mazeLocate = txt;
    }

    /**
     * le constructeur de selectView crée  la scene avec les boutons
     * les boutons changent l'emplacement du labyrinthe
     * (grace a setMazeLocate) a charger puis changent la scene
     *
     * @param gameroot
     * @param mazeLocate
     * @param primaryStage
     */
    public SelectView(Pane gameroot, String mazeLocate, Stage primaryStage) {
        this.gameroot = gameroot;
        this.mazeLocate = mazeLocate;
        this.primaryStage = new Stage();
        //this.gameroot.setStyle("-fx-background-color: #00ff00");
        //l'image de fond est derrière le fond :|

        //background image
        Image img = new Image("file:src/main/resources/bg_select.png", 1080, 720, false, true);
        BackgroundImage myBgI = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        //titre
        Text titre = new Text("SELECTIONNER\n     LE\n   NIVEAU");
        titre.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 30));
        titre.setFill(Color.GOLDENROD);
        titre.setTextOrigin(VPos.CENTER);
        titre.setTranslateX(365);
        titre.setTranslateY(100);

        //lab1 button
        Button lab1 = new Button("\n\n\n\n\n\n\n\n\n\n\nLabyrinthe 1");
        lab1.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
        lab1.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        lab1.setTextFill(Color.GOLDENROD);
        lab1.setPrefSize(270, 210);
        lab1.setTranslateX(90);
        lab1.setTranslateY(200);
        lab1.setOnAction(event -> {
            setMazeLocate("src/main/resources/maze1.txt");
            setScene(primaryStage, "1");
        });


        //lab2 button
        Button lab2 = new Button("\n\n\n\n\n\n\n\n\n\n\nLabyrinthe 2");
        lab2.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
        lab2.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        lab2.setTextFill(Color.GOLDENROD);
        lab2.setPrefSize(270, 210);
        lab2.setTranslateX(405);
        lab2.setTranslateY(200);
        lab2.setOnAction(event -> {
            setMazeLocate("src/main/resources/maze2.txt");
            setScene(primaryStage, "2");
        });


        //lab3 button
        Button lab3 = new Button("\n\n\n\n\n\n\n\n\n\n\nLabyrinthe 3");
        lab3.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
        lab3.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        lab3.setTextFill(Color.GOLDENROD);
        lab3.setPrefSize(270, 210);
        lab3.setTranslateX(720);
        lab3.setTranslateY(200);
        lab3.setOnAction(event -> {
            setMazeLocate("src/main/resources/maze3.txt");
            setScene(primaryStage, "3");
        });


        //labUser et tt ce qui va avec
        Button labUser = new Button("\nLabyrinthe\nutilisateur\n\n");
        labUser.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");
        labUser.setTextFill(Color.GOLDENROD);
        labUser.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        labUser.setPrefSize(270, 210);
        labUser.setTranslateX(405);
        labUser.setTranslateY(475);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier de labyrinthe");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        labUser.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                setMazeLocate(selectedFile.getAbsolutePath());
                setScene(primaryStage, selectedFile.toPath().toString());
            }

        });

        this.gameroot.getChildren().add(titre);
        this.gameroot.getChildren().add(lab1);
        this.gameroot.getChildren().add(lab2);
        this.gameroot.getChildren().add(lab3);
        this.gameroot.getChildren().add(labUser);
        this.gameroot.setBackground(new Background(myBgI));
    }

    public void setScene(Stage primaryStage, String level) {
        //organisateur de la fenêtre (stage)
        Pane root = new Pane();
        //scène (comme caneva dans python tkinter)
        Scene gameScene = new Scene(root);
        PacmanController pacmanController = new PacmanController();
        //ajoute l'event pression sur la scene
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        //ajoute l'event relachement sur la scene
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);

        //données du labyrinthe
        MazeState maze = new MazeState(MazeConfig.makeMazeFINAL(mazeLocate), 50.0, root, level);
        //apparance graphique du précédent labyrinthe
        GameView gameView = new GameView(maze, root, 50.0);

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Pacman");
        primaryStage.setResizable(true);

        Sound.SOUND.stopMainMusicLoop();
        //méthode de la classe "gameView" qui sert à actualiser en continue le jeu
        gameView.animate(primaryStage);
    }
}