import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Tile that is walkable by player and enemy, can contain items
 *
 * @author Alex
 */

class Ground extends Tile {
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
        if (hasItem != null) {
            gc.drawImage(getImage(hasItem.getImageName()), x, y);
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
