package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuView {

    private final Pane menuRoot;

    public MenuView(Pane menuRoot, Stage primaryStage) {
        this.menuRoot = menuRoot;

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.web("#8A2BE2"));
        rectangle.setStroke(Color.web("#8A2BE2"));
        rectangle.setStrokeWidth(5);
        rectangle.setWidth(500);
        rectangle.setHeight(112.5);
        rectangle.setX(0);
        rectangle.setY(60);

        ImageView image = new ImageView(new Image("projet_icone1.png"));
        image.setFitWidth(500);
        image.setFitHeight(112.5);
        image.setTranslateX(0);
        image.setTranslateY(60);

        Button jouer = new Button("Jouer");
        jouer.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        jouer.setTextFill(Color.GOLD);
        jouer.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 27));
        jouer.setPrefSize(240, 10);
        jouer.setTranslateX(130);
        jouer.setTranslateY(250);
        jouer.setOnAction(event -> {
            // --- Création de la fenêtre de selection du plateau de jeu et changement de scène
            Pane selectroot = new Pane();
            SelectView selectView = new SelectView(selectroot,"src/main/resources/maze1.txt", primaryStage);
            Scene selectScene = new Scene(selectroot, 1080, 720);
            selectScene.setFill(Paint.valueOf("#000000"));
            // ---

            primaryStage.setScene(selectScene);
            primaryStage.setTitle("Pacman - Selecteur de Labyrinthe");
            primaryStage.setResizable(false);
        });

        Button regles = new Button("Regles");
        regles.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #8A2BE2;" +
                        "-fx-border-width: 5px;"
        );
        regles.setTextFill(Color.GOLD);
        regles.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 27));
        regles.setPrefSize(240, 10);
        regles.setTranslateX(130);
        regles.setTranslateY(350);
        regles.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane reglesroot = new Pane();
                ReglesView reglesView = new ReglesView(reglesroot, primaryStage);
                Scene reglesScene = new Scene(reglesroot, 800, 900);
                reglesScene.setFill(Paint.valueOf("#000000"));

                primaryStage.setScene(reglesScene);
                primaryStage.setTitle("Pacman - Règles du jeu");
                primaryStage.setResizable(false);
            }
        }));


        Button quit = new Button("Quitter");
        quit.setStyle("-fx-background-color: #000000;" + "-fx-border-color: #8A2BE2;" + "-fx-border-width: 5px;"
        );
        quit.setTextFill(Color.GOLD);
        quit.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 27));
        quit.setPrefSize(240, 10);
        quit.setTranslateX(130);
        quit.setTranslateY(450);
        quit.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        this.menuRoot.setBackground(
                new Background(
                        new BackgroundImage(
                                new Image("file:src/main/resources/maze1_background.png"),
                                BackgroundRepeat.NO_REPEAT, // Répétition de l'image
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(
                                        BackgroundSize.AUTO, // Largeur
                                        BackgroundSize.AUTO, // Hauteur
                                        false, // Largeur automatique
                                        false, // Hauteur automatique
                                        false, // Recadrer
                                        false  // Redimensionner
                                )
                        )
                )
        );

        this.menuRoot.getChildren().add(rectangle);
        this.menuRoot.getChildren().add(image);
        this.menuRoot.getChildren().add(jouer);
        this.menuRoot.getChildren().add(regles);
        this.menuRoot.getChildren().add(quit);
    }
}