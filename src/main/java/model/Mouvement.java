package model;

import geometry.IntCoordinates;
import geometry.RealCoordinates;

import static model.Ghost.*;
import static model.Ghost.CLYDE;

public class Mouvement  {
    public static void start (int timer, MazeState maze){
        if ( BLINKY.getPos().estEgal(new RealCoordinates(7, 6)) && timer < 2) {
            blinkyStart1();
        }
        if ((timer > 2) && PINKY.getPos().round().estEgal(new IntCoordinates(7, 7))) {
            pinkyStart();
        }
        if ((maze.getPacgum() > maze.getPacgumTotal()/4) && INKY.getPos().round().estEgal(new IntCoordinates(6, 7))) {
            inkyStart();
        }
        if ((maze.getPacgum() > maze.getPacgumTotal()/2)  && CLYDE.getPos().round().estEgal(new IntCoordinates(8, 7))) {
            clydeStart();
        }
    }
    public static void north (  MazeState maze, Ghost valeurs, String mode){
        Direction dir = Direction.NONE;
        IntCoordinates co = new IntCoordinates((int)valeurs.getPos().getX() , (int)Math.floor( valeurs.getPos().getY() ) );
        IntCoordinates objectif = null;
        if (mode.equals("chase")){  objectif = Mode.chaseObjectif(valeurs);}
        else if (mode.equals("scatter")) { objectif = Mode.scatterObjectif(valeurs);}
        else {objectif = Mode.frightenedObjectif();}
        if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).westWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.upNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }

        else if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).westWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.upNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.upNeighbour() ) ;
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).westWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.leftNeighbour() ) ;
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).eastWall() ) {
            valeurs.setPos(teleporte (co, Direction.EAST));
            valeurs.setDirection(Direction.EAST);
        }
        else if ( !maze.getconfig().getCell(co).westWall() ) {
            valeurs.setPos(teleporte (co, Direction.WEST));
            valeurs.setDirection(Direction.WEST);
        }
    }
    public static void south (MazeState maze, Ghost valeurs, String mode){
        Direction dir = Direction.NONE;
        IntCoordinates co = new IntCoordinates((int)valeurs.getPos().getX() , (int)Math.ceil( valeurs.getPos().getY() ) );
        IntCoordinates objectif = null;
        if (mode.equals("chase")){  objectif = Mode.chaseObjectif(valeurs);}
        else if (mode.equals("scatter")) { objectif = Mode.scatterObjectif(valeurs);}
        else {objectif = Mode.frightenedObjectif();}
        if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).westWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.downNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).westWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.downNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.downNeighbour() );
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).westWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.leftNeighbour() );
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).westWall() ) {
            valeurs.setPos(teleporte (co, Direction.WEST));
            valeurs.setDirection(Direction.WEST);
        }
        else if (!maze.getconfig().getCell(co).eastWall() ) {
            valeurs.setPos(teleporte (co, Direction.EAST));
            valeurs.setDirection(Direction.EAST);
        }
    }
    public static void east (MazeState maze, Ghost valeurs, String mode){
        Direction dir = Direction.NONE;
        IntCoordinates co = new IntCoordinates((int)Math.ceil (valeurs.getPos().getX()) , (int)valeurs.getPos().getY());
        IntCoordinates objectif = null;
        if (mode.equals("chase")){  objectif = Mode.chaseObjectif(valeurs);}
        else if (mode.equals("scatter")) { objectif = Mode.scatterObjectif(valeurs);}
        else {objectif = Mode.frightenedObjectif();}
        if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).eastWall() && !maze.getconfig().getCell(co).northWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.downNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.downNeighbour(), co.rightNeighbour() );
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).eastWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.rightNeighbour() , co.upNeighbour() );
            if (vers.estEgal(co.rightNeighbour())){  dir = Direction.EAST;}
            if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).southWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.upNeighbour() , co.downNeighbour() );
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            if (vers.estEgal(co.upNeighbour())){  dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if (!maze.getconfig().getCell(co).southWall() ) {
            valeurs.setPos(teleporte (co, Direction.SOUTH));
            valeurs.setDirection(Direction.SOUTH);
        }
        else if (!maze.getconfig().getCell(co).northWall() ) {
            valeurs.setPos(teleporte (co, Direction.NORTH));
            valeurs.setDirection(Direction.NORTH);
        }
    }
    public static void west (MazeState maze , Ghost valeurs, String mode){
        Direction dir = Direction.NONE;
        IntCoordinates co = new IntCoordinates((int)Math.floor (valeurs.getPos().getX()) , (int)valeurs.getPos().getY()  );
        IntCoordinates objectif = null;
        if (mode.equals("chase")){  objectif = Mode.chaseObjectif(valeurs);}
        else if (mode.equals("scatter")) { objectif = Mode.scatterObjectif(valeurs);}
        else {objectif = Mode.frightenedObjectif();}
        if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).westWall() && !maze.getconfig().getCell(co).northWall() ) {
            IntCoordinates vers = Manhattan(objectif , co.upNeighbour() , co.downNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            else { dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).southWall() && !maze.getconfig().getCell(co).westWall() ) {
            IntCoordinates vers = Manhattan(objectif, co.downNeighbour(), co.leftNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            if (vers.estEgal(co.downNeighbour())){  dir = Direction.SOUTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).westWall() ) {
            IntCoordinates vers = Manhattan(objectif, co.leftNeighbour() , co.upNeighbour() );
            if (vers.estEgal(co.leftNeighbour())){  dir = Direction.WEST;}
            else {  dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }

        else if ( !maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).southWall()) {
            IntCoordinates vers = Manhattan(objectif, co.upNeighbour() , co.downNeighbour() );
            if (vers.estEgal(co.downNeighbour())){ dir = Direction.SOUTH;}
            else{ dir = Direction.NORTH;}
            valeurs.setPos(teleporte (co, dir));
            valeurs.setDirection(dir);
        }
        else if ( !maze.getconfig().getCell(co).southWall()) {
            valeurs.setPos(teleporte (co, Direction.SOUTH));
            valeurs.setDirection(Direction.SOUTH);
        }
        else if ( !maze.getconfig().getCell(co).northWall()) {
            valeurs.setPos(teleporte (co, Direction.NORTH));
            valeurs.setDirection(Direction.NORTH);
        }
    }
    public static void none(MazeState maze , Ghost valeurs, String mode){
        IntCoordinates co =valeurs.getPos().round();
        IntCoordinates objectif = null;
        if (mode.equals("chase")){  objectif = Mode.chaseObjectif(valeurs);}
        else if (mode.equals("scatter")) { objectif = Mode.scatterObjectif(valeurs);}
        else {objectif = Mode.frightenedObjectif();}
        if (!maze.getconfig().getCell(co).southWall()) {
            if (!maze.getconfig().getCell(co).eastWall() && !maze.getconfig().getCell(co).westWall()) {
                IntCoordinates vers = Manhattan(objectif , co.leftNeighbour() , co.downNeighbour(), co.rightNeighbour() );
                if (vers.estEgal(co.rightNeighbour())){  valeurs.setDirection(Direction.EAST);}
                if (vers.estEgal(co.downNeighbour())){  valeurs.setDirection(Direction.SOUTH);}
                else { valeurs.setDirection(Direction.WEST);}
            } else if (!maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).eastWall()) {
                IntCoordinates vers = Manhattan(objectif , co.upNeighbour() , co.downNeighbour(), co.rightNeighbour() );
                if (vers.estEgal(co.rightNeighbour())){  valeurs.setDirection(Direction.EAST);}
                if (vers.estEgal(co.downNeighbour())){  valeurs.setDirection(Direction.SOUTH);}
                else { valeurs.setDirection(Direction.NORTH);}
            } else if (!maze.getconfig().getCell(co).northWall() && !maze.getconfig().getCell(co).westWall()) {
                IntCoordinates vers = Manhattan(objectif , co.upNeighbour() , co.downNeighbour(), co.leftNeighbour() );
                if (vers.estEgal(co.leftNeighbour())){  valeurs.setDirection(Direction.WEST);}
                if (vers.estEgal(co.downNeighbour())){  valeurs.setDirection(Direction.SOUTH);}
                else { valeurs.setDirection(Direction.NORTH);}
            } else if (!maze.getconfig().getCell(co).eastWall()) {
                IntCoordinates vers = Manhattan(objectif , co.downNeighbour(), co.rightNeighbour() );
                if (vers.estEgal(co.downNeighbour())){ valeurs.setDirection(Direction.SOUTH);}
                else { valeurs.setDirection(Direction.WEST);}
            } else if (!maze.getconfig().getCell(co).westWall()) {
                IntCoordinates vers = Manhattan(objectif, co.downNeighbour(), co.leftNeighbour() );
                if (vers.estEgal(co.leftNeighbour())){  valeurs.setDirection(Direction.WEST);}
                if (vers.estEgal(co.downNeighbour())){  valeurs.setDirection(Direction.SOUTH);}
            }
        }
        else if (!maze.getconfig().getCell(co).northWall())    {
            if (!maze.getconfig().getCell(co).eastWall() && !maze.getconfig().getCell(co).westWall()) {
                IntCoordinates vers = Manhattan(objectif , co.leftNeighbour() , co.upNeighbour(), co.rightNeighbour() );
                if (vers.estEgal(co.rightNeighbour())){  valeurs.setDirection(Direction.EAST);}
                if (vers.estEgal(co.upNeighbour())){  valeurs.setDirection(Direction.NORTH);}
                else { valeurs.setDirection(Direction.WEST);}
            } else if (!maze.getconfig().getCell(co).eastWall()) {
                IntCoordinates vers = Manhattan(objectif, co.leftNeighbour(), co.upNeighbour() );
                if (vers.estEgal(co.leftNeighbour())){  valeurs.setDirection(Direction.WEST);}
                else { valeurs.setDirection(Direction.NORTH);}
            } else if (!maze.getconfig().getCell(co).westWall()) {
                IntCoordinates vers = Manhattan(objectif, co.leftNeighbour() , co.upNeighbour() );
                if (vers.estEgal(co.leftNeighbour())){  valeurs.setDirection(Direction.WEST);}
                else { valeurs.setDirection(Direction.NORTH);}
            }
        }
    }
    public static IntCoordinates Manhattan(IntCoordinates objectif , IntCoordinates prop1 , IntCoordinates prop2) {
        // Cette fonction retourne parmis les 2 propositions laquel est la plus proche de l'objectif
        if ( (prop1.estEgal(new IntCoordinates(14,7))) || (prop1.estEgal(new IntCoordinates(0,7)))){return prop2;}
        if ( (prop2.estEgal(new IntCoordinates(14,7))) || (prop2.estEgal(new IntCoordinates(0,7)))){return prop1;}
        int p2 = Math.abs(objectif.x() - prop2.x()) + Math.abs(objectif.y() - prop2.y());
        int p1 = Math.abs(objectif.x() - prop1.x()) + Math.abs(objectif.y() - prop1.y());
        if (p1 < p2) {
            return prop1;
        }
        if (p2 == p1) {
            if (Math.abs(objectif.y() - prop1.y()) < Math.abs(objectif.y() - prop2.y())) {
                return prop1;
            }
        }
        return prop2;

    }
    public static IntCoordinates Manhattan(IntCoordinates objectif , IntCoordinates prop1 , IntCoordinates prop2, IntCoordinates prop3){
        IntCoordinates res1 = Manhattan(objectif, prop2,prop3);
        return Manhattan(objectif, res1 , prop1);
    }
    public static RealCoordinates teleporte (IntCoordinates co , Direction dir){
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
    public static void blinkyStart1() {
        BLINKY.setDirection(Direction.WEST);
    }
    public static void pinkyStart(){
        PINKY.setDirection(Direction.NORTH);
    }
    public static void inkyStart(){
        INKY.setDirection(Direction.EAST);
    }
    public static void clydeStart(){
        CLYDE.setDirection(Direction.WEST);
    }
}
