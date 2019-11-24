public abstract class Tile {
    private char mapChar;

    public Tile(final char mapc) {
        mapChar = mapc;
    }

    public boolean isPassable(final Player p) {
        return true;
    }
    public boolean isPassable(final Enemy e) {
        // probably easier to assume an enemy can't go on a tile unless
        // told otherwise.
        return false;
    }

    public void playerContact(final Player p) {
        return;
    }

    public final char getMapChar() {
        return mapChar;
    }

    protected final void setMapChar(final char mapc) {
        mapChar = mapc;
    }

    public String getAdditionalInfo() {
        return null;
    }

    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}