package model;

import geometry.IntCoordinates;

import java.util.Random;

import static model.Ghost.*;

public class Mode {
    public static void mode (int timer, MazeState maze){
        if (!PacMan.INSTANCE.isEnergized()) {
            if (Mode.isScatter(timer)) {
                Mode.scatterMode(maze);
            } else {
                Mode.chaseMode(maze);
            }
        } else {
            Mode.frightenedMode(maze);
        }

    }
    public static void Backward(){
        BLINKY.setDirection(getDirectionBackward(BLINKY.getDirection()));
        PINKY.setDirection(getDirectionBackward(PINKY.getDirection()));
        INKY.setDirection(getDirectionBackward(INKY.getDirection()));
        CLYDE.setDirection(getDirectionBackward(CLYDE.getDirection()));
    }
    public static Direction getDirectionBackward(Direction dir){
        switch (dir){
            case NORTH : return Direction.SOUTH;
            case SOUTH : return Direction.NORTH;
            case EAST:  return Direction.WEST;
            case WEST:  return Direction.EAST;
        }
        return Direction.NONE;
    }
    public static void scatterMode(MazeState maze){
        for (Ghost valeurs : Ghost.values()){
            if (BLINKY.getPos().round().estEgal(new IntCoordinates(7,6))){
                BLINKY.setDirection(Direction.WEST);
            }
            // Permet d'initialiser le premier mouvement de BLINKY
            if (BLINKY.getPos().round().estEgal(new IntCoordinates(7,6))){
                BLINKY.setDirection(Direction.WEST);
            }

            if (valeurs.getDirection() == Direction.NORTH){
                if ( valeurs.getPos().getY() - Math.floor( valeurs.getPos().getY() )  < 0.1 ){
                    Mouvement.north(maze ,valeurs , "scatter");
                }
            }
            else if (valeurs.getDirection() == Direction.SOUTH){
                if ( Math.ceil( valeurs.getPos().getY() ) - valeurs.getPos().getY() < 0.1 ){
                    Mouvement.south(maze ,valeurs , "scatter");
                }
            }
            else if (valeurs.getDirection() == Direction.EAST){
                if ( Math.ceil( valeurs.getPos().getX() ) - valeurs.getPos().getX() < 0.1){
                    Mouvement.east(maze , valeurs , "scatter");
                }
            }

            if (valeurs.getDirection() == Direction.WEST){
                if ( valeurs.getPos().getX() - Math.floor( valeurs.getPos().getX()  )  < 0.1){
                    Mouvement.west(maze ,valeurs, "scatter");
                }
            }
        }

    }
    public static void frightenedMode(MazeState maze){
        for (Ghost valeurs : Ghost.values()){
            if (BLINKY.getPos().round().estEgal(new IntCoordinates(7,6))){
                BLINKY.setDirection(Direction.WEST);
            }
            if (valeurs.getDirection() == Direction.NORTH){
                if ( valeurs.getPos().getY() - Math.floor( valeurs.getPos().getY() )  < 0.1 ){
                    Mouvement.north(maze , valeurs , "frightened ");
                }
            }
            else if (valeurs.getDirection() == Direction.SOUTH){
                if ( Math.ceil( valeurs.getPos().getY() ) - valeurs.getPos().getY() < 0.1 ){
                    Mouvement.south(maze , valeurs , "frightened ");
                }
            }
            else if (valeurs.getDirection() == Direction.EAST){
                if ( Math.ceil( valeurs.getPos().getX() ) - valeurs.getPos().getX() < 0.1){
                    Mouvement.east(maze ,valeurs , "frightened ");
                }
            }

            if (valeurs.getDirection() == Direction.WEST){
                if ( valeurs.getPos().getX() - Math.floor( valeurs.getPos().getX()  )  < 0.1){
                    Mouvement.west(maze , valeurs, "frightened ");
                }
            }
        }
    }
    public static void chaseMode(MazeState maze){
        for (Ghost valeurs : Ghost.values()){
            if (valeurs.getDirection() == Direction.NORTH){
                if ( valeurs.getPos().getY() - Math.floor( valeurs.getPos().getY() )  < 0.1 ){
                    Mouvement.north(maze , valeurs, "chase");
                }
            }
            else if (valeurs.getDirection() == Direction.SOUTH){
                if ( Math.ceil( valeurs.getPos().getY() ) - valeurs.getPos().getY() < 0.1 ){
                    Mouvement.south(maze ,valeurs, "chase");
                }
            }
            else if (valeurs.getDirection() == Direction.EAST){
                if ( Math.ceil( valeurs.getPos().getX() ) - valeurs.getPos().getX() < 0.1){
                    Mouvement.east(maze,valeurs, "chase");
                }
            }

            if (valeurs.getDirection() == Direction.WEST){
                if ( valeurs.getPos().getX() - Math.floor( valeurs.getPos().getX()  )  < 0.1){
                    Mouvement.west(maze , valeurs, "chase");
                }
            }
        }
    }
    public static IntCoordinates chaseObjectif (Ghost valeur) {
        //Retourne les coordonnées qui est l'objectif pour chaque fantome
        //Correspond à l'IA des fantomes
        if (valeur == BLINKY) {
            return PacMan.INSTANCE.getPos().round();
        }
        if (valeur == PINKY) {
            return PacMan.INSTANCE.devantPacman();
        }
        if (valeur == CLYDE) {
            if (CLYDE.getPos().round().minus(PacMan.INSTANCE.getPos().round()) > 4) {
                return PacMan.INSTANCE.getPos().round();
            } else {
                return new IntCoordinates(0, 13);
            }
        }
        if (valeur == INKY) {
            IntCoordinates devantPac = PacMan.INSTANCE.devantPacman();
            int vx = devantPac.x() - BLINKY.getPos().round().x()  ;
            int vy = devantPac.y() - BLINKY.getPos().round().y();
            return new IntCoordinates(PacMan.INSTANCE.getPos().round().x() + vx , PacMan.INSTANCE.getPos().round().y() + vy);
        }
        return new IntCoordinates(0,12);

    }
    public static IntCoordinates scatterObjectif (Ghost valeur) {
        if (valeur == BLINKY) {
            return new IntCoordinates(13 , 2);
        }
        if (valeur == PINKY) {
            return new IntCoordinates(0 , 0);
        }
        if (valeur == INKY) {
            return new IntCoordinates(14 , 12);
        }
        if (valeur == CLYDE) {
            return new IntCoordinates(0 , 12);
        }
        else {
            return new IntCoordinates(0,0);
        }
    }
    public static IntCoordinates frightenedObjectif(){
        Random random = new Random();
        int x = random.nextInt(14);
        int y = random.nextInt(12);
        return new IntCoordinates(x,y);
    }
    public static boolean isScatter(int S) {
        // Durées des phases "Scatter" et "Chase"
        if (S > 84){
            return false;
        }
        int scatterDuration = 7;
        int chaseDuration = 20;

        // Nombre de cycles complet (Scatter puis Chase) jusqu'à S
        int fullCycles = S / (scatterDuration + chaseDuration);

        // Durée totale de ces cycles complets
        int totalDuration = fullCycles * (scatterDuration + chaseDuration);

        // Si S est inférieur à la durée totale, alors il est dans la phase "Scatter"
        if (S - totalDuration < scatterDuration) {
            return true;
        }

        // Sinon, il est dans la phase "Chase" ou "Chase" permanent
        return false;
    }
}
