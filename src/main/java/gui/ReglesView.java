package gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class ReglesView {
    private final Pane reglesRoot;
    public Stage primaryStage;
    public Button singlePLayer;
    public Button multiPLayer;
    public boolean singlePlayerFlag = false;
    public boolean multiPlayerFlag = true;

    public ReglesView(Pane reglesRoot, Stage primaryStage) {
        this.reglesRoot = reglesRoot;
        this.primaryStage = new Stage();
        this.reglesRoot.setStyle("-fx-background-color: #000000");

        //titre
        Label titleLabel = new Label("Regles");
        titleLabel.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 30));
        titleLabel.setTextFill(Color.GOLD);
        titleLabel.setPrefSize(800, 48);
        titleLabel.setTranslateX(0);
        titleLabel.setTranslateY(80);
        titleLabel.setAlignment(Pos.CENTER);

        // Rectangle
        Rectangle rectangle = new Rectangle();
        /*rectangle.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        */
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.web("#8A2BE2"));
        rectangle.setStrokeWidth(5);
        rectangle.setWidth(695);
        rectangle.setHeight(630);
        rectangle.setX(52.5);
        rectangle.setY(220);

        //Retour Button
        Button retour = new Button("<-");
        retour.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        retour.setTextFill(Color.GOLD);
        retour.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 27));
        retour.setPrefSize(50, 50);
        retour.setTranslateX(50);
        retour.setTranslateY(80);
        retour.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane menuroot = new Pane();
                MenuView menuView = new MenuView(menuroot, primaryStage);
                Scene menuScene = new Scene(menuroot, 500, 600);

                primaryStage.setScene(menuScene);
                primaryStage.setTitle("Pacman - Menu");
                primaryStage.setResizable(false);
            }
        }));

        //Labels Single Player
        Label label1 = new Label("* BUT DU JEU");
        label1.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label1.setTextFill(Color.GOLD);
        label1.setPrefSize(690, 32);
        label1.setTranslateX(60);
        label1.setTranslateY(230);
        label1.setAlignment(Pos.CENTER_LEFT);
        Label description1 = new Label("Le but du jeu est de finir avec le plus grand score pour Pac-Man. Après avoir mangé toutes les pac-gommes sur le plateau, le plateau se régénère. La partie se termine une fois que Pac-Man a perdu toutes ses vies.");
        description1.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description1.setTextFill(Color.GOLD);
        description1.setPrefSize(680, 70);
        description1.setTranslateX(60);
        description1.setTranslateY(265);
        description1.setAlignment(Pos.CENTER_LEFT);
        description1.setWrapText(true);

        Label label2 = new Label("* PERSONNAGES");
        label2.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label2.setTextFill(Color.GOLD);
        label2.setPrefSize(690, 32);
        label2.setTranslateX(60);
        label2.setTranslateY(345);
        label2.setAlignment(Pos.CENTER_LEFT);
        Label description2 = new Label("Pac-Man est le personnage principal du jeu. Il est contrôlé par le joueur, via les touches UP, DOWN, LEFT, RIGHT, et doit manger le plus de pac-gommes.\nLes fantômes sont les ennemis du jeu. Ils sont contrôlés par l'ordinateur et tentent de manger Pac-Man.");
        description2.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description2.setTextFill(Color.GOLD);
        description2.setPrefSize(680, 88);
        description2.setTranslateX(60);
        description2.setTranslateY(375);
        description2.setAlignment(Pos.CENTER_LEFT);
        description2.setWrapText(true);

        Label label3 = new Label("* PAC-GOMMES & SUPER PAC-GOMMES");
        label3.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label3.setTextFill(Color.GOLD);
        label3.setPrefSize(690, 32);
        label3.setTranslateX(60);
        label3.setTranslateY(475);
        label3.setAlignment(Pos.CENTER_LEFT);
        Label description3 = new Label("Les pac-gommes rapportent des points. Les super pac-gommes rapportent des points et permettent à Pac-Man de manger les fantômes pour En obtenir d’avantage.\nLes fantômes sont vulnérables aux super pac-gommes. \nLorsque Pac-Man mange une super pac-gomme, les fantômes deviennent bleus et peuvent être mangés par Pac-Man.\nLes fantômes re-spawn après avoir été manger.\nLes fantômes redeviennent normaux après un certain temps.");
        description3.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description3.setTextFill(Color.GOLD);
        description3.setPrefSize(680, 142);
        description3.setTranslateX(60);
        description3.setTranslateY(505);
        description3.setAlignment(Pos.CENTER_LEFT);
        description3.setWrapText(true);

        Label label4 = new Label("* BONUS");
        label4.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label4.setTextFill(Color.GOLD);
        label4.setPrefSize(690, 32);
        label4.setTranslateX(60);
        label4.setTranslateY(655);
        label4.setAlignment(Pos.CENTER_LEFT);
        Label description4 = new Label("Pac-Man peut manger des fruits spéciaux qui lui donnent des points supplémentaires.");
        description4.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description4.setTextFill(Color.GOLD);
        description4.setPrefSize(680, 34);
        description4.setTranslateX(60);
        description4.setTranslateY(685);
        description4.setAlignment(Pos.CENTER_LEFT);
        description4.setWrapText(true);

        Label label5 = new Label("* RECAPITULATIF DES POINTS");
        label5.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label5.setTextFill(Color.GOLD);
        label5.setPrefSize(690, 32);
        label5.setTranslateX(60);
        label5.setTranslateY(725);
        label5.setAlignment(Pos.CENTER_LEFT);
        Label description5 = new Label("Pac-gommes: 10 points.\nSuper pac-gommes: 50 points.\nFantômes: 50 points.\nFruits: 50 points.");
        description5.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description5.setTextFill(Color.GOLD);
        description5.setPrefSize(680, 70);
        description5.setTranslateX(60);
        description5.setTranslateY(755);
        description5.setAlignment(Pos.CENTER_LEFT);
        description5.setWrapText(true);

        //Labels MultiPlayer
        Label label6 = new Label("* BUT DU JEU");
        label6.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label6.setTextFill(Color.GOLD);
        label6.setPrefSize(690, 32);
        label6.setTranslateX(60);
        label6.setTranslateY(350);
        label6.setAlignment(Pos.CENTER_LEFT);
        Label description6 = new Label("Le but du jeu est de finir avec le plus grand score.");
        description6.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description6.setTextFill(Color.GOLD);
        description6.setPrefSize(680, 16);
        description6.setTranslateX(60);
        description6.setTranslateY(390);
        description6.setAlignment(Pos.CENTER_LEFT);
        description6.setWrapText(true);

        Label label7 = new Label("* PERSONNAGES");
        label7.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label7.setTextFill(Color.GOLD);
        label7.setPrefSize(690, 32);
        label7.setTranslateX(60);
        label7.setTranslateY(415);
        label7.setAlignment(Pos.CENTER_LEFT);
        Label description7 = new Label("Pac-Man est contrôlé par le joueur 1, via les touches UP, DOWN, LEFT, RIGHT.\nmiss Pac-Man est contrôlé par le joueur 2, via les touches Z, S, Q, D.");
        description7.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description7.setTextFill(Color.GOLD);
        description7.setPrefSize(680, 55);
        description7.setTranslateX(60);
        description7.setTranslateY(450);
        description7.setAlignment(Pos.CENTER_LEFT);
        description7.setWrapText(true);

        Label label8 = new Label("* PAC-GOMMES");
        label8.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label8.setTextFill(Color.GOLD);
        label8.setPrefSize(690, 32);
        label8.setTranslateX(60);
        label8.setTranslateY(510);
        label8.setAlignment(Pos.CENTER_LEFT);
        Label description8 = new Label("Les pac-gommes rapportent des points.");
        description8.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description8.setTextFill(Color.GOLD);
        description8.setPrefSize(680, 16);
        description8.setTranslateX(60);
        description8.setTranslateY(545);
        description8.setAlignment(Pos.CENTER_LEFT);
        description8.setWrapText(true);

        Label label9 = new Label("* BONUS");
        label9.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label9.setTextFill(Color.GOLD);
        label9.setPrefSize(690, 32);
        label9.setTranslateX(60);
        label9.setTranslateY(565);
        label9.setAlignment(Pos.CENTER_LEFT);
        Label description9 = new Label("Pac-Man peut manger des fruits spéciaux qui lui donnent des points supplémentaires.");
        description9.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description9.setTextFill(Color.GOLD);
        description9.setPrefSize(680, 34);
        description9.setTranslateX(60);
        description9.setTranslateY(600);
        description9.setAlignment(Pos.CENTER_LEFT);
        description9.setWrapText(true);

        Label label10 = new Label("* RECAPITULATIF DES POINTS");
        label10.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        label10.setTextFill(Color.GOLD);
        label10.setPrefSize(690, 32);
        label10.setTranslateX(60);
        label10.setTranslateY(640);
        label10.setAlignment(Pos.CENTER_LEFT);
        Label description10 = new Label("Pac-gommes: 10 points.\nSuper pac-gommes: 50 points.\nFantômes: 50 points.\nFruits: 50 points.");
        description10.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 10));
        description10.setTextFill(Color.GOLD);
        description10.setPrefSize(680, 70);
        description10.setTranslateX(60);
        description10.setTranslateY(675);
        description10.setAlignment(Pos.CENTER_LEFT);
        description10.setWrapText(true);

        // Single Player Button
        this.singlePLayer = new Button("Single PLayer");
        this.singlePLayer.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        this.singlePLayer.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        this.singlePLayer.setTextFill(Color.GOLD);
        this.singlePLayer.setPrefSize(350, 50);
        this.singlePLayer.setTranslateX(50);
        this.singlePLayer.setTranslateY(170);
        this.singlePLayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (singlePlayerFlag) {
                    singlePlayerFlag = false;
                    multiPlayerFlag = true;
                    singlePLayer.setStyle(
                            "-fx-background-color: #000000;" +
                            "-fx-border-color: #8A2BE2;" +
                            "-fx-border-width: 5px;"
                    );
                    multiPLayer.setStyle("-fx-background-color: #000000;");

                    reglesRoot.getChildren().remove(label6);
                    reglesRoot.getChildren().remove(description6);
                    reglesRoot.getChildren().remove(label7);
                    reglesRoot.getChildren().remove(description7);
                    reglesRoot.getChildren().remove(label8);
                    reglesRoot.getChildren().remove(description8);
                    reglesRoot.getChildren().remove(label9);
                    reglesRoot.getChildren().remove(description9);
                    reglesRoot.getChildren().remove(label10);
                    reglesRoot.getChildren().remove(description10);

                    reglesRoot.getChildren().add(label1);
                    reglesRoot.getChildren().add(description1);
                    reglesRoot.getChildren().add(label2);
                    reglesRoot.getChildren().add(description2);
                    reglesRoot.getChildren().add(label3);
                    reglesRoot.getChildren().add(description3);
                    reglesRoot.getChildren().add(label4);
                    reglesRoot.getChildren().add(description4);
                    reglesRoot.getChildren().add(label5);
                    reglesRoot.getChildren().add(description5);
                }
            }
        });

        // Multi Player Button
        this.multiPLayer = new Button("MultiPLayer");
        this.multiPLayer.setStyle(
                "-fx-background-color: #000000;"
        );
        this.multiPLayer.setFont(Font.loadFont("file:src/main/resources/Police/ARCADE_I.TTF", 20));
        this.multiPLayer.setTextFill(Color.GOLD);
        this.multiPLayer.setPrefSize(350, 50);
        this.multiPLayer.setTranslateX(400);
        this.multiPLayer.setTranslateY(170);
        this.multiPLayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (multiPlayerFlag) {
                    multiPlayerFlag = false;
                    singlePlayerFlag = true;
                    singlePLayer.setStyle("-fx-background-color: #000000");
                    multiPLayer.setStyle(
                            "-fx-background-color: #000000;" +
                            "-fx-border-color: #8A2BE2;" +
                            "-fx-border-width: 5px;"
                    );

                    reglesRoot.getChildren().remove(label1);
                    reglesRoot.getChildren().remove(description1);
                    reglesRoot.getChildren().remove(label2);
                    reglesRoot.getChildren().remove(description2);
                    reglesRoot.getChildren().remove(label3);
                    reglesRoot.getChildren().remove(description3);
                    reglesRoot.getChildren().remove(label4);
                    reglesRoot.getChildren().remove(description4);
                    reglesRoot.getChildren().remove(label5);
                    reglesRoot.getChildren().remove(description5);

                    reglesRoot.getChildren().add(label6);
                    reglesRoot.getChildren().add(description6);
                    reglesRoot.getChildren().add(label7);
                    reglesRoot.getChildren().add(description7);
                    reglesRoot.getChildren().add(label8);
                    reglesRoot.getChildren().add(description8);
                    reglesRoot.getChildren().add(label9);
                    reglesRoot.getChildren().add(description9);
                    reglesRoot.getChildren().add(label10);
                    reglesRoot.getChildren().add(description10);
                }
            }
        });


        this.reglesRoot.getChildren().add(titleLabel);
        this.reglesRoot.getChildren().add(retour);
        this.reglesRoot.getChildren().add(this.singlePLayer);
        this.reglesRoot.getChildren().add(this.multiPLayer);
        this.reglesRoot.getChildren().add(rectangle);
        this.reglesRoot.getChildren().add(label1);
        this.reglesRoot.getChildren().add(description1);
        this.reglesRoot.getChildren().add(label2);
        this.reglesRoot.getChildren().add(description2);
        this.reglesRoot.getChildren().add(label3);
        this.reglesRoot.getChildren().add(description3);
        this.reglesRoot.getChildren().add(label4);
        this.reglesRoot.getChildren().add(description4);
        this.reglesRoot.getChildren().add(label5);
        this.reglesRoot.getChildren().add(description5);
    }
}