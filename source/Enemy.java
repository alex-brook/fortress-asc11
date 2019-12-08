import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Map;

/**
 * Contains the inherited behaviours of the various types of enemy.
 *
 * @author Alex
 * @author Irfaan
 * @author Stephen
 */

public abstract class Enemy {
    private char mapChar;
    private int xPos;
    private int yPos;
    protected Direction direction;
    protected boolean lookingRight;

    private Map<String, Image> images;

    /**
     * Enemy Constructor.
     * @param x x coordinate of enemy
     * @param y y coordinate of enemy
     * @param mapc character in save file
     * @param img img when drawn to scene
     */
    public Enemy(final int x, final int y, final char mapc,
                 final Map<String, Image> img) {
        images = img;
        xPos = x;
        yPos = y;
        mapChar = mapc;
        lookingRight = true;
    }

    /**
     * Getter for the image graphic of the enemy.
     * @param name name of the enemy image as a string
     * @return image file with the requested name
     */
    public Image getImage(final String name) {
        return images.get(name);
    }

    /**
     * Abstract move function for enemy types to override.
     * @param passableGrid 2D array of booleans that show if each tile is
     *                     passable by an instace of enemy or not
     * @param playerX player instance's x coordinate
     * @param playerY player instance's y coordinate
     */
    public abstract void move(boolean[][] passableGrid,
                              int playerX, int playerY);


    /**
     * Gets the coordinates of the adjacent tile in the specified direction
     * of the player.
     * @param d direction from the player
     * @return coordinates in the adjacent tile
     */
    protected final Point pointFromDirection(final Direction d) {
        return pointFromDirection(new Point(getXPos(), getYPos()), d);
    }

    /**
     * Gets the coordinates of the adjacent tile in a specified direction of
     * the point.
     * @param p point looking at
     * @param d direction from the point
     * @return coordinates of the adjacent tile
     */
    protected final Point pointFromDirection(final Point p, final Direction d) {
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
     * Getter for instances x coordinate.
     * @return x coordinate
     */
    public final int getXPos() {
        return xPos;
    }

    /**
     * Getter for instances y coordinate.
     * @return y coordinate
     */
    public final int getYPos() {
        return yPos;
    }

    /**
     * Getter for enemy save file character.
     * @return map character
     */
    public final char getMapChar() {
        return mapChar;
    }

    /**
     * Getter for any additional information on save file.
     * @return additional information
     */
    public String getAdditionalInfo() {
        return null;
    }

    /**
     * Default draw method for enemy.
     * @param gc drawable feature of canvas
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @param animationTick runtime of animation
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
     * Moves instance of enemy one tile to the left.
     */
    public void moveLeft() {
        xPos -= 1;
        direction = Direction.LEFT;
        lookingRight = false;
    }

    /**
     * Moves an instance of enemy one tile to the right.
     */
    public void moveRight() {
        xPos += 1;
        direction = Direction.RIGHT;
        lookingRight = true;
    }

    /**
     * Moves an instance of enemy one tile upwards.
     */
    public void moveUp() {
        yPos -= 1;
        direction = Direction.UP;
    }

    /**
     * Moves an instance of enemy one tile downwards.
     */
    public void moveDown() {
        yPos += 1;
        direction = Direction.DOWN;
    }

    /**
     * Moves enemy in the direction it is facing.
     */
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
     * Getter for y coordinate change when moving upwards.
     * @return y coordinate change
     */
    public int getUpY() {
        return yPos - 1;
    }

    /**
     * Getter for x coordinate change when moving to the right.
     * @return x coordinate change
     */
    public int getRightX() {
        return xPos + 1;
    }

    /**
     * Getter for y coordinate change when moving downwards.
     * @return y coordinate change
     */
    public int getDownY() {
        return yPos + 1;
    }

    /**
     * Getter for x coordinate change when moving to the left.
     * @return x coordinate change
     */
    public int getLeftX() {
        return xPos - 1;
    }

    /**
     * Generic toString override.
     * @return wanted toString output
     */
    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}
