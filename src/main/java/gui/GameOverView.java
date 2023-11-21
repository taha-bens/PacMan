package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GameOverView {
    private final Pane gameOverRoot;

    public GameOverView(Pane gameOverRoot, Stage primaryStage, int PlayerScore, int level){
        this.gameOverRoot = gameOverRoot;
        this.gameOverRoot.setStyle("-fx-background-color: #000000");

        if (level>0) {
            Label titleLabel = new Label("ScoreBoard");
            titleLabel.setTextFill(Color.GOLD);
            titleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
            titleLabel.setPrefSize(500, 30);
            titleLabel.setTranslateX(0);
            titleLabel.setTranslateY(10);
            titleLabel.setAlignment(Pos.CENTER);

            GridPane grid = ScoreBoard((new Save("src/main/resources/maze"+level+"_save.txt")).getContenu());

            grid.setTranslateX(0);
            grid.setTranslateY(50);

            TextField textField = new TextField();
            textField.setPromptText("Pseudo");
            textField.setStyle("-fx-alignment: CENTER;");
            textField.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
            textField.setPrefSize(300, 30);
            textField.setTranslateX(100);
            textField.setTranslateY(470);

            Button ok = new Button("OK");
            ok.setStyle(
                    "-fx-background-color: #000000;" +
                            "-fx-border-color: #8A2BE2;" +
                            "-fx-border-width: 5px;"
            );
            ok.setTextFill(Color.GOLD);
            ok.setPrefSize(100, 30);
            ok.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
            ok.setTranslateX(200);
            ok.setTranslateY(530);
            ok.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!textField.getText().trim().isEmpty()) { // Si différent de "" ou " " ou "                     " (que des espaces)
                        ok.setDisable(true);
                        String pseudo = Pattern.compile("[^a-zA-Z0-9]").matcher(textField.getText().toLowerCase()).replaceAll(""); //Remplace tous les caractères spéciaux par ""
                        System.out.println("Pseudo : " + pseudo);
                        textField.clear();

                        // --- inscription dans le fichier de sauvegarde
                        Save contenu = new Save("src/main/resources/maze"+level+"_save.txt");
                        contenu.setUser(pseudo, PlayerScore);
                        contenu.trier();
                        contenu.writeFichier();
                        // ---

                        // --- Actualisation du ScoreBoard
                        gameOverRoot.getChildren().remove(grid);
                        gameOverRoot.getChildren().add(ScoreBoard((new Save("src/main/resources/maze"+level+"_save.txt")).getContenu()));
                        // ---

                        // --- attendre 5s
                        waitTime(5000);
                        // ---

                        // --- changement de Scène
                        menuScene(primaryStage);
                        // ---
                    } else {
                        System.out.println("Pseudo Invalide!");
                    }
                }
            });

            this.gameOverRoot.getChildren().add(titleLabel);
            this.gameOverRoot.getChildren().add(grid);
            this.gameOverRoot.getChildren().add(textField);
            this.gameOverRoot.getChildren().add(ok);
        }

        Label playerScoreLabel = new Label("Votre Score : "+PlayerScore);
        playerScoreLabel.setTextFill(Color.GOLD);
        playerScoreLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        playerScoreLabel.setPrefSize(500, 30);
        playerScoreLabel.setTranslateX(0);
        playerScoreLabel.setTranslateY((level>0)?(425):(300));
        playerScoreLabel.setAlignment(Pos.CENTER);

        this.gameOverRoot.getChildren().add(playerScoreLabel);

        if (level==0) {
            Button ok = new Button("OK");
            ok.setStyle(
                    "-fx-background-color: #000000;" +
                            "-fx-border-color: #8A2BE2;" +
                            "-fx-border-width: 5px;"
            );
            ok.setTextFill(Color.GOLD);
            ok.setPrefSize(100, 30);
            ok.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
            ok.setTranslateX(200);
            ok.setTranslateY(530);
            ok.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // --- changement de Scène
                    menuScene(primaryStage);
                    // ---
                }
            });
            this.gameOverRoot.getChildren().add(ok);
        }

    }

    public GridPane ScoreBoard(String[][] contenu){
        // --- ScoreBoard
        GridPane grid = new GridPane();

        Label pseudoTitleLabel = new Label("Pseudo");
        pseudoTitleLabel.setTextFill(Color.GOLD);
        pseudoTitleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        grid.add(pseudoTitleLabel, 0, 0);
        pseudoTitleLabel.setStyle("-fx-border-color: #FFD700; -fx-border-width: 1px;");
        pseudoTitleLabel.setPrefSize(250, 30);
        pseudoTitleLabel.setAlignment(Pos.CENTER);

        Label scoreTitleLabel = new Label("Score");
        scoreTitleLabel.setTextFill(Color.GOLD);
        scoreTitleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        grid.add(scoreTitleLabel, 1, 0);
        scoreTitleLabel.setStyle("-fx-border-color: #FFD700; -fx-border-width: 1px;");
        scoreTitleLabel.setPrefSize(250, 30);
        scoreTitleLabel.setAlignment(Pos.CENTER);

        contenu = formatScoreBoard(contenu);

        for (int row = 0; row < contenu.length; row++) {
            String pseudoText = contenu[row][0];
            String scoreText = contenu[row][1];

            Label pseudoLabel = new Label(pseudoText);
            pseudoLabel.setTextFill(Color.GOLD);
            pseudoLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
            pseudoLabel.setStyle("-fx-border-color: #FFD700; -fx-border-width: 1px;");
            pseudoLabel.setPrefSize(250, 30);
            pseudoLabel.setAlignment(Pos.CENTER);
            grid.add(pseudoLabel, 0, row+1);

            Label scoreLabel = new Label(scoreText);
            scoreLabel.setTextFill(Color.GOLD);
            scoreLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
            scoreLabel.setStyle("-fx-border-color: #FFD700; -fx-border-width: 1px;");
            scoreLabel.setPrefSize(250, 30);
            scoreLabel.setAlignment(Pos.CENTER);
            grid.add(scoreLabel, 1, row+1);
        }

        return grid;
    }

    public String[][] formatScoreBoard(String[][] contenu) {
        if (contenu.length>10) {
            contenu = Arrays.copyOf(contenu, 10);
        }
        if (contenu.length<10) {
            String[][] newContenu = new String[10][2];

            for (int i=0; i<contenu.length; i++) {
                newContenu[i][0] = contenu[i][0];
                newContenu[i][1] = contenu[i][1];
            }

            for (int i=contenu.length; i<10; i++) {
                newContenu[i][0] = "-";
                newContenu[i][1] = "000000";
            }

            contenu = newContenu;
        }

        return contenu;
    }

    public void waitTime(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Gérez l'exception, par exemple, en affichant un message d'erreur
            e.printStackTrace();
        }
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
