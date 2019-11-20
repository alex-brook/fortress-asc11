class Ground extends Tile {
    private Item hasItem;

    Ground(final char mapChar) {
        super(mapChar);
        this.hasItem = null;
    }
    Ground(final char mapChar, final Item item) {
        super(mapChar);
        this.hasItem = item;
    }

    public Item pickupItem() {
        Item item = hasItem;
        hasItem = null;
        setMapChar(TileFactory.MapChars.GROUND);
        return item;
    }

    public void setItem(final Item item) {
        this.hasItem = item;
    }
}
