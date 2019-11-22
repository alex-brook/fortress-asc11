class Hazard extends Tile {
    private Item requiredItem;

    Hazard(final char mapChar, final Item item) {
        super(mapChar);
        requiredItem = item;
    }

    @Override
    public void playerContact(final Player p) {
        if (!p.hasItem(requiredItem)) {
            p.kill();
        }
    }
}
