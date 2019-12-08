import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Contains the inherited behaviors of various types of tile
 * Javadoc comments added by Stephen
 *
 * @author Alex
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

    public Tile(final char mapc, final Map<String, Image> img) {
        mapChar = mapc;
        images = img;
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean isPassable(final Player p) {
        return true;
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean isPassable(final Enemy e) {
        return false;
    }

    /**
     *
     * @param p
     */
    public void playerContact(final Player p) {
        return;
    }

    /**
     *
     * @param t
     */
    public void setUpNeighbour(final Tile t) {
        upNeighbour = t;
    }

    /**
     *
     * @param t
     */
    public void setDownNeighbour(final Tile t) {
        downNeighbour = t;
    }

    /**
     *
     * @param t
     */
    public void setLeftNeighbour(final Tile t) {
        leftNeighbour = t;
    }

    /**
     *
     * @param t
     */
    public void setRightNeighbour(final Tile t) {
        rightNeighbour = t;
    }

    /**
     *
     * @return
     */
    public Tile getUpNeighbour() {
        return upNeighbour;
    }

    /**
     *
     * @return
     */
    public Tile getDownNeighbour() {
        return downNeighbour;
    }

    /**
     *
     * @return
     */
    public Tile getLeftNeighbour() {
        return leftNeighbour;
    }

    /**
     *
     * @return
     */
    public Tile getRightNeighbour() {
        return rightNeighbour;
    }

    /**
     *
     * @param snd
     */
    protected void setSound(String snd) {
        sound = snd;
    }

    /**
     *
     * @return
     */
    public String consumeSound() {
        String snd = sound;
        setSound(null);
        return snd;
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
     * @param mapc
     */
    protected final void setMapChar(final char mapc) {
        mapChar = mapc;
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
        return;
    }

    /**
     *
     * @param name
     * @return
     */
    protected Image getImage(final String name) {
        return images.get(name);
    }

    /**
     *
     * @return
     */
    public Color getMinimapColor() {
        return Color.DARKSLATEGREY;
    }

    /**
     *
     * @return
     */
    @Override
    public final String toString() {
        return String.valueOf(getMapChar());
    }
}