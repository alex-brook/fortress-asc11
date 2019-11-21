class Teleporter extends Tile {
    private int linkedX;
    private int linkedY;

    Teleporter(final char mapChar, final int x, final int y) {
        super(mapChar);
        linkedX = x;
        linkedY = y;
    }

    @Override
    public void playerContact(final Player p) {
        p.teleportTo(linkedX, linkedY);
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("%d%s%d",
                linkedX, GameState.INFO_DELIMITER, linkedY);
    }
}