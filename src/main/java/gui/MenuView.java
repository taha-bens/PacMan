package gui;

import config.MazeConfig;
import geometry.IntCoordinates;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.*;
import gui.PacmanController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MenuView {
    private final Pane menuRoot;

    public MenuView(Pane menuRoot, Stage primaryStage){
        this.menuRoot = menuRoot;

        ImageView image = new ImageView(new Image("projet_icone.png", 500, 0, true, false));
        image.setTranslateX(0);
        image.setTranslateY(60);

        Button jouer = new Button("Jouer");
        jouer.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        jouer.setTextFill(Color.GOLD);
        jouer.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        jouer.setPrefSize(240, 10);
        jouer.setTranslateX(130);
        jouer.setTranslateY(300);
        jouer.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Jouer");

                // --- Création de la fenêtre de selection du plateau de jeu et changement de scène
                Pane selectroot = new Pane();
                SelectView selectView = new SelectView(selectroot,"src/main/resources/maze1.txt", primaryStage);
                Scene selectScene = new Scene(selectroot, 1080, 720);
                selectScene.setFill(Paint.valueOf("#000000"));
                // ---

                //primaryStage.setScene();
                primaryStage.setScene(selectScene);
                primaryStage.setTitle("Pacman - Selection Plateau");
                primaryStage.setResizable(false);
            }
        }));

        Button quit = new Button("Quitter");
        quit.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        quit.setTextFill(Color.GOLD);
        quit.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        quit.setPrefSize(240, 10);
        quit.setTranslateX(130);
        quit.setTranslateY(400);
        quit.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        }));

        this.menuRoot.setBackground(
                new Background(
                        new BackgroundImage(
                                new Image("file:src/main/resources/menu_background.png"),
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

        this.menuRoot.getChildren().add(image);
        this.menuRoot.getChildren().add(jouer);
        this.menuRoot.getChildren().add(quit);
    }
}
