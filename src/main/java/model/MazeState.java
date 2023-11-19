package model;

import Pacman.src.main.java.model.Sound;
import config.Cell;
import config.MazeConfig;
import config.MazeLoader;
import geometry.IntCoordinates;
import geometry.RealCoordinates;

import java.lang.module.ModuleDescriptor;
import java.util.*;

import static model.Ghost.*;

import javafx.scene.image.Image;
import model.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.sound.sampled.*;

public final class MazeState {
    ///
    private Timer timer;
    ///
    private final MazeConfig config;
    private final int height;
    private final int width;

    private final boolean[][] gridState;

    private final List<Critter> critters;
    private int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;

    private Pane root;
    private ImageView l1, l2, l3;

    public MazeState(MazeConfig config, double scale, Pane root) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );
        this.root = root;
        resetCritters();
        Image liveImage = new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true);
        l1 = new ImageView(new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true));
        l2 = new ImageView(new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true));
        l3 = new ImageView(new Image("pacman/PacmanL1.png",scale*0.7,scale*0.7,true,true));
        ImageView[] liveImageTab = {l1,l2,l3};
        for (int i = 1; i <= 3; i++){
            this.root.getChildren().add(liveImageTab[i-1]);
            liveImageTab[i-1].setX(scale*(i-1)+5);
            liveImageTab[i-1].setY(710);
        }
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void startTimerEnergized(){
        if (timer != null){
            timer.cancel();
        }
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                PacMan.INSTANCE.setEnergized(false);
            }
        };
        timer.schedule(timerTask, 8000);
    }
    public void resetTimerEnergized(){
        if (timer != null){
            timer.cancel();
        }
        startTimerEnergized();
    }

    public void update(long deltaTns) {
        // FIXME: too many things in this method. Maybe some responsibilities can be delegated to other methods or classes?
        for(Critter critter: critters) {
            System.out.print((critter instanceof PacMan) ? ""
                    : // PacmanController.changePacman('event')?
                    switch ((Ghost) critter) {
                        case BLINKY -> String.valueOf(critter.getPos()) + "\n";
                        default -> "";
                    });
            RealCoordinates curPos = critter.getPos();
            RealCoordinates nextPos = critter.nextPos(deltaTns);
            Set<IntCoordinates> curNeighbours = curPos.intNeighbours();
            Set<IntCoordinates> nextNeighbours = nextPos.intNeighbours();

            if (!curNeighbours.containsAll(nextNeighbours)) { // the critter would overlap new cells. Do we allow it?
                switch (critter.getDirection()) {
                    case NORTH -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).northWall()) {
                            nextPos = curPos.floorY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case EAST -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).eastWall()) {
                            nextPos = curPos.ceilX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case SOUTH -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).southWall()) {
                            nextPos = curPos.ceilY();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                    case WEST -> {
                        for (IntCoordinates n: curNeighbours) if (config.getCell(n).westWall()) {
                            nextPos = curPos.floorX();
                            critter.setDirection(Direction.NONE);
                            break;
                        }
                    }
                }

            }

            critter.setPos(nextPos.warp(width, height));
        }
        // FIXME Pac-Man rules should somehow be in Pacman class
        IntCoordinates pacPos = PacMan.INSTANCE.getPos().round();
        if (!gridState[pacPos.y()][pacPos.x()]){
            gridState[pacPos.y()][pacPos.x()] = true;
            if (this.getConfig().getCell(pacPos).initialContent() == Cell.Content.ENERGIZER){
                PacMan.INSTANCE.setEnergized(true);
                addScore(50);
                resetTimerEnergized();
            } else {
                addScore(10);
            }
        }

        for (Critter critter : critters) {
            if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
                if (PacMan.INSTANCE.isEnergized()) { // PacMan energisé tue un fantome
                    addScore(200);
                    resetCritter(critter);
                } else {
                    playerLost(); // PacMan touche un fantome
                    return;
                }
            }
        }
    }

    private void addScore(int increment) {
        score += increment;
        displayScore();
    }

    private void displayScore() {
        // FIXME: this should be displayed in the JavaFX view, not in the console
        System.out.println("Score: " + score);
    }

    private void playerLost() {
        // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
        lives--;
        if (lives == 2) this.root.getChildren().remove(l3);
        else if (lives == 1) this.root.getChildren().remove(l2);
        else if (lives == 0) {
            this.root.getChildren().remove(l1);
            Sound.SOUND.stopStartMusic();
            System.out.println("Game over!");
            System.exit(0);
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (Critter critter: critters) resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    public int getScore(){return this.score;}

    public void updatePacman(){
        if (PacMan.INSTANCE.getDirection() == Direction.EAST){
            if (Math.ceil( PacMan.INSTANCE.getPos().getX() ) - PacMan.INSTANCE.getPos().getX() < 0.07  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH || PacMan.INSTANCE.getNextDirection() == Direction.SOUTH){
                    IntCoordinates co = new IntCoordinates((int)Math.ceil (PacMan.INSTANCE.getPos().getX()) , (int)PacMan.INSTANCE.getPos().getY()  );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH && !(config.getCell(co).northWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y() - 0.001));
                        PacMan.INSTANCE.setDirection(Direction.NORTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.SOUTH && !(config.getCell(co).southWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()+ 0.001));
                        PacMan.INSTANCE.setDirection(Direction.SOUTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.WEST){
            if ( PacMan.INSTANCE.getPos().getX() - Math.floor( PacMan.INSTANCE.getPos().getX()  )  < 0.07  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH || PacMan.INSTANCE.getNextDirection() == Direction.SOUTH){
                    IntCoordinates co = new IntCoordinates((int)Math.floor (PacMan.INSTANCE.getPos().getX()) , (int)PacMan.INSTANCE.getPos().getY()  );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.NORTH && !(config.getCell(co).northWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()- 0.001));
                        PacMan.INSTANCE.setDirection(Direction.NORTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.SOUTH && !(config.getCell(co).southWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x()  , co.y()+ 0.001));
                        PacMan.INSTANCE.setDirection(Direction.SOUTH);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.SOUTH){
            if (Math.ceil( PacMan.INSTANCE.getPos().getY() ) - PacMan.INSTANCE.getPos().getY() < 0.07  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.EAST || PacMan.INSTANCE.getNextDirection() == Direction.WEST){
                    IntCoordinates co = new IntCoordinates((int)PacMan.INSTANCE.getPos().getX() , (int)Math.ceil( PacMan.INSTANCE.getPos().getY() ) );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.EAST && !(config.getCell(co).eastWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() + 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.EAST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.WEST && !(config.getCell(co).westWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() - 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.WEST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.NORTH){
            if ( PacMan.INSTANCE.getPos().getY() - Math.floor( PacMan.INSTANCE.getPos().getY() )  < 0.07  && PacMan.INSTANCE.getNextDirection() != Direction.NONE){
                if (PacMan.INSTANCE.getNextDirection() == Direction.EAST || PacMan.INSTANCE.getNextDirection() == Direction.WEST){
                    IntCoordinates co = new IntCoordinates((int)PacMan.INSTANCE.getPos().getX() , (int)Math.floor( PacMan.INSTANCE.getPos().getY() ) );
                    if (PacMan.INSTANCE.getNextDirection() == Direction.WEST && !(config.getCell(co).westWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() - 0.001  , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.WEST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                    if (PacMan.INSTANCE.getNextDirection() == Direction.EAST && !(config.getCell(co).eastWall())){
                        PacMan.INSTANCE.setPos(new RealCoordinates(co.x() + 0.001 , co.y()));
                        PacMan.INSTANCE.setDirection(Direction.EAST);
                        PacMan.INSTANCE.setNextDirection(Direction.NONE);
                    }
                }
            }

        }
    }
    public void updateGhost(){
        Direction dir = Direction.NONE;
        for (Ghost valeurs : Ghost.values()){
            // Permet d'initialiser le premier mouvement de chaque fantome dans la direction qu'il faut
            if (BLINKY.getPos().round().estEgal(new IntCoordinates(7,6))){
                BLINKY.setDirection(Direction.WEST);
            }
            if (PINKY.getPos().round().estEgal(new IntCoordinates(7,7))){
                PINKY.setDirection(Direction.NORTH);
            }
            if (CLYDE.getPos().round().estEgal(new IntCoordinates(8,7))){
                CLYDE.setDirection(Direction.WEST);
            }
            if (INKY.getPos().round().estEgal(new IntCoordinates(6,7))){
                INKY.setDirection(Direction.EAST);
            }

            if (valeurs.getDirection() == Direction.NORTH){  //Si le fantome se dirige vers le nord
                if ( valeurs.getPos().getY() - Math.floor( valeurs.getPos().getY() )  < 0.1 ){ // Si le fantôme est dans le bout de sa case
                    IntCoordinates co = new IntCoordinates((int)valeurs.getPos().getX() , (int)Math.floor( valeurs.getPos().getY() ) ); // On recupere les coordonnées de la case ou il va arriver
                    if ( !config.getCell(co).northWall() && !config.getCell(co).westWall() && !config.getCell(co).eastWall() ) { // Si on peut aller tout droit, a droite ou a gauche
                        IntCoordinates objectif = objectif(valeurs); //On recupere les coordonnee que le fantome actuel a pour objectif
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.upNeighbour(), co.leftNeighbour() ); // Dans "vers" se trouve les coordonnées de la case adjacentes a "co" la plus proche de l'objectif
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;} //Si vers est la case a gauche alors on recupere WEST
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}  //Si vers est la case en haut alors on recupere NORTH
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}  //Si vers est la case a droite alors on recupere EAST
                        valeurs.setPos(teleporte (co, dir)); //teleporte trouve avec une coordonnees et un direction l'endroit le plus proche de "co" et place l'entité dans son axe nécéssaire
                        valeurs.setDirection(dir); //On change la direction de l'entité
                    }
                    else if ( !config.getCell(co).northWall() && !config.getCell(co).westWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.upNeighbour(), co.leftNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).northWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.upNeighbour() ) ;
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).westWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.leftNeighbour() ) ;
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).eastWall() ) {
                        valeurs.setPos(teleporte (co, Direction.EAST));
                        valeurs.setDirection(Direction.EAST);
                    }
                    else if ( !config.getCell(co).westWall() ) {
                        valeurs.setPos(teleporte (co, Direction.WEST));
                        valeurs.setDirection(Direction.WEST);
                    }
                }
            }
            else if (valeurs.getDirection() == Direction.SOUTH){
                if ( Math.ceil( valeurs.getPos().getY() ) - valeurs.getPos().getY() < 0.08 ){
                    IntCoordinates co = new IntCoordinates((int)valeurs.getPos().getX() , (int)Math.ceil( valeurs.getPos().getY() ) );
                    if ( !config.getCell(co).southWall() && !config.getCell(co).westWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.downNeighbour(), co.leftNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).southWall() && !config.getCell(co).westWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.downNeighbour(), co.leftNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).southWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.downNeighbour() );
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).westWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.leftNeighbour() );
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).westWall() ) {
                        valeurs.setPos(teleporte (co, Direction.WEST));
                        valeurs.setDirection(Direction.WEST);
                    }
                    else if (!config.getCell(co).eastWall() ) {
                        valeurs.setPos(teleporte (co, Direction.EAST));
                        valeurs.setDirection(Direction.EAST);
                    }

                }
            }
            else if (valeurs.getDirection() == Direction.EAST){
                if ( Math.ceil( valeurs.getPos().getX() ) - valeurs.getPos().getX() < 0.08){
                    IntCoordinates co = new IntCoordinates((int)Math.ceil (valeurs.getPos().getX()) , (int)valeurs.getPos().getY()  );
                    if ( !config.getCell(co).southWall() && !config.getCell(co).eastWall() && !config.getCell(co).northWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.downNeighbour(), co.leftNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).southWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.downNeighbour(), co.rightNeighbour() );
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).northWall() && !config.getCell(co).eastWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.upNeighbour() );
                        if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).northWall() && !config.getCell(co).southWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.upNeighbour() , co.downNeighbour() );
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if (!config.getCell(co).southWall() ) {
                        valeurs.setPos(teleporte (co, Direction.SOUTH));
                        valeurs.setDirection(Direction.SOUTH);
                    }
                    else if (!config.getCell(co).northWall() ) {
                        valeurs.setPos(teleporte (co, Direction.NORTH));
                        valeurs.setDirection(Direction.NORTH);
                    }
                }
            }

            if (valeurs.getDirection() == Direction.WEST){
                if ( valeurs.getPos().getX() - Math.floor( valeurs.getPos().getX()  )  < 0.08){
                    IntCoordinates co = new IntCoordinates((int)Math.floor (valeurs.getPos().getX()) , (int)valeurs.getPos().getY()  );
                    if ( !config.getCell(co).southWall() && !config.getCell(co).westWall() && !config.getCell(co).northWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif , co.upNeighbour() , co.downNeighbour(), co.leftNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).southWall() && !config.getCell(co).westWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif, co.downNeighbour(), co.leftNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).northWall() && !config.getCell(co).westWall() ) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif, co.leftNeighbour() , co.upNeighbour() );
                        if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }

                    else if ( !config.getCell(co).northWall() && !config.getCell(co).southWall()) {
                        IntCoordinates objectif = objectif(valeurs);
                        IntCoordinates vers = Manhattan(objectif, co.upNeighbour() , co.downNeighbour() );
                        if (vers.estEgal(co.downNeighbour())){ dir = Direction.SOUTH;}
                        if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
                        valeurs.setPos(teleporte (co, dir));
                        valeurs.setDirection(dir);
                    }
                    else if ( !config.getCell(co).southWall()) {
                        valeurs.setPos(teleporte (co, Direction.SOUTH));
                        valeurs.setDirection(Direction.SOUTH);
                    }
                    else if ( !config.getCell(co).northWall()) {
                        valeurs.setPos(teleporte (co, Direction.NORTH));
                        valeurs.setDirection(Direction.NORTH);
                    }
                }
            }
        }
    }

    //ECRIRE MANHATTAN AVEC UN FOR (ARG1...ARGn) DONC AVEC UNE SEUL FONCTION MANHATTAN
    public IntCoordinates Manhattan(IntCoordinates objectif , IntCoordinates prop1 , IntCoordinates prop2){
        // Cette fonction retourne parmis les 2 propositions laquel est la plus proche de l'objectif
        int p2 = Math.abs(objectif.x() - prop2.x()) + Math.abs(objectif.y() - prop2.y()) ;
        int p1 = Math.abs(objectif.x() - prop1.x()) + Math.abs(objectif.y() - prop1.y()) ;
        if (p2 < p1){
            return prop2;
        }
        else {
            return prop1;
        }

    }
    public IntCoordinates Manhattan(IntCoordinates objectif , IntCoordinates prop1 , IntCoordinates prop2, IntCoordinates prop3){
        IntCoordinates res1 = Manhattan(objectif, prop1,prop2);
        return Manhattan(objectif, res1 , prop3);
    }

    public RealCoordinates teleporte (IntCoordinates co , Direction dir){
        //Cette fonction retourne les coordonnées juste à côté de "co" pour que l'entité puisse s'y deplacer sans probleme
        if (dir == Direction.NORTH){
            return new RealCoordinates(co.x() , co.y() - 0.01);
        }
        if (dir == Direction.SOUTH){
            return new RealCoordinates(co.x(), co.y() + 0.01);
        }
        if (dir == Direction.EAST){
            return new RealCoordinates(co.x() + 0.01, co.y() );
        }
        else {
            return new RealCoordinates(co.x() - 0.01, co.y() );
        }
    }
    public IntCoordinates devantPacman (){
        //Cette fonction retourne les coordonnées de la case qui se trouve à 2 case devant pacman
        if (PacMan.INSTANCE.getDirection() == Direction.NORTH) {
            if (PacMan.INSTANCE.getPos().round().y() - 2 < 2) {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), 2);
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), PacMan.INSTANCE.getPos().round().y() - 2);
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.SOUTH) {
            if (PacMan.INSTANCE.getPos().round().y() + 2 > 12) {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), 12);
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x(), PacMan.INSTANCE.getPos().round().y() + 2);
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.WEST) {
            if (PacMan.INSTANCE.getPos().round().x() - 2 < 0) {
                return new IntCoordinates(0, PacMan.INSTANCE.getPos().round().y());
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x() - 2, PacMan.INSTANCE.getPos().round().y());
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.EAST) {
            if (PacMan.INSTANCE.getPos().round().x() + 2 > 14) {
                return new IntCoordinates(14, PacMan.INSTANCE.getPos().round().y());
            } else {
                return new IntCoordinates(PacMan.INSTANCE.getPos().round().x() + 2, PacMan.INSTANCE.getPos().round().y());
            }
        } else {
            return PacMan.INSTANCE.getPos().round();
        }
    }


    public IntCoordinates objectif (Ghost valeur) {
        //Retourne les coordonnées qui est l'objectif pour chaque fantome
        //Correspond à l'IA des fantomes
        if (valeur == BLINKY) {
            return PacMan.INSTANCE.getPos().round();
        }
        if (valeur == PINKY) {
            return devantPacman();
        }
        if (valeur == CLYDE) {
            if (CLYDE.getPos().round().minus(PacMan.INSTANCE.getPos().round()) > 4) {
                return PacMan.INSTANCE.getPos().round();
            } else {
                return new IntCoordinates(0, 13);
            }
        }
        if (valeur == INKY) {
            IntCoordinates devantPac = devantPacman();
            int vx = devantPac.x() - BLINKY.getPos().round().x()  ;
            int vy = devantPac.y() - BLINKY.getPos().round().y();
            return new IntCoordinates(PacMan.INSTANCE.getPos().round().x() + vx , PacMan.INSTANCE.getPos().round().y() + vy);
        }
        return new IntCoordinates(0,12);

    }

}
