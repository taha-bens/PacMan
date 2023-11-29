package gui;

import javafx.animation.AnimationTimer;
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
import model.Mode;
import model.Mouvement;
import model.PacMan;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GameOverView {
    private final Pane gameOverRoot;

    private static AnimationTimer reload;

    public GameOverView(Pane gameOverRoot, Stage primaryStage, int PlayerScore, String level){
        this.gameOverRoot = gameOverRoot;
        this.gameOverRoot.setStyle("-fx-background-color: #000000");

        int temoin;
        switch(level){
            case "0" : temoin = 0; level = "src/main/resources/maze"+level+"_save.txt"; break;
            case "1" : temoin = 1; level = "src/main/resources/maze"+level+"_save.txt"; break;
            case "2" : temoin = 2; level = "src/main/resources/maze"+level+"_save.txt"; break;
            default : temoin = -1; level = level.replace(".txt", "_save.txt");
        }

        Label titleLabel = new Label("ScoreBoard");
        titleLabel.setTextFill(Color.GOLD);
        titleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        titleLabel.setPrefSize(500, 30);
        titleLabel.setTranslateX(0);
        titleLabel.setTranslateY(10);
        titleLabel.setAlignment(Pos.CENTER);

        verifFile(level);
        GridPane grid = ScoreBoard((new Save(level)).getContenu());

        TextField textField = new TextField();
        textField.setPromptText("Pseudo");
        textField.setStyle("-fx-alignment: CENTER;");
        textField.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        textField.setPrefSize(300, 30);
        textField.setTranslateX(100);
        textField.setTranslateY(470);

        Label playerScoreLabel = new Label("Votre Score : "+PlayerScore);
        playerScoreLabel.setTextFill(Color.GOLD);
        playerScoreLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        playerScoreLabel.setPrefSize(500, 30);
        playerScoreLabel.setTranslateX(0);
        playerScoreLabel.setTranslateY(425);
        playerScoreLabel.setAlignment(Pos.CENTER);

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
        String finalLevel = level;

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!textField.getText().trim().isEmpty()) { // Si différent de "" ou " " ou "                     " (que des espaces)
                    ok.setDisable(true);
                    String pseudo = Pattern.compile("[^a-zA-Z0-9]").matcher(textField.getText().toLowerCase()).replaceAll(""); //Remplace tous les caractères spéciaux par ""
                    System.out.println("Pseudo : " + pseudo);
                    textField.clear();

                    // --- inscription dans le fichier de sauvegarde
                    Save contenu = new Save(finalLevel);
                    contenu.setUser(pseudo, PlayerScore);
                    contenu.trier();
                    contenu.writeFichier();
                    // ---

                    // --- Actualisation du ScoreBoard
                    gameOverRoot.getChildren().remove(grid);
                    GridPane grid = ScoreBoard((new Save(finalLevel)).getContenu());
                    gameOverRoot.getChildren().add(grid);
                    // ---

                    // --- changement de Scène après 5s
                    reload = new AnimationTimer(){
                        long last = 0;
                        long timer = 0;
                        @Override
                        public void handle(long now) {
                            if (last == 0) { // ignore the first tick, just compute the first deltaT
                                last = now;
                                return;
                            }
                            if (timer > 5000000000L) {
                                menuScene(primaryStage);
                                reload.stop();
                            }
                            timer += now - last;
                            last = now;
                        }
                    };
                    reload.start();
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
        this.gameOverRoot.getChildren().add(playerScoreLabel);

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
        grid.setTranslateX(0);
        grid.setTranslateY(50);
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

    public void menuScene(Stage primaryStage) {
        Pane menuroot = new Pane();
        MenuView menuView = new MenuView(menuroot, primaryStage);
        Scene menuScene = new Scene(menuroot, 500, 600);

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Pacman - Menu");
        primaryStage.setResizable(false);
    }

    public static void verifFile(String filePath) {
        System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier : " + filePath);
                e.printStackTrace();
            }
        }
    }
}
