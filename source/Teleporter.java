class Teleporter extends Tile {
    private int linkedX;
    private int linkedY;

    Teleporter(final char mapChar, final int x, final int y) {
        super(mapChar);
        linkedX = x;
        linkedY = y;
    }
}