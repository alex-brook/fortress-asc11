class KeyDoor extends Door {
    private Item requiredItem;

    KeyDoor(final char mapChar, final Item item) {
        super(mapChar);
        requiredItem = item;
    }
}
