class Ground extends Tile {
    // basic image
    private static final String GROUND = "floor_1.png";
    // tokens
    private static final String TOKEN_0 = "coin_anim_f0.png";
    private static final String TOKEN_1 = "coin_anim_f1.png";
    private static final String TOKEN_2 = "coin_anim_f2.png";


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

    @Override
    public void playerContact(final Player p) {
        if (hasItem != null && hasItem == Item.TOKEN) {
            pickupItem();
            p.giveToken();
        } else if (hasItem != null) {
            p.giveItem(pickupItem());
        }
    }
}
