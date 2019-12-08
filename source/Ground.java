import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Tile that is walkable by player and enemy, can contain items
 * Javadoc comments added by Stephen
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

    /**
     * Changes the properties of an instance of ground when a player picks up
     * its item
     * @return item picked up from tile
     */
    public Item pickupItem() {
        Item item = hasItem;
        hasItem = null;
        setMapChar(TileFactory.MapChars.GROUND);
        return item;
    }

    /**
     * Setter for item in an instance of ground
     * @param item item in ground tile
     */
    public void setItem(final Item item) {
        this.hasItem = item;
    }


    /**
     * Gets the minimap tile colour for an instance of ground
     * @return colour of tile on mimimap
     */
    @Override
    public Color getMinimapColor() {
        return hasItem == null ? super.getMinimapColor() : Color.GOLD;
    }

    /**
     * Draws the ground graphic with/ without relevant item
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
     * @param animationTick runtime of animation
     */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GROUND_IMG), x, y);
        if (hasItem != null) {
            gc.drawImage(getImage(hasItem.getImageName()), x, y);
        }
    }

    /**
     * Overrides the default contact method for an instance of player to pickup
     * an item if the instance of tile contains one
     * @param p instance of player
     */
    @Override
    public void playerContact(final Player p) {
        if (hasItem != null && hasItem == Item.TOKEN) {
            pickupItem();
            p.giveToken();
        } else if (hasItem != null) {
            p.giveItem(pickupItem());
        }
    }

    /**
     * Boolean that shows if an instance of ground is passable by an instance
     * of enemy
     * @param e instance of enemy
     * @return boolean that dictates if an instance of enemy can walk on the
     * tile
     */
    @Override
    public boolean isPassable(final Enemy e) {
        return hasItem == null;
    }
}
