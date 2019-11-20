class StraightLineEnemy extends Enemy {
    private String direction;

    StraightLineEnemy(final int x, final int y, final char mapChar,
                      final String dir) {
        super(x, y, mapChar);
        direction = dir;
    }
}