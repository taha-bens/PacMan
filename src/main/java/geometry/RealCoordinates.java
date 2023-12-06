package geometry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * On défini des coordonnées réelles pour représenter les positions qui se
 * situe entre chaque coordonnées (par ex : entre 1.0 et 2.0 -> 1.57 ou 1.69)
 * cela permet de rendre fluide les déplacement des créature par exemple
 * (si on c'était uniquement contanté de "IntCoordinates", les créatures
 * se téléporteraient de 1 coordonnée à chaque fois, ça ne serai pas fluide)
 * @param x
 * @param y
 */
public record RealCoordinates(double x, double y) {

    public static final RealCoordinates ZERO = new RealCoordinates(0, 0);
    public static final RealCoordinates NORTH_UNIT = new RealCoordinates(0, -1);
    public static final RealCoordinates EAST_UNIT = new RealCoordinates(1, 0);
    public static final RealCoordinates SOUTH_UNIT = new RealCoordinates(0, 1);
    public static final RealCoordinates WEST_UNIT = new RealCoordinates(-1, 0);

    public RealCoordinates plus(RealCoordinates other) {
        return new RealCoordinates(x + other.x, y + other.y);
    }

    public RealCoordinates times(double multiplier) {
        return new RealCoordinates(x * multiplier, y * multiplier);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Donner les cases voisines par rapport à this.x et this.y
     * @return the coordinates of all integer squares that a unit square with current coordinates would intersect
     * Set<IntCoordinate> (ensemble immuable)
     */
    public Set<IntCoordinates> intNeighbours() {
        return new HashSet<>(List.of(
                new IntCoordinates((int) Math.floor(x), (int) Math.floor(y)),
                new IntCoordinates((int) Math.floor(x), (int) Math.ceil(y)),
                new IntCoordinates((int) Math.ceil(x), (int) Math.floor(y)),
                new IntCoordinates((int) Math.ceil(x), (int) Math.ceil(y))
        )
        );
    }

    public IntCoordinates round() {
        return new IntCoordinates((int) Math.round(x), (int) Math.round(y));
    }

    public RealCoordinates floorX() {
        return new RealCoordinates((int) Math.floor(x), y);
    }

    public RealCoordinates floorY() {
        return new RealCoordinates(x, (int) Math.floor(y));
    }

    public RealCoordinates ceilX() {
        return new RealCoordinates((int) Math.ceil(x), y);
    }

    public RealCoordinates ceilY() {
        return new RealCoordinates(x, (int) Math.ceil(y));
    }

    public RealCoordinates warp(int width, int height) {
        var rx = x;
        var ry = y;
        while (Math.round(rx) < 0)
            rx += width;
        while (Math.round(ry) < 0)
            ry += height;
        while (Math.round(rx) >= width)
            rx -= width;
        while (Math.round(rx) >= height)
            ry -= height;
        return new RealCoordinates(rx, ry);
    }

    public boolean estEgal(RealCoordinates co) {
        return ((this.x == co.getX()) && (this.y == co.getY()));
    }
}