import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
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

    /**
     *
     * @param name
     * @return
     */
    public Image getImage(final String name) {
        return images.get(name);
    }

    public abstract void move(final boolean[][] passableGrid,
                              final int playerX, final int playerY);



    protected final Point pointFromDirection(Direction d) {
        return pointFromDirection(new Point(getXPos(), getYPos()), d);
    }
    protected final Point pointFromDirection(Point p, Direction d) {
        switch (d) {
            case UP:
                return new Point(p.x, p.y - 1);
            case DOWN:
                return new Point(p.x, p.y + 1);
            case LEFT:
                return new Point(p.x - 1, p.y);
            case RIGHT:
                return new Point(p.x + 1, p.y);
            default:
                return null;
        }
    }

    /**
     *
     * @return
     */
    public final int getXPos() {
        return xPos;
    }

    /**
     *
     * @return
     */
    public final int getYPos() {
        return yPos;
    }

    /**
     *
     * @return
     */
    public final char getMapChar() {
        return mapChar;
    }

    /**
     *
     * @return
     */
    public String getAdditionalInfo() {
        return null;
    }

    /**
     *
     * @param gc
     * @param x
     * @param y
     * @param animationTick
     */
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

    /**
     *
     */
    public void moveLeft() {
        xPos -= 1;
        direction = Direction.LEFT;
        lookingRight = false;
    }

    /**
     *
     */
    public void moveRight() {
        xPos += 1;
        direction = Direction.RIGHT;
        lookingRight = true;
    }

    /**
     *
     */
    public void moveUp() {
        yPos -= 1;
        direction = Direction.UP;
    }

    /**
     *
     */
    public void moveDown() {
        yPos += 1;
        direction = Direction.DOWN;
    }

    public void moveInCurrentDirection() {
        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
            default:
                return;
        }
    }

    /**
     *
     * @return
     */
    public int getUpY() {
        return yPos - 1;
    }

    /**
     *
     * @return
     */
    public int getRightX() {
        return xPos + 1;
    }

    /**
     *
     * @return
     */
    public int getDownY() {
        return yPos + 1;
    }

    /**
     *
     * @return
     */
    public int getLeftX() {
        return xPos - 1;
    }

    /**
     * Generic toString override
     * @return wanted toString output
     */
    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}