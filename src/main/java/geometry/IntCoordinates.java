package geometry;

//import rajouté
import geometry.RealCoordinates;

public record IntCoordinates(int x, int y) {
    public RealCoordinates toRealCoordinates(double scale) {
        return new RealCoordinates(x * scale, y * scale);
    }
    public IntCoordinates upNeighbour(){
        return new IntCoordinates(x , y-1);
    }
    public IntCoordinates downNeighbour(){
        return new IntCoordinates(x , y+1);
    }
    public IntCoordinates leftNeighbour(){
        return new IntCoordinates(x-1 , y);
    }
    public IntCoordinates rightNeighbour(){
        return new IntCoordinates(x+1 , y);
    }
    public boolean estEgal (IntCoordinates p){
        return ((x == p.x) && (y == p.y));
    }
    public int minus(IntCoordinates co){
        return Math.abs(this.x -co.x) + Math.abs(this.y - co.y);
    }
}
