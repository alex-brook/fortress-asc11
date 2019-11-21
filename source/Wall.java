class Wall extends Tile {
    Wall(final char mapChar) {
        super(mapChar);
    }

    @Override
    public boolean isPassable(final Player p) {
        return false;
    }
}
