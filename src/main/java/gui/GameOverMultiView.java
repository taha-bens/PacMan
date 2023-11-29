package gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GameOverMultiView {
    private final Pane gameOverRoot;

    private static AnimationTimer reload;

    public GameOverMultiView(Pane gameOverRoot, Stage primaryStage, int Player1Score, int Player2Score){
        this.gameOverRoot = gameOverRoot;
        this.gameOverRoot.setStyle("-fx-background-color: #000000");

        Label titleLabel = new Label("Gagnant");
        titleLabel.setTextFill(Color.GOLD);
        titleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        titleLabel.setPrefSize(500, 43);
        titleLabel.setTranslateX(0);
        titleLabel.setTranslateY(30);
        titleLabel.setAlignment(Pos.CENTER);

        Label winnerLabel = new Label((Player1Score!=Player2Score)?((Player1Score>Player2Score)?("Joueur 1"):("Joueur 2")):("Egalité"));
        winnerLabel.setTextFill(Color.GOLD);
        winnerLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 30));
        winnerLabel.setPrefSize(500, 50);
        winnerLabel.setTranslateX(0);
        winnerLabel.setTranslateY(100);
        winnerLabel.setAlignment(Pos.CENTER);
        
        Label player1Label = new Label("Joueur 1");
        player1Label.setTextFill(Color.GOLD);
        player1Label.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        player1Label.setPrefSize(250, 32);
        player1Label.setTranslateX(0);
        player1Label.setTranslateY(200);
        player1Label.setAlignment(Pos.CENTER);

        Label player2Label = new Label("Joueur 2");
        player2Label.setTextFill(Color.GOLD);
        player2Label.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        player2Label.setPrefSize(250, 32);
        player2Label.setTranslateX(250);
        player2Label.setTranslateY(200);
        player2Label.setAlignment(Pos.CENTER);
        
        Image imageP1 = new Image("pacman/PacmanR1.png");
        ImageView ImageP1 = new ImageView(imageP1);
        ImageP1.setX(75);
        ImageP1.setY(250);
        ImageP1.setFitWidth(100);
        ImageP1.setFitHeight(100);

        Image imageP2 = new Image("mspacman/MsPacmanL.png");
        ImageView ImageP2 = new ImageView(imageP2);
        ImageP2.setX(325);
        ImageP2.setY(250);
        ImageP2.setFitWidth(100);
        ImageP2.setFitHeight(100);

        Label ScoreLabel1 = new Label("Score :");
        ScoreLabel1.setTextFill(Color.GOLD);
        ScoreLabel1.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 15));
        ScoreLabel1.setPrefSize(250, 24);
        ScoreLabel1.setTranslateX(0);
        ScoreLabel1.setTranslateY(375);
        ScoreLabel1.setAlignment(Pos.CENTER);

        Label ScoreLabel2 = new Label("Score :");
        ScoreLabel2.setTextFill(Color.GOLD);
        ScoreLabel2.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 15));
        ScoreLabel2.setPrefSize(250, 24);
        ScoreLabel2.setTranslateX(250);
        ScoreLabel2.setTranslateY(375);
        ScoreLabel2.setAlignment(Pos.CENTER);

        Label ScorePlayer1Label = new Label(String.valueOf(Player1Score));
        ScorePlayer1Label.setTextFill(Color.GOLD);
        ScorePlayer1Label.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        ScorePlayer1Label.setPrefSize(250, 32);
        ScorePlayer1Label.setTranslateX(0);
        ScorePlayer1Label.setTranslateY(425);
        ScorePlayer1Label.setAlignment(Pos.CENTER);

        Label ScorePlayer2Label = new Label(String.valueOf(Player2Score));
        ScorePlayer2Label.setTextFill(Color.GOLD);
        ScorePlayer2Label.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        ScorePlayer2Label.setPrefSize(250, 32);
        ScorePlayer2Label.setTranslateX(250);
        ScorePlayer2Label.setTranslateY(425);
        ScorePlayer2Label.setAlignment(Pos.CENTER);

        // --- changement de Scène après 10s
        reload = new AnimationTimer(){
            long last = 0;
            long timer = 0;
            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                if (timer > 10000000000L) {
                    menuScene(primaryStage);
                    reload.stop();
                }
                timer += now - last;
                last = now;
            }
        };
        reload.start();
        // ---

        this.gameOverRoot.getChildren().add(titleLabel);
        this.gameOverRoot.getChildren().add(winnerLabel);
        this.gameOverRoot.getChildren().add(player1Label);
        this.gameOverRoot.getChildren().add(player2Label);
        this.gameOverRoot.getChildren().add(ImageP1);
        this.gameOverRoot.getChildren().add(ImageP2);
        this.gameOverRoot.getChildren().add(ScoreLabel1);
        this.gameOverRoot.getChildren().add(ScoreLabel2);
        this.gameOverRoot.getChildren().add(ScorePlayer1Label);
        this.gameOverRoot.getChildren().add(ScorePlayer2Label);

    }

    public void menuScene(Stage primaryStage) {
        Pane menuroot = new Pane();
        MenuView menuView = new MenuView(menuroot, primaryStage);
        Scene menuScene = new Scene(menuroot, 500, 600);

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Pacman - Menu");
        primaryStage.setResizable(false);
    }
}
