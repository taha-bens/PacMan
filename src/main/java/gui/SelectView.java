package gui;

import config.MazeConfig;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.text.*;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class SelectView {
    private final Pane gameroot;
    private String mazeLocate;
    public Stage primaryStage;

    /*
    le constructeur de selectView crée  la scene avec les boutons
    les boutons changent l'emplacement du labyrinthe (grace a SetMazeLocate) a charger puis changent la scene

     */


    public void SetMazeLocate(String txt) {
        this.mazeLocate = txt;
        //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }


    public SelectView(Pane gameroot, String mazeLocate, Stage primaryStage) {
        this.gameroot = gameroot;
        this.mazeLocate=mazeLocate;
        this.primaryStage =new Stage();
        //this.gameroot.setStyle("-fx-background-color: #00ff00");
        //l'image de fond est derrière le fond :|

        //background image
        BackgroundImage myBI= new BackgroundImage(new Image("file:src/main/resources/bg_select.png",1080,720,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
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
        lab1.setStyle("-fx-background-color: rgba(0,0,0,0)");
        lab1.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        lab1.setTextFill(Color.GOLDENROD);
        lab1.setPrefSize(270,210);
        lab1.setTranslateX(90);
        lab1.setTranslateY(200);
        lab1.setOnAction(event -> {
            //System.out.println("click lab1");
            SetMazeLocate("src/main/resources/maze1.txt");
            SetScene(primaryStage, 1);
        });


        //lab2 button
        Button lab2 = new Button("\n\n\n\n\n\n\n\n\n\n\nLabyrinthe 2");
        lab2.setStyle("-fx-background-color: rgba(0,0,0,0)");
        lab2.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        lab2.setTextFill(Color.GOLDENROD);
        lab2.setPrefSize(270,210);
        lab2.setTranslateX(405);
        lab2.setTranslateY(200);
        lab2.setOnAction(event -> {
            //System.out.println("click lab2");
            SetMazeLocate("src/main/resources/maze2.txt");
            SetScene(primaryStage, 2);
        });



        //lab3 button
        Button lab3 = new Button("\n\n\n\n\n\n\n\n\n\n\nLabyrinthe 3");
        lab3.setStyle("-fx-background-color: rgba(0,0,0,0)");
        lab3.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        lab3.setTextFill(Color.GOLDENROD);
        lab3.setPrefSize(270,210);
        lab3.setTranslateX(720);
        lab3.setTranslateY(200);
        lab3.setOnAction(event -> {
            //System.out.println("click lab3");
            SetMazeLocate("src/main/resources/maze3.txt");
            SetScene(primaryStage, 3);
        });


        //labUser et tt ce qui va avec
        Button labUser = new Button("\nLabyrinthe\nutilisateur\n\n");
        labUser.setStyle("-fx-background-color: rgba(0,0,0,0)");
        labUser.setTextFill(Color.GOLDENROD);
        labUser.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 12));
        labUser.setPrefSize(270, 210);
        labUser.setTranslateX(405);
        labUser.setTranslateY(475);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier de labyrinthe");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        labUser.setOnAction(actionEvent -> {
            //System.out.println("click labUser");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                SetMazeLocate(selectedFile.getAbsolutePath());
                SetScene(primaryStage, 0);
            }

        });


    this.gameroot.getChildren().add(titre);
    this.gameroot.getChildren().add(lab1);
    this.gameroot.getChildren().add(lab2);
    this.gameroot.getChildren().add(lab3);
    this.gameroot.getChildren().add(labUser);
    this.gameroot.setBackground(new Background(myBI));

    }



    public void SetScene(Stage primaryStage, int level){
        Pane root = new Pane(); //organisateur de la fenêtre (stage)
        Scene gameScene = new Scene(root); //scène (comme caneva dans python tkinter)

        PacmanController pacmanController = new PacmanController();
        Blinky rouge = new Blinky();
        Inky bleu = new Inky();
        Pinky rose = new Pinky();
        Clyde orange = new Clyde();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //test

            }
        }, 0, 50);
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler); //ajoute l'event pression sur la scene
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler); //ajoute l'event relachement sur la scene

        MazeState maze = new MazeState(MazeConfig.makeMazeFINAL(mazeLocate), 50.0, root, level); //données du labyrinthe
        GameView gameView = new GameView(maze, root, 50.0); //apparance graphique du précédent labyrinthe

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Pacman");
        primaryStage.setResizable(true);

        gameView.animate(primaryStage); //méthode de la classe "gameView" qui sert à actualiser en continue le jeu
    }




}



