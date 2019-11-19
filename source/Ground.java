class Ground extends Tile{
    private Item hasItem;

    Ground() {
        this.hasItem = null;
    }
    Ground(final Item item) {
        this.hasItem = item;
    }

    public Item getItem() {
        return hasItem;
    }

    public void setItem(final Item item) {
        this.hasItem = item;
    }
}