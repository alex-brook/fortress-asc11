import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Type of tile that moves the player to a predesignated position.
 *
 * @author Alex
 * @author Stephen
 */
class Teleporter extends Tile {
    private static final String TELEPORTER_IMG = "potion_green.png";
    private static final String TELEPORT_SOUND = "teleport.wav";

    private int linkedX;
    private int linkedY;

    Teleporter(final char mapChar, final Map<String, Image> img,
               final int x, final int y) {
        super(mapChar, img);
        linkedX = x;
        linkedY = y;
    }

    /**
     * Draws the graphics for a teleporter tile in the scene.
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
     * @param animationTick runtime of animation
     */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GROUND_IMG), x, y);
        gc.drawImage(getImage(TELEPORTER_IMG), x, y);
    }

    /**
     * When player moves onto an instance of teleporter, they are moved to the
     * appropriate tile adjacent to the linked teleporter.
     * @param p instance of player
     */
    @Override
    public void playerContact(final Player p) {
        switch (p.getDirection()) {
            case UP:
                p.teleportTo(linkedX, linkedY - 1);
                break;
            case DOWN:
                p.teleportTo(linkedX, linkedY + 1);
                break;
            case LEFT:
                p.teleportTo(linkedX - 1, linkedY);
                break;
            case RIGHT:
                p.teleportTo(linkedX + 1, linkedY);
                break;
            default:
                break;
        }
        setSound(TELEPORT_SOUND);
    }

    /**
     * Getter for additional information from save file.
     * @return additional information
     */
    @Override
    public String getAdditionalInfo() {
        return String.format("%d%s%d",
                linkedX, GameState.INFO_DELIMITER, linkedY);
    }
}
