class Hazard extends Tile{
    private Item requiredItem;

    Hazard() {
        requiredItem = null;
    }
    Hazard(final Item item) {
        requiredItem = item;
    }

    public char getMapChar() {
        if (requiredItem == Item.FIRE_BOOTS) {
            return MapChars.FIRE;
        } else if (requiredItem == Item.FLIPPERS) {
            return MapChars.WATER;
        }
        return '\0';
    }
}
