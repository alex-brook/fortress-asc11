class Hazard extends Tile {
    private Item requiredItem;

    Hazard(final char mapChar, final Item item) {
        super(mapChar);
        requiredItem = item;
    }
}
