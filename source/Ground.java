import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Tile that is walkable by player and enemy, can contain items
 *
 * @author Alex
 */

class Ground extends Tile {
    // basic image

    //keys
    private static final String RED_KEY_IMG = "key_red.png";
    private static final String BLUE_KEY_IMG = "key_blue.png";
    private static final String GREEN_KEY_IMG = "key_green.png";
    //clothes
    private static final String FIRE_BOOTS_IMG = "brown_boots.png";
    private static final String FLIPPERS_IMG = "silver_boots.png";
    // tokens


    private Item hasItem;

    Ground(final char mapChar, final Map<String, Image> img) {
        super(mapChar, img);
        this.hasItem = null;
    }
    Ground(final char mapChar, final Map<String, Image> img, final Item item) {
        super(mapChar, img);
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
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GROUND_IMG), x, y);

        if (hasItem == Item.RED_KEY) {
            gc.drawImage(getImage(RED_KEY_IMG), x, y);
        } else if (hasItem == Item.BLUE_KEY) {
            gc.drawImage(getImage(BLUE_KEY_IMG), x, y);
        } else if (hasItem == Item.GREEN_KEY) {
            gc.drawImage(getImage(GREEN_KEY_IMG), x, y);
        } else if (hasItem == Item.TOKEN) {
            gc.drawImage(getImage(TOKEN_IMG), x, y);
        } else if (hasItem == Item.FIRE_BOOTS) {
            gc.drawImage(getImage(FIRE_BOOTS_IMG), x, y);
        } else if (hasItem == Item.FLIPPERS) {
            gc.drawImage(getImage(FLIPPERS_IMG), x, y);
        }
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
    
    @Override
    public boolean isPassable(final Enemy e) {
        return true;
    }
}
