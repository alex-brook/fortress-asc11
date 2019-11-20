class Hazard extends Tile{
    private Item requiredItem;

    Hazard() {
        requiredItem = null;
    }
    Hazard(final Item item) {
        requiredItem = item;
    }
}
