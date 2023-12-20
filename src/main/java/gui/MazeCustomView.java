package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MazeCustomView {
    private final Pane makerRoot;
    public Stage primaryStage;
    public boolean temoin = false;

    public MazeCustomView(Pane makerRoot, Stage primaryStage) {
        this.makerRoot = makerRoot;
        this.primaryStage = new Stage();
        this.makerRoot.setStyle("-fx-background-color: #000000");

        //titre
        Label titleLabel = new Label("CREATION LABYRINTHE");
        titleLabel.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 30));
        titleLabel.setTextFill(Color.GOLD);
        titleLabel.setPrefSize(625, 48);
        titleLabel.setTranslateX(175);
        titleLabel.setTranslateY(80);
        titleLabel.setAlignment(Pos.CENTER_LEFT);

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
        rectangle.setWidth(695);
        rectangle.setHeight(670);
        rectangle.setX(52.5);
        rectangle.setY(180);

        //Retour Button
        Button retour = new Button("<-");
        retour.setStyle(
                "-fx-background-color: #000000;" +
                "-fx-border-color: #8A2BE2;" +
                "-fx-border-width: 5px;"
        );
        retour.setTextFill(Color.GOLD);
        retour.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        retour.setPrefSize(50, 50);
        retour.setTranslateX(50);
        retour.setTranslateY(80);
        retour.setOnAction(event -> {
            Pane selectroot = new Pane();
            SelectView selectView = new SelectView(selectroot,"src/main/resources/maze1.txt", primaryStage);
            Scene selectScene = new Scene(selectroot, 1080, 720);
            selectScene.setFill(Paint.valueOf("#000000"));

            primaryStage.setScene(selectScene);
            primaryStage.setTitle("Pacman - Selection Plateau");
            primaryStage.setResizable(false);
        });

        Label label1 = new Label("* PREREQUIS");
        label1.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        label1.setTextFill(Color.GOLD);
        label1.setPrefSize(690, 32);
        label1.setTranslateX(60);
        label1.setTranslateY(230);
        label1.setAlignment(Pos.CENTER_LEFT);
        Label description1 = new Label("Tous les labyrinthes doivent faire la même taille, 15 de hauteur et 15 de largeur.\nChaque ligne du fichier.txt doit se terminer par un /.\nLes 2 premières lignes et les 2 dernieres lignes doit être rempli pour chaque case de #0 (voir exemple plus bas).\nChaque case du labyrinthe.txt doit contenir 2 choses : 1 variable qui indique la position des murs et une variable pour le contenu de la case.");
        description1.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 10));
        description1.setTextFill(Color.GOLD);
        description1.setPrefSize(680, 142);
        description1.setTranslateX(60);
        description1.setTranslateY(265);
        description1.setAlignment(Pos.CENTER_LEFT);
        description1.setWrapText(true);

        Label label2 = new Label("POSITIONNEMENT DES MURS");
        label2.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        label2.setTextFill(Color.GOLD);
        label2.setPrefSize(700, 32);
        label2.setTranslateX(50);
        label2.setTranslateY(420);
        label2.setAlignment(Pos.CENTER);
        Label description2 = new Label(
                "N  :   mur Nord + mur Ouest\n" +
                "S  :  mur Sud + mur Est\n" +
                "E  :  mur Est + mur Nord\n" +
                "W  :  mur Ouest + mur Sud\n" +
                "=  :  mur NoRd + mur Sud\n" +
                "|  :  mur Est + mur Ouest\n" +
                "^  :  mur Nord\n" +
                "v  :  mur Sud\n" +
                "<  :  mur Ouest\n" +
                ">  :  mur Est");
        description2.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 10));
        description2.setTextFill(Color.GOLD);
        description2.setPrefSize(680, 178);
        description2.setTranslateX(60);
        description2.setTranslateY(455);
        description2.setAlignment(Pos.CENTER);
        description2.setWrapText(true);

        Label label3 = new Label("CONTENUE D’UNE CASE");
        label3.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        label3.setTextFill(Color.GOLD);
        label3.setPrefSize(700, 32);
        label3.setTranslateX(50);
        label3.setTranslateY(645);
        label3.setAlignment(Pos.CENTER);
        Label description3 = new Label(
                "0  : le contenu de la case est vide\n" +
                ".  : le contenu de la case est un pac-gum\n" +
                "*  : le contenu de la case est un super pac-gum");
        description3.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 10));
        description3.setTextFill(Color.GOLD);
        description3.setPrefSize(680, 52);
        description3.setTranslateX(60);
        description3.setTranslateY(680);
        description3.setAlignment(Pos.CENTER);
        description3.setWrapText(true);

        Label label4 = new Label("EXEMPLE:");
        label4.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 20));
        label4.setTextFill(Color.GOLD);
        label4.setPrefSize(690, 32);
        label4.setTranslateX(60);
        label4.setTranslateY(780);
        label4.setAlignment(Pos.CENTER_LEFT);

        ImageView labImage = new ImageView(new Image("MazeCustomExemple.png"));
        labImage.setFitWidth(430);
        labImage.setFitHeight(330);
        labImage.setLayoutX(185);
        labImage.setLayoutY(430);

        Button button = new Button();
        ImageView imageButton = new ImageView(new Image("Information3.png"));
        button.setGraphic(imageButton);
        button.setStyle(
                "-fx-background-color: #000000;"
        );
        button.setTextFill(Color.GOLD);
        button.setFont(Font.loadFont("file:src/main/resources/ARCADE_I.TTF", 27));
        button.setPrefSize(50, 50);
        button.setTranslateX(375);
        button.setTranslateY(770);
        Tooltip tooltip = new Tooltip();
        ImageView imageToolTip = new ImageView(new Image("MazeCustomExemple.png"));
        tooltip.setGraphic(imageToolTip);
        button.setTooltip(tooltip);
        button.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (temoin) {
                    makerRoot.getChildren().remove(labImage);
                    temoin = false;
                } else {
                    makerRoot.getChildren().add(labImage);
                    temoin = true;
                }
            }
        }));

        this.makerRoot.getChildren().add(titleLabel);
        this.makerRoot.getChildren().add(retour);
        this.makerRoot.getChildren().add(rectangle);
        this.makerRoot.getChildren().add(label1);
        this.makerRoot.getChildren().add(description1);
        this.makerRoot.getChildren().add(label2);
        this.makerRoot.getChildren().add(description2);
        this.makerRoot.getChildren().add(label3);
        this.makerRoot.getChildren().add(description3);
        this.makerRoot.getChildren().add(label4);
        this.makerRoot.getChildren().add(button);
    }
}