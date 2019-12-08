import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Contains the inherited behaviors of various types of tile.
 *
 * @author Alex
 * @author Stephen
 */
public abstract class Tile {
    protected static final String GROUND_IMG = "ground.png";

    private char mapChar;
    private String sound;

    private Map<String, Image> images;

    private Tile upNeighbour;
    private Tile downNeighbour;
    private Tile leftNeighbour;
    private Tile rightNeighbour;

    /**
     * Tile constructor.
     * @param mapc character in save file
     * @param img image for game scene
     */
    public Tile(final char mapc, final Map<String, Image> img) {
        mapChar = mapc;
        images = img;
    }

    /**
     * Dictates if the tile passable by the player.
     * @param p instance of player
     * @return true if player can walk on the tile
     */
    public boolean isPassable(final Player p) {
        return true;
    }

    /**
     * Dictates if the tile passable by an enemy.
     * @param e instance of enemy
     * @return true if enemy can walk on the tile
     */
    public boolean isPassable(final Enemy e) {
        return false;
    }

    /**
     * When player comes into contact with the tile...
     * @param p instance of player
     */
    public void playerContact(final Player p) {
        return;
    }

    /**
     * Setter for adjacent tile above current tile.
     * @param t current tile
     */
    public void setUpNeighbour(final Tile t) {
        upNeighbour = t;
    }

    /**
     * Setter for adjacent tile below current tile.
     * @param t current tile
     */
    public void setDownNeighbour(final Tile t) {
        downNeighbour = t;
    }

    /**
     * Setter for adjacent tile to the left of current tile.
     * @param t current tile
     */
    public void setLeftNeighbour(final Tile t) {
        leftNeighbour = t;
    }

    /**
     * Setter for adjacent tile to the right of current tile.
     * @param t current tile
     */
    public void setRightNeighbour(final Tile t) {
        rightNeighbour = t;
    }

    /**
     * Getter for adjacent tile above current tile.
     * @return tile above current tile
     */
    public Tile getUpNeighbour() {
        return upNeighbour;
    }

    /**
     * Getter for adjacent tile below current tile.
     * @return tile below current tile
     */
    public Tile getDownNeighbour() {
        return downNeighbour;
    }

    /**
     * Getter for adjacent tile to the left of current tile.
     * @return tile to the left of current tile
     */
    public Tile getLeftNeighbour() {
        return leftNeighbour;
    }

    /**
     * Getter for adjacent tile to the right of current tile.
     * @return tile to the right of current tile
     */
    public Tile getRightNeighbour() {
        return rightNeighbour;
    }

    /**
     * Setter for sound effect.
     * @param snd name of sound file
     */
    protected void setSound(final String snd) {
        sound = snd;
    }

    /**
     * Returns current sound file name sets it to null so no repeats.
     * @return name of sound file
     */
    public String consumeSound() {
        String snd = sound;
        setSound(null);
        return snd;
    }

    /**
     * Getter for character in save file for tile.
     * @return Character in save file
     */
    public final char getMapChar() {
        return mapChar;
    }

    /**
     * Setter for character in save file.
     * @param mapc character in save file
     */
    protected final void setMapChar(final char mapc) {
        mapChar = mapc;
    }

    /**
     * Getter for additional information from save file.
     * @return additional information
     */
    public String getAdditionalInfo() {
        return null;
    }

    /**
     * Draws the graphics for a tile in the scene.
     * @param gc drawable feature of canvas
     * @param x x coordinate
     * @param y y coordinate
     * @param animationTick runtime of animation
     */
    public void draw(final GraphicsContext gc, final double x, final double y,
                     final int animationTick) {
        return;
    }

    /**
     * Getter for the image of the tile when drawn.
     * @param name image file name
     * @return image to be drawn
     */
    protected Image getImage(final String name) {
        return images.get(name);
    }

    /**
     * Getter for the colour of the graphic for the tile on the minimap.
     * @return colour of tile in the minimap
     */
    public Color getMinimapColor() {
        return Color.DARKSLATEGREY;
    }

    /**
     * Generic toString override.
     * @return corrected toString
     */
    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}
