package config;

public record Cell(boolean northWall,
                   boolean eastWall,
                   boolean southWall,
                   boolean westWall,
                   Cell.Content initialContent) {

    public enum Content {
        NOTHING, DOT, ENERGIZER
    }

    public static Cell UCell(char direction, Content c) {
        return new Cell(direction != 'n',
                direction != 'e',
                direction != 's',
                direction != 'w',
                c);
    }

    public static Cell TCell(char direction, Content c) {
        return new Cell(direction == 'n',
                direction == 'e',
                direction == 's',
                direction == 'w',
                c);
    }

    public static Cell CornerCell(String coin, Content c) {

        return new Cell(coin.contains("n"),
                coin.contains("e"),
                coin.contains("s"),
                coin.contains("w"),
                c);
    }

    public static Cell PipeCell(char orientation, Content c) {
        return new Cell(orientation == 'h',
                orientation == 'v',
                orientation == 'h',
                orientation == 'v',
                c);
    }

    public static Cell EntierCell(boolean b, Content c) {
        return new Cell(b, b, b, b, c);
    }
}