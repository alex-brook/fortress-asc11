import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

class KeyDoor extends Door {
    private static final String LOCK_LEFT_IMG = "door_lock_right.png";
    private static final String LOCK_RIGHT_IMG = "door_lock_left.png";
    private static final String LOCK_UP_IMG = "door_lock_bottom.png";
    private static final String LOCK_DOWN_IMG = "door_lock_top.png";

    private static final String LOCK_LEFT_RED = "red_door_lock_right.png";
    private static final String LOCK_RIGHT_RED = "red_door_lock_left.png";
    private static final String LOCK_UP_RED = "red_door_lock_bottom.png";
    private static final String LOCK_DOWN_RED = "red_door_lock_top.png";

    private static final String LOCK_LEFT_GREEN = "green_door_lock_right.png";
    private static final String LOCK_RIGHT_GREEN = "green_door_lock_left.png";
    private static final String LOCK_UP_GREEN = "green_door_lock_bottom.png";
    private static final String LOCK_DOWN_GREEN = "green_door_lock_top.png";

    private static final String LOCK_LEFT_BLUE = "blue_door_lock_right.png";
    private static final String LOCK_RIGHT_BLUE = "blue_door_lock_left.png";
    private static final String LOCK_UP_BLUE = "blue_door_lock_bottom.png";
    private static final String LOCK_DOWN_BLUE = "blue_door_lock_top.png";

    private Item requiredItem;

    KeyDoor(final char mapChar, final Map<String, Image> img, final Item item) {
        super(mapChar, img);
        requiredItem = item;
    }

    @Override
    public boolean isPassable(final Player p) {
        if (isLocked()) {
            return p.hasItem(requiredItem);
        }
        return true;
    }

    @Override
    public void playerContact(final Player p) {
        if (isLocked()) {
            p.takeItem(requiredItem);
            unlock();
        }
    }

    @Override
    public String getAdditionalInfo() {
        if (isLocked()) {
            return String.format("%c", requiredItem.getMapChar());
        }
        return null;
    }

    private void drawLock(final GraphicsContext gc, final double x,
                          final double y) {
        switch (requiredItem) {
            case RED_KEY:
                if (isUp()) {
                    gc.drawImage(getImage(LOCK_UP_RED), x, y);
                } else if (isDown()) {
                    gc.drawImage(getImage(LOCK_DOWN_RED), x, y);
                } else if (isLeft()) {
                    gc.drawImage(getImage(LOCK_LEFT_RED), x, y);
                } else if (isRight()) {
                    gc.drawImage(getImage(LOCK_RIGHT_RED), x, y);
                }
                break;
            case GREEN_KEY:
                if (isUp()) {
                    gc.drawImage(getImage(LOCK_UP_GREEN), x, y);
                } else if (isDown()) {
                    gc.drawImage(getImage(LOCK_DOWN_GREEN), x, y);
                } else if (isLeft()) {
                    gc.drawImage(getImage(LOCK_LEFT_GREEN), x, y);
                } else if (isRight()) {
                    gc.drawImage(getImage(LOCK_RIGHT_GREEN), x, y);
                }
                break;
            case BLUE_KEY:
                if (isUp()) {
                    gc.drawImage(getImage(LOCK_UP_BLUE), x, y);
                } else if (isDown()) {
                    gc.drawImage(getImage(LOCK_DOWN_BLUE), x, y);
                } else if (isLeft()) {
                    gc.drawImage(getImage(LOCK_LEFT_BLUE), x, y);
                } else if (isRight()) {
                    gc.drawImage(getImage(LOCK_RIGHT_BLUE), x, y);
                }
                break;
            default:
                if (isUp()) {
                    gc.drawImage(getImage(LOCK_UP_IMG), x, y);
                } else if (isDown()) {
                    gc.drawImage(getImage(LOCK_DOWN_IMG), x, y);
                } else if (isLeft()) {
                    gc.drawImage(getImage(LOCK_LEFT_IMG), x, y);
                } else if (isRight()) {
                    gc.drawImage(getImage(LOCK_RIGHT_IMG), x, y);
                }
        }
    }

    @Override
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        super.draw(gc, x, y, animationTick);
        if (isLocked()) {
            drawLock(gc, x, y);
        }
    }
}
