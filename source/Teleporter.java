import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Type of tile that moves the player to a predesignated position
 * Javadoc comments added by Stephen
 *
 * @author Alex
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
     *
     * @param gc
     * @param x
     * @param y
     * @param animationTick
     */
    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GROUND_IMG), x, y);
        gc.drawImage(getImage(TELEPORTER_IMG), x, y);
    }

    /**
     *
     * @param p
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
        }
        setSound(TELEPORT_SOUND);
    }

    /**
     *
     * @return
     */
    @Override
    public String getAdditionalInfo() {
        return String.format("%d%s%d",
                linkedX, GameState.INFO_DELIMITER, linkedY);
    }
}