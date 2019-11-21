public abstract class Door extends Tile {
    private boolean locked;

    public Door(final char mapChar) {
        super(mapChar);
        locked = true;
    }

    protected final boolean isLocked() {
        return locked;
    }
    protected final void unlock() {
        locked = false;
        setMapChar(TileFactory.MapChars.GROUND);
    }
}
