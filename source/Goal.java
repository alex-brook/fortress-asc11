class Goal extends Tile {
    Goal(final char mapChar) {
        super(mapChar);
    }

    @Override
    public void playerContact(final Player p) {
        p.win();
    }
}