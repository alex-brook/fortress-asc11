import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

class Teleporter extends Tile {
    private static final String TELEPORTER_IMG = "potion_green.png";

    private int linkedX;
    private int linkedY;

    Teleporter(final char mapChar, final Map<String, Image> img,
               final int x, final int y) {
        super(mapChar, img);
        linkedX = x;
        linkedY = y;
    }

    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        gc.drawImage(getImage(GROUND_IMG), x, y);
        gc.drawImage(getImage(TELEPORTER_IMG), x, y);
    }

    @Override
    public void playerContact(final Player p) {
        p.teleportTo(linkedX, linkedY);
    }

    @Override
    public String getAdditionalInfo() {
        return String.format("%d%s%d",
                linkedX, GameState.INFO_DELIMITER, linkedY);
    }
}