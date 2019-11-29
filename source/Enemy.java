import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Map;

/**
 * Contains the inherited behaviours of the various types of enemy
 *
 * @author Alex and Irfaan
 */

public abstract class Enemy {
    private char mapChar;
    private int xPos;
    private int yPos;
    protected Direction direction;
    protected boolean lookingRight;

    private Map<String, Image> images;

    public Enemy(final int x, final int y, final char mapc,
                 final Map<String, Image> img) {
        images = img;
        xPos = x;
        yPos = y;
        mapChar = mapc;
        lookingRight = true;
    }

    public Image getImage(final String name) {
        return images.get(name);
    }

    public abstract void move(final boolean[][] passableGrid,
                              final int playerX, final int playerY);

    public final int getXPos() {
        return xPos;
    }

    public final int getYPos() {
        return yPos;
    }

    public final char getMapChar() {
        return mapChar;
    }

    public String getAdditionalInfo() {
        return null;
    }

    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        final int leanDegrees = 10;
        gc.save();
        if (!lookingRight) {
            gc.translate(x, y);
            gc.scale(-1, 1);
            gc.translate(-x - GameState.TILE_RES, -y);
        }
        if (direction == Direction.UP) {
            gc.translate(x, y);
            gc.rotate(-leanDegrees);
            gc.translate(-x, -y);
        } else if (direction == Direction.DOWN) {
            gc.translate(x, y);
            gc.rotate(leanDegrees);
            gc.translate(-x, -y);
        }
    }
    
    public void moveLeft() {
        xPos -= 1;
        direction = Direction.LEFT;
        lookingRight = false;
    }

    public void moveRight() {
        xPos += 1;
        direction = Direction.RIGHT;
        lookingRight = true;
    }

    public void moveUp() {
        yPos -= 1;
        direction = Direction.UP;
    }

    public void moveDown() {
        yPos += 1;
        direction = Direction.DOWN;
    }

    public int getUpY() {
        return yPos - 1;
    }

    public int getRightX() {
        return xPos + 1;
    }

    public int getDownY() {
        return yPos + 1;
    }

    public int getLeftX() {
        return xPos - 1;
    }

    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}