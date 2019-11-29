import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Type of door tile that is openable by the player if they have the correct
 * number of points
 *
 * @author Alex
 */

class ScoreDoor extends Door {
    private static final String LOCK_UP = "token_door_bottom.png";
    private static final String LOCK_DOWN = "token_door_top.png";
    private static final String LOCK_LEFT = "token_door_right.png";
    private static final String LOCK_RIGHT = "token_door_left.png";


    private int tokensNeeded;

    ScoreDoor(final char mapChar, final Map<String, Image> img, final int tokens) {
        super(mapChar, img);
        tokensNeeded = tokens;
    }
    @Override
    public boolean isPassable(final Player p) {
        if (isLocked()) {
            return p.getTokenCount() >= tokensNeeded;
        }
        return true;
    }

    @Override
    public void playerContact(final Player p) {
        if (isLocked()) {
            p.takeTokens(tokensNeeded);
            unlock();
        }
    }

    @Override
    public void draw(final GraphicsContext gc, final double x,
                     final double y, final int animationTick) {
        super.draw(gc, x, y, animationTick);
        gc.save();
        gc.setFill(Color.WHITE);
        if (isLocked() && isUp()) {
            gc.drawImage(getImage(LOCK_UP), x, y);
        } else if (isLocked() && isDown()) {
            gc.drawImage(getImage(LOCK_DOWN), x, y);
        } else if (isLocked() && isLeft()) {
            gc.drawImage(getImage(LOCK_LEFT), x, y);
        } else if (isLocked() && isRight()) {
            gc.drawImage(getImage(LOCK_RIGHT), x, y);
        }
        gc.restore();
    }

    @Override
    public String getAdditionalInfo() {
        if (isLocked()) {
            return String.format("%d", tokensNeeded);
        } else {
            return null;
        }
    }
}