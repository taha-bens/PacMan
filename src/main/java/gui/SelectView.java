package gui;

import javafx.application.Platform;
import javafx.scene.paint.Paint;
import model.*;
import config.MazeConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import java.io.File;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SelectView {
    private final Pane selectRoot;
    private String mazeLocate;
    public Stage primaryStage;

    public Button singlePLayer;
    public Button multiPLayer;
    public boolean singlePlayerFlag = false;
    public boolean multiPlayerFlag = true;

    public void SetMazeLocate(String txt) {
        this.mazeLocate = txt;
    }

    /**
     * le constructeur de selectView crée  la scene avec les boutons
     * les boutons changent l'emplacement du labyrinthe
     * (grace a setMazeLocate) a charger puis changent la scene
     *
     * @param selectRoot
     * @param mazeLocate
     * @param primaryStage
     */


    public SelectView(Pane selectRoot, String mazeLocate, Stage primaryStage) {
        this.selectRoot = selectRoot;
        this.mazeLocate = mazeLocate;
        this.primaryStage = new Stage();
        this.selectRoot.setStyle("-fx-background-color: #000000");

        //titre
        Label titleLabel = new Label("SELECTEUR DE LABYRINTHE");
        titleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 30));
        titleLabel.setTextFill(Color.GOLD);
        titleLabel.setPrefSize(1080, 50);
        titleLabel.setTranslateX(0);
        titleLabel.setTranslateY(70);
        titleLabel.setAlignment(Pos.CENTER);

        // Rectangle
        Rectangle rectangle = new Rectangle();
        /*
        rectangle.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #8A2BE2;" +
                        "-fx-border-width: 5px;"
        );
        */
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.web("#8A2BE2"));
        rectangle.setStrokeWidth(5);
        rectangle.setWidth(945);
        rectangle.setHeight(395);
        rectangle.setX(67.5);
        rectangle.setY(250);

        //lab1 button
        Button lab1 = new Button();
        lab1.setBackground(Background.EMPTY);
        //lab1.setStyle("-fx-background-color: #FF0000");
        lab1.setTextFill(Color.GOLD);
        lab1.setPrefSize(275,250);
        lab1.setTranslateX(105);
        lab1.setTranslateY(290);
        lab1.setOnAction(event -> {
            SetMazeLocate("src/main/resources/maze1.txt");
            SetScene(primaryStage, "1");
        });

        //labLabel1
        Label labLabel1 = new Label("LABYRINTHE 1");
        labLabel1.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        labLabel1.setTextFill(Color.GOLD);
        labLabel1.setPrefSize(275, 32);
        labLabel1.setTranslateX(105);
        labLabel1.setTranslateY(300);
        labLabel1.setAlignment(Pos.CENTER);

        //lab1Image
        ImageView lab1Image = new ImageView(new Image("maze1_background.png"));
        lab1Image.setFitWidth(275);
        lab1Image.setFitHeight(150);
        lab1Image.setLayoutX(105);
        lab1Image.setLayoutY(350);

        //lab2 button
        Button lab2 = new Button();
        lab2.setBackground(Background.EMPTY);
        //lab2.setStyle("-fx-background-color: #FF0000");
        lab2.setTextFill(Color.GOLD);
        lab2.setPrefSize(275,250);
        lab2.setTranslateX(402.5);
        lab2.setTranslateY(290);
        lab2.setOnAction(event -> {
            SetMazeLocate("src/main/resources/maze2.txt");
            SetScene(primaryStage, "2");
        });

        //labLabel2
        Label labLabel2 = new Label("LABYRINTHE 2");
        labLabel2.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        labLabel2.setTextFill(Color.GOLD);
        labLabel2.setPrefSize(275, 32);
        labLabel2.setTranslateX(402.5);
        labLabel2.setTranslateY(300);
        labLabel2.setAlignment(Pos.CENTER);

        //lab2Image
        ImageView lab2Image = new ImageView(new Image("maze2_background.png"));
        lab2Image.setFitWidth(275);
        lab2Image.setFitHeight(150);
        lab2Image.setLayoutX(402.5);
        lab2Image.setLayoutY(350);

        //lab3 button
        Button lab3 = new Button();
        lab3.setBackground(Background.EMPTY);
        //lab3.setStyle("-fx-background-color: #FF0000");
        lab3.setTextFill(Color.GOLD);
        lab3.setPrefSize(275,250);
        lab3.setTranslateX(700);
        lab3.setTranslateY(290);
        lab3.setOnAction(event -> {
            SetMazeLocate("src/main/resources/maze3.txt");
            SetScene(primaryStage, "3");
        });

        //labLabel3
        Label labLabel3 = new Label("LABYRINTHE 3");
        labLabel3.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        labLabel3.setTextFill(Color.GOLD);
        labLabel3.setPrefSize(275, 32);
        labLabel3.setTranslateX(700);
        labLabel3.setTranslateY(300);
        labLabel3.setAlignment(Pos.CENTER);

        //lab3Image
        ImageView lab3Image = new ImageView(new Image("maze3_background.png"));
        lab3Image.setFitWidth(275);
        lab3Image.setFitHeight(150);
        lab3Image.setLayoutX(700);
        lab3Image.setLayoutY(350);

        //lab4 button
        Button lab4 = new Button();
        lab4.setBackground(Background.EMPTY);
        //lab4.setStyle("-fx-background-color: #FF0000");
        lab4.setTextFill(Color.GOLD);
        lab4.setPrefSize(400,350);
        lab4.setTranslateX(100);
        lab4.setTranslateY(275);
        lab4.setOnAction(event -> {
            System.out.println("Labyrinthe 4");
        });

        //labLabel4
        Label labLabel4 = new Label("LABYRINTHE 1");
        labLabel4.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        labLabel4.setTextFill(Color.GOLD);
        labLabel4.setPrefSize(400, 32);
        labLabel4.setTranslateX(100);
        labLabel4.setTranslateY(290);
        labLabel4.setAlignment(Pos.CENTER);

        //lab4Image
        ImageView lab4Image = new ImageView(new Image("default_background.png"));
        lab4Image.setFitWidth(400);
        lab4Image.setFitHeight(215);
        lab4Image.setLayoutX(100);
        lab4Image.setLayoutY(350);

        //lab5 button
        Button lab5 = new Button();
        lab5.setBackground(Background.EMPTY);
        //lab5.setStyle("-fx-background-color: #FF0000");
        lab5.setTextFill(Color.GOLD);
        lab5.setPrefSize(400,350);
        lab5.setTranslateX(575);
        lab5.setTranslateY(275);
        lab5.setOnAction(event -> {
            System.out.println("Labyrinthe 5");
        });

        //labLabel5
        Label labLabel5 = new Label("LABYRINTHE 2");
        labLabel5.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        labLabel5.setTextFill(Color.GOLD);
        labLabel5.setPrefSize(400, 32);
        labLabel5.setTranslateX(575);
        labLabel5.setTranslateY(290);
        labLabel5.setAlignment(Pos.CENTER);

        //lab5Image
        ImageView lab5Image = new ImageView(new Image("default_background.png"));
        lab5Image.setFitWidth(400);
        lab5Image.setFitHeight(215);
        lab5Image.setLayoutX(575);
        lab5Image.setLayoutY(350);

        //Help Button
        Button help = new Button();
        help.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #8A2BE2;" +
                        "-fx-border-width: 5px;"
        );
        ImageView imageView = new ImageView(new Image("custom.png"));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        help.setGraphic(imageView);
        help.setTextFill(Color.GOLD);
        help.setPrefSize(75,75);
        help.setTranslateX(65);
        help.setTranslateY(573);
        help.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane makerroot = new Pane();
                MazeCustomView makerView = new MazeCustomView(makerroot, primaryStage);
                Scene makerScene = new Scene(makerroot, 800, 900);
                makerScene.setFill(Paint.valueOf("#000000"));

                primaryStage.setScene(makerScene);
                primaryStage.setTitle("Pacman - Création de Labyrinthe");
                primaryStage.setResizable(false);
            }
        }));

        //labCustom
        Button labCustom = new Button("LABYRINTHE PERSONNALISE");
        labCustom.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #8A2BE2;" +
                        "-fx-border-width: 5px;"
        );
        labCustom.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        labCustom.setTextFill(Color.GOLD);
        labCustom.setPrefSize(875, 75);
        labCustom.setTranslateX(140);
        labCustom.setTranslateY(573);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier de labyrinthe");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        labCustom.setOnAction(actionEvent -> {
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                SetMazeLocate(selectedFile.getAbsolutePath());
                SetScene(primaryStage, selectedFile.toPath().toString());
            }
        });

        // Single Player Button
        this.singlePLayer = new Button("Single PLayer");
        this.singlePLayer.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #8A2BE2;" +
                        "-fx-border-width: 5px;"
        );
        this.singlePLayer.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        this.singlePLayer.setTextFill(Color.GOLD);
        this.singlePLayer.setPrefSize(475, 50);
        this.singlePLayer.setTranslateX(65);
        this.singlePLayer.setTranslateY(200);
        this.singlePLayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (singlePlayerFlag) {
                    selectRoot.getChildren().add(labLabel1);
                    selectRoot.getChildren().add(lab1Image);
                    selectRoot.getChildren().add(lab1);
                    selectRoot.getChildren().add(labLabel2);
                    selectRoot.getChildren().add(lab2Image);
                    selectRoot.getChildren().add(lab2);
                    selectRoot.getChildren().add(labLabel3);
                    selectRoot.getChildren().add(lab3Image);
                    selectRoot.getChildren().add(lab3);
                    selectRoot.getChildren().remove(labLabel4);
                    selectRoot.getChildren().remove(lab4Image);
                    selectRoot.getChildren().remove(lab4);
                    selectRoot.getChildren().remove(labLabel5);
                    selectRoot.getChildren().remove(lab5Image);
                    selectRoot.getChildren().remove(lab5);
                    selectRoot.getChildren().add(help);
                    selectRoot.getChildren().add(labCustom);
                    singlePlayerFlag = false;
                    multiPlayerFlag = true;
                    singlePLayer.setStyle(
                            "-fx-background-color: #000000;" +
                                    "-fx-border-color: #8A2BE2;" +
                                    "-fx-border-width: 5px;"
                    );
                    multiPLayer.setStyle("-fx-background-color: #000000;");
                }
            }
        });

        // Multi Player Button
        this.multiPLayer = new Button("MultiPLayer");
        this.multiPLayer.setStyle(
                "-fx-background-color: #000000;"
        );
        this.multiPLayer.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        this.multiPLayer.setTextFill(Color.GOLD);
        this.multiPLayer.setPrefSize(475, 50);
        this.multiPLayer.setTranslateX(540);
        this.multiPLayer.setTranslateY(200);
        this.multiPLayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (multiPlayerFlag) {
                    selectRoot.getChildren().remove(labLabel1);
                    selectRoot.getChildren().remove(lab1Image);
                    selectRoot.getChildren().remove(lab1);
                    selectRoot.getChildren().remove(labLabel2);
                    selectRoot.getChildren().remove(lab2Image);
                    selectRoot.getChildren().remove(lab2);
                    selectRoot.getChildren().remove(labLabel3);
                    selectRoot.getChildren().remove(lab3Image);
                    selectRoot.getChildren().remove(lab3);
                    selectRoot.getChildren().add(labLabel4);
                    selectRoot.getChildren().add(lab4Image);
                    selectRoot.getChildren().add(lab4);
                    selectRoot.getChildren().add(labLabel5);
                    selectRoot.getChildren().add(lab5Image);
                    selectRoot.getChildren().add(lab5);
                    selectRoot.getChildren().remove(help);
                    selectRoot.getChildren().remove(labCustom);
                    multiPlayerFlag = false;
                    singlePlayerFlag = true;
                    singlePLayer.setStyle("-fx-background-color: #000000");
                    multiPLayer.setStyle(
                            "-fx-background-color: #000000;" +
                                    "-fx-border-color: #8A2BE2;" +
                                    "-fx-border-width: 5px;"
                    );
                }
            }
        });


        this.selectRoot.getChildren().add(titleLabel);
        this.selectRoot.getChildren().add(this.singlePLayer);
        this.selectRoot.getChildren().add(this.multiPLayer);
        this.selectRoot.getChildren().add(rectangle);
        this.selectRoot.getChildren().add(labLabel1);
        this.selectRoot.getChildren().add(lab1Image);
        this.selectRoot.getChildren().add(lab1);
        this.selectRoot.getChildren().add(labLabel2);
        this.selectRoot.getChildren().add(lab2Image);
        this.selectRoot.getChildren().add(lab2);
        this.selectRoot.getChildren().add(labLabel3);
        this.selectRoot.getChildren().add(lab3Image);
        this.selectRoot.getChildren().add(lab3);
        this.selectRoot.getChildren().add(help);
        this.selectRoot.getChildren().add(labCustom);
    }

    public void SetScene(Stage primaryStage, String level){
        Pane root = new Pane(); //organisateur de la fenêtre (stage)
        Scene gameScene = new Scene(root); //scène (comme caneva dans python tkinter)
        PacmanController pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler); //ajoute l'event pression sur la scene
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler); //ajoute l'event relachement sur la scene

        MazeState maze = new MazeState(MazeConfig.makeMazeFINAL(mazeLocate), 50.0, root, level, primaryStage, 0, 3); //données du labyrinthe
        GameView gameView = new GameView(maze, root, 50.0); //apparance graphique du précédent labyrinthe

        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Pacman");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        Sound.SOUND.stopMainMusicLoop();
        gameView.animate(primaryStage); //méthode de la classe "gameView" qui sert à actualiser en continue le jeu
    }

    private void openFileChooser() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(System.getProperty("user.home")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}