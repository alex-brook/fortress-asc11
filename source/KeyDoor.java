class KeyDoor extends Door {
    private Item requiredItem;

    KeyDoor(final char mapChar, final Item item) {
        super(mapChar);
        requiredItem = item;
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("%c", requiredItem.getMapChar());
    }
}
