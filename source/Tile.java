public abstract class Tile {
    protected char mapChar;

    public Tile(final char mapc) {
        mapChar = mapc;
    }

    public boolean isPassable(final Player p) {
        return true;
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