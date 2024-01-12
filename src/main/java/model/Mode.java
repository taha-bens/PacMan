package model;

import java.util.Random;

import geometry.IntCoordinates;

import static model.Ghost.*;

public class Mode {
    /**
     * Changer le mode
     * @param timer
     * @param maze
     */
    public static void mode(int timer, MazeState maze) {
        if (!PacMan.UN.isEnergized()) {
            if (Mode.isScatter(timer)) {
                Mode.scatterMode(maze);
            } else {
                Mode.chaseMode(maze);
            }
        } else {
            Mode.frightenedMode(maze);
        }
    }

    /**
     *
     */
    public static void backward() {
        BLINKY.setDirection(getDirectionBackward(BLINKY.getDirection()));
        PINKY.setDirection(getDirectionBackward(PINKY.getDirection()));
        INKY.setDirection(getDirectionBackward(INKY.getDirection()));
        CLYDE.setDirection(getDirectionBackward(CLYDE.getDirection()));
    }

    /**
     *
     * @param dir
     * @return
     */
    public static Direction getDirectionBackward(Direction dir) {
        return switch (dir) {
            case NORTH -> Direction.SOUTH;
            case SOUTH -> Direction.NORTH;
            case EAST -> Direction.WEST;
            case WEST -> Direction.EAST;
            default -> Direction.NONE;
        };
    }

    /**
     *
     * @param maze
     */
    public static void scatterMode(MazeState maze) {
        for (Ghost valeurs : Ghost.values()) {
            if (BLINKY.getPos().round().estEgal(new IntCoordinates(7, 6))) {
                BLINKY.setDirection(Direction.WEST);
            }

            // Permet d'initialiser le premier mouvement de BLINKY
            if (BLINKY.getPos().round().estEgal(new IntCoordinates(7, 6))) {
                BLINKY.setDirection(Direction.WEST);
            }

            if (valeurs.getDirection() == Direction.NORTH) {
                if (valeurs.getPos().getY() - Math.floor(valeurs.getPos().getY()) < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.north(maze, valeurs, "dead");
                    } else {
                        Mouvement.north(maze, valeurs, "scatter");
                    }
                }
            } else if (valeurs.getDirection() == Direction.SOUTH) {
                if (Math.ceil(valeurs.getPos().getY()) - valeurs.getPos().getY() < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.south(maze, valeurs, "dead");
                    } else {
                        Mouvement.south(maze, valeurs, "scatter");
                    }
                }
            } else if (valeurs.getDirection() == Direction.EAST) {
                if (Math.ceil(valeurs.getPos().getX()) - valeurs.getPos().getX() < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.east(maze, valeurs, "dead");
                    } else {
                        Mouvement.east(maze, valeurs, "scatter");
                    }
                }
            }

            if (valeurs.getDirection() == Direction.WEST) {
                if (valeurs.getPos().getX() - Math.floor(valeurs.getPos().getX()) < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.west(maze, valeurs, "dead");
                    } else {
                        Mouvement.west(maze, valeurs, "scatter");
                    }
                }
            }
        }
    }

    /**
     *
     * @param maze
     */
    public static void frightenedMode(MazeState maze) {
        for (Ghost valeurs : Ghost.values()) {
            if (BLINKY.getPos().getX() == 7.0 && BLINKY.getPos().getY() < 6.10 && BLINKY.getPos().getY() > 6.0) {
                BLINKY.setDirection(Direction.NORTH);
            }

            if (valeurs.getDirection() == Direction.NORTH) {
                if (valeurs.getPos().getY() - Math.floor(valeurs.getPos().getY()) < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.north(maze, valeurs, "dead");
                    } else {
                        Mouvement.north(maze, valeurs, "frightened");
                    }
                }
            } else if (valeurs.getDirection() == Direction.SOUTH) {
                if (Math.ceil(valeurs.getPos().getY()) - valeurs.getPos().getY() < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.south(maze, valeurs, "dead");
                    } else {
                        Mouvement.south(maze, valeurs, "frightened");
                    }
                }
            } else if (valeurs.getDirection() == Direction.EAST) {
                if (Math.ceil(valeurs.getPos().getX()) - valeurs.getPos().getX() < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.east(maze, valeurs, "dead");
                    } else {
                        Mouvement.east(maze, valeurs, "frightened");
                    }
                }
            } else if (valeurs.getDirection() == Direction.WEST) {
                if (valeurs.getPos().getX() - Math.floor(valeurs.getPos().getX()) < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.west(maze, valeurs, "dead");
                    } else {
                        Mouvement.west(maze, valeurs, "frightened");
                    }
                }
            } else if (valeurs.getDirection() == Direction.NONE) {
                if (valeurs.isDead()) {
                    Mouvement.none(maze, valeurs, "dead");
                } else {
                    Mouvement.none(maze, valeurs, "frightened");
                }
            }
        }
    }

    /**
     *
     * @param maze
     */
    public static void chaseMode(MazeState maze) {
        for (Ghost valeurs : Ghost.values()) {
            if (valeurs.getDirection() == Direction.NORTH) {
                if (valeurs.getPos().getY() - Math.floor(valeurs.getPos().getY()) < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.north(maze, valeurs, "dead");
                    } else {
                        Mouvement.north(maze, valeurs, "chase");
                    }
                }
            } else if (valeurs.getDirection() == Direction.SOUTH) {
                if (Math.ceil(valeurs.getPos().getY()) - valeurs.getPos().getY() < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.south(maze, valeurs, "dead");
                    } else {
                        Mouvement.south(maze, valeurs, "chase");
                    }
                }
            } else if (valeurs.getDirection() == Direction.EAST) {
                if (Math.ceil(valeurs.getPos().getX()) - valeurs.getPos().getX() < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.east(maze, valeurs, "dead");
                    } else {
                        Mouvement.east(maze, valeurs, "chase");
                    }
                }
            } else if (valeurs.getDirection() == Direction.WEST) {
                if (valeurs.getPos().getX() - Math.floor( valeurs.getPos().getX()) < 0.1) {
                    if (valeurs.isDead()) {
                        Mouvement.west(maze, valeurs, "dead");
                    } else {
                        Mouvement.west(maze, valeurs, "chase");
                    }
                }
            } else if (valeurs.getDirection() == Direction.NONE) {
                if (valeurs.isDead()) {
                    Mouvement.none(maze, valeurs, "dead");
                } else {
                    Mouvement.none(maze, valeurs, "chase");
                }
            }
        }
    }

    /**
     * Retourne les coordonnées qui est l'objectif pour chaque fantome
     * Correspond à l'IA des fantomes
     * @param valeur
     * @return
     */
    public static IntCoordinates chaseObjectif(Ghost valeur) {
        if (valeur == BLINKY) {
            return PacMan.UN.getPos().round();

        } else if (valeur == PINKY) {
            return PacMan.UN.devantPacman();

        } else if (valeur == CLYDE) {
            if (CLYDE.getPos().round().minus(PacMan.UN.getPos().round()) > 4) {
                return PacMan.UN.getPos().round();

            } else {
                return new IntCoordinates(0, 13);
            }
        } else if (valeur == INKY) {
            IntCoordinates devantPac = PacMan.UN.devantPacman();
            int vx = devantPac.x() - BLINKY.getPos().round().x();
            int vy = devantPac.y() - BLINKY.getPos().round().y();
            return new IntCoordinates(
                    PacMan.UN.getPos().round().x() + vx,
                    PacMan.UN.getPos().round().y() + vy
            );
        }
        return new IntCoordinates(0,12);
    }

    public static IntCoordinates scatterObjectif(Ghost valeur) {
        if (valeur == BLINKY) {
            return new IntCoordinates(13, 2);

        } else if (valeur == PINKY) {
            return new IntCoordinates(0, 0);

        } else if (valeur == INKY) {
            return new IntCoordinates(14, 12);

        } else if (valeur == CLYDE) {
            return new IntCoordinates(0, 12);

        }
        return new IntCoordinates(0, 0);
    }

    public static IntCoordinates frightenedObjectif() {
        Random random = new Random();
        int x = random.nextInt(14);
        int y = random.nextInt(12);
        return new IntCoordinates(x, y);
    }

    public static IntCoordinates homeObjectif(){
        return new IntCoordinates(7,6);
    }
    public static boolean isScatter(int S) {
        // Durées des phases "Scatter" et "Chase"
        if (S > 84) {
            return false;
        }
        int scatterDuration = 7;
        int chaseDuration = 20;
        int fullCycles = S / (scatterDuration + chaseDuration);
        int totalDuration = fullCycles * (scatterDuration + chaseDuration);

        return S - totalDuration < scatterDuration;
    }
}
