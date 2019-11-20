class StraightLineEnemy extends Enemy {
    private String direction;

    StraightLineEnemy(final int x, final int y, final String dir) {
        super(x, y);
        direction = dir;
    }
}